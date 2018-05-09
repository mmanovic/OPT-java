package hr.fer.zemris.optjava.dz11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Chromosome;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.GenerationAlgorithm1;
import hr.fer.zemris.generic.ga.operators.ICrossover;
import hr.fer.zemris.generic.ga.operators.IMutation;
import hr.fer.zemris.generic.ga.operators.ISelection;
import hr.fer.zemris.generic.ga.operators.OnePointCrossover;
import hr.fer.zemris.generic.ga.operators.SingleMutation;
import hr.fer.zemris.generic.ga.operators.TournamentSelection;
import hr.fer.zemris.generic.ga.operators.UniformCrossover;
import hr.fer.zemris.generic.ga.operators.UniformMutation;
import hr.fer.zemris.optjava.rng.EVOThread;

/**
 * Preporuceni argumenti komandne linije: kuca.png 200 25 12000 300000 izlaz.txt aprox.png
 * 
 * @author Mato
 *
 */
public class Pokretac1 {
	public static int TOURNAMENT_SIZE = 4;
	public static double backgroundChangeProb = 0.1;
	public static double sigma = 5;
	public static double rectangleMutationRate = 0.005;

	public static void main(String[] args) throws IOException {
		File inputFile = new File(args[0]);
		int nOfRectangles = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		int maxGenerations = Integer.parseInt(args[3]);
		double minFitness = Double.parseDouble(args[4]);
		File txtOutputFile = new File(args[5]);
		File outputFile = new File(args[6]);

		GrayScaleImage template = GrayScaleImage.load(inputFile);

		Evaluator evaluator = new Evaluator(template);
		List<GASolution<int[]>> population = createPopulation(populationSize, nOfRectangles, template);
		ISelection<int[]> selection = new TournamentSelection(TOURNAMENT_SIZE);

		@SuppressWarnings("unchecked")
		ICrossover<int[]>[] crossovers = (ICrossover<int[]>[]) new ICrossover[2];
		@SuppressWarnings("unchecked")
		IMutation<int[]>[] mutations = (IMutation<int[]>[]) new IMutation[2];
		mutations[0] = new SingleMutation(backgroundChangeProb, sigma, template.getWidth(), template.getHeight(),
				nOfRectangles);
		crossovers[0] = new OnePointCrossover(nOfRectangles);
		crossovers[1] = new UniformCrossover();
		mutations[1] = new UniformMutation(rectangleMutationRate, template.getWidth(), template.getHeight());

		GenerationAlgorithm1<int[]> algorithm = new GenerationAlgorithm1<>(maxGenerations, minFitness, population,
				evaluator, selection, crossovers, mutations);

		Runnable job = new Runnable() {

			@Override
			public void run() {
				GASolution<int[]> best;
				try {
					best = algorithm.run();
					GrayScaleImage outPicture = new GrayScaleImage(template.getWidth(), template.getHeight());
					evaluator.draw(best, outPicture);
					outPicture.save(outputFile);

					FileWriter fw = new FileWriter(txtOutputFile);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(best.toString());
					bw.close();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}

			}
		};
		Thread thread = new EVOThread(job);
		thread.start();

	}

	private static List<GASolution<int[]>> createPopulation(int populationSize, int nOfRectangles,
			GrayScaleImage template) {
		List<GASolution<int[]>> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(nOfRectangles, template.getHeight(), template.getWidth()));
		}
		return population;
	}

}
