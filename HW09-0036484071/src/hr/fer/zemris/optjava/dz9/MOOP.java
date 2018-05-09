package hr.fer.zemris.optjava.dz9;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.models.DominationSort;
import hr.fer.zemris.optjava.models.MOOPProblem;
import hr.fer.zemris.optjava.models.NSGA;
import hr.fer.zemris.optjava.models.Problem1;
import hr.fer.zemris.optjava.models.Problem2;
import hr.fer.zemris.optjava.models.ShareValueCalculator;
import hr.fer.zemris.optjava.models.SpaceType;
import hr.fer.zemris.optjava.operators.Chromosome;
import hr.fer.zemris.optjava.operators.Crossover;
import hr.fer.zemris.optjava.operators.ICrossover;
import hr.fer.zemris.optjava.operators.IMutation;
import hr.fer.zemris.optjava.operators.ISelection;
import hr.fer.zemris.optjava.operators.Mutation;
import hr.fer.zemris.optjava.operators.RouletteWheelSelection;

/**
 * Parametri komandne linije: 2 100 decision-space 100
 * 
 * @author Mato
 *
 */
public class MOOP {
	public static double sigmaShare = 1;// parametar funkcije nise
	public static double alpha = 2;// parametar funkcije nise
	public static double sigma = 0.2;// parametar mutacije
	public static double BLXAlpha = 0.4;// parametar krizanja

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 4) {
			System.out.println("Invalid number of arguments.");
			return;
		}
		int function = Integer.parseInt(args[0]);
		int populationSize = Integer.parseInt(args[1]);
		String type = args[2];
		int maxIterations = Integer.parseInt(args[3]);

		MOOPProblem problem = null;
		if (function == 1) {
			problem = new Problem1();
		} else if (function == 2) {
			problem = new Problem2();
		} else {
			System.out.println("Unsuported problem: " + function);
			return;
		}

		ShareValueCalculator calc = null;
		if (type.trim().toLowerCase().equals("decision-space")) {
			calc = new ShareValueCalculator(alpha, sigmaShare, SpaceType.DECISION_SPACE);
		} else if (type.trim().toLowerCase().equals("objective-space")) {
			calc = new ShareValueCalculator(alpha, sigmaShare, SpaceType.OBJECTIVE_SPACE);
		} else {
			System.out.println("Unsuported type of space: " + type);
			return;
		}
		List<Chromosome> population = createPopulation(populationSize, problem);
		IMutation mutation = new Mutation(sigma);
		ICrossover crossover = new Crossover(BLXAlpha);
		ISelection selection = new RouletteWheelSelection();
		NSGA algorithm = new NSGA(maxIterations, population, mutation, crossover, selection, calc);
		population = algorithm.run();

		List<List<Chromosome>> fronts = DominationSort.getFronts(population);
		for (List<Chromosome> front : fronts) {
			for (Chromosome x : front) {
				System.out.println(x);
			}
			System.out.println("============================================================================");
		}
		System.out.println("Velièine populacije po frontama:");
		for (List<Chromosome> front : fronts) {
			System.out.println(front.size());
		}
		writeSolutionsToFiles(fronts);

	}

	private static void writeSolutionsToFiles(List<List<Chromosome>> fronts) throws FileNotFoundException {
		PrintWriter writerS = new PrintWriter("./izlaz-dec.txt");
		PrintWriter writerO = new PrintWriter("./izlaz-obj.txt");
		for (List<Chromosome> front : fronts) {
			for (Chromosome x : front) {
				String str = "";
				for (double d : x.getValues()) {
					str += d + " ";
				}
				writerS.write(str + "\n");
				str = "";
				for (double d : x.getObjectives()) {
					str += d + " ";
				}
				writerO.write(str + "\n");
			}
		}
		writerS.close();
		writerO.close();
	}

	private static List<Chromosome> createPopulation(int populationSize, MOOPProblem problem) {
		Chromosome.maxs = problem.getMaxs();
		Chromosome.mins = problem.getMins();
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			Chromosome x = new Chromosome(problem, problem.getSolutionDimension(), problem.getNumberOfObjectives());
			problem.evaluateSolution(x.getValues(), x.getObjectives());
			population.add(x);
		}
		return population;
	}

}
