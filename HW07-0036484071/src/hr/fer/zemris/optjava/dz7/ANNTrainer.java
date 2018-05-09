package hr.fer.zemris.optjava.dz7;

import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.optjava.dz7.clonalg.AntiBody;
import hr.fer.zemris.optjava.dz7.clonalg.ClonAlg;
import hr.fer.zemris.optjava.dz7.models.FFANN;
import hr.fer.zemris.optjava.dz7.models.IDataset;
import hr.fer.zemris.optjava.dz7.models.ITransferFunction;
import hr.fer.zemris.optjava.dz7.models.SigmoidFunction;
import hr.fer.zemris.optjava.dz7.models.Util;
import hr.fer.zemris.optjava.dz7.pso.PSO;
import hr.fer.zemris.optjava.dz7.pso.Particle;

/**
 * @author Mato
 * 
 *         Konfiguracija za PSO na primjer: ./07-iris-formatirano.data pso-b-10
 *         50 1E-2 1500
 *
 *         Konfiguracija za ClonAlg na primjer: ./07-iris-formatirano.data
 *         clonalg 50 1E-2 150
 */
public class ANNTrainer {
	public static void main(String[] args) {
		Path file = Paths.get(args[0]);
		String algorithm = args[1];
		int populationSize = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxIteration = Integer.parseInt(args[4]);

		IDataset dataset = Util.loadDataset(file);
		FFANN ffann = new FFANN(new int[] { 4, 5, 3, 3 },
				new ITransferFunction[] { new SigmoidFunction(), new SigmoidFunction(), new SigmoidFunction() },
				dataset);

		double[] bestWeights = new double[0];

		// napomena: ostali parametri su postavljeni u donjim metodama tj. nisu
		// prenoseni kroz konstruktor sto bi bilo kaoticno
		if (algorithm.equalsIgnoreCase("pso-a")) {
			bestWeights = new PSO(populationSize, merr, maxIteration, populationSize, ffann).run();
			setPSOParameters();
		} else if (algorithm.toLowerCase().startsWith("pso-b-")) {
			int neighborhoodSize = Integer.parseInt(algorithm.substring(6));
			setPSOParameters();
			bestWeights = new PSO(populationSize, merr, maxIteration, neighborhoodSize, ffann).run();
		} else if (algorithm.equalsIgnoreCase("clonalg")) {
			setClonAlgParameters();
			bestWeights = new ClonAlg(merr, maxIteration, populationSize, ffann).run();
		} else {
			System.out.println("Unsuported algorithm: " + algorithm);
			return;
		}
		ffann.printStats(bestWeights);
	}

	private static void setPSOParameters() {
		Particle.C1 = 2;
		Particle.C2 = 2;
		Particle.V_MAX = 0.2;
		Particle.V_MIN = -0.2;
		Particle.X_MAX = 1;
		Particle.X_MIN = -1;
		PSO.W_MAX = 1;
		PSO.W_MIN = 0;
	}

	private static void setClonAlgParameters() {
		AntiBody.X_MAX = 1;
		AntiBody.X_MIN = -1;
		ClonAlg.BETA = 2;
		ClonAlg.RO = 4;
	}

}
