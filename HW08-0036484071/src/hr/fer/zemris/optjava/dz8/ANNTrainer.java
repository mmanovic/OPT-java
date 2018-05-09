package hr.fer.zemris.optjava.dz8;

import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.optjava.dz8.algorithm.DifferentialEvolution;
import hr.fer.zemris.optjava.dz8.models.IDataset;
import hr.fer.zemris.optjava.dz8.models.Util;
import hr.fer.zemris.optjava.dz8.neural.AbstractANN;
import hr.fer.zemris.optjava.dz8.neural.Elman;
import hr.fer.zemris.optjava.dz8.neural.ITransferFunction;
import hr.fer.zemris.optjava.dz8.neural.TANHFunction;
import hr.fer.zemris.optjava.dz8.neural.TDNN;

/**
 * @author Mato
 * 
 *         Primjer konfiguracije za tdnn: ./laser-data.txt tdnn-8x10x1 30 0.001 200
 * 
 *         Za elmanovu mrežu greška mi ne ide ispod 0.11 tj. slièna je greški za
 *         tdnn mrežu ako ulazni sloj ima 1 neuron, tj. context nema nikakvog
 *         posebnog uèinka. 
 *
 */
public class ANNTrainer {
	public static int dataCount = 600;
	public static double F = 0.5;
	public static double Cr = 0.03;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Invalid number of arguments!");
			return;
		}
		Path file = Paths.get(args[0]);
		String typeOfNetwork = args[1];
		int populationSize = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxIteration = Integer.parseInt(args[4]);

		AbstractANN ann = null;
		if (typeOfNetwork.toLowerCase().startsWith(("tdnn-"))) {
			int[] architecture = parseArchitecture(typeOfNetwork.substring(5));
			ITransferFunction[] tanhFunctionsArray = createTANHS(architecture.length - 1);
			IDataset dataset = Util.loadDataset(file, architecture[0], dataCount);
			ann = new TDNN(architecture, tanhFunctionsArray, dataset);
		} else if (typeOfNetwork.toLowerCase().startsWith(("elman-"))) {
			int[] architecture = parseArchitecture(typeOfNetwork.substring(6));
			ITransferFunction[] tanhFunctionsArray = createTANHS(architecture.length - 1);
			IDataset dataset = Util.loadDataset(file, architecture[0], dataCount);
			ann = new Elman(architecture, tanhFunctionsArray, dataset);
		}

		DifferentialEvolution de = new DifferentialEvolution(ann, merr, populationSize, maxIteration, F, Cr);
		de.run();

	}

	private static ITransferFunction[] createTANHS(int i) {
		ITransferFunction[] functions = new ITransferFunction[i];
		for (int j = 0; j < i; j++) {
			functions[j] = new TANHFunction();
		}
		return functions;
	}

	private static int[] parseArchitecture(String arh) {
		String[] tmp = arh.trim().split("x");
		int[] architecture = new int[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			architecture[i] = Integer.parseInt(tmp[i]);
		}
		return architecture;

	}

}
