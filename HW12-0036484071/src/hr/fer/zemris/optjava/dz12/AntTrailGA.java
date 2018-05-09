package hr.fer.zemris.optjava.dz12;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.dz12.GAmodels.Chromosome;
import hr.fer.zemris.optjava.dz12.GUI.MainFrame;
import hr.fer.zemris.optjava.dz12.models.AntWorld;
import hr.fer.zemris.optjava.dz12.nodes.INode;

/**
 * @author Mato
 * 
 *         Parametri za komandnu liniju: 13-SantaFeAntTrail.txt 100 1000 89 bestSolution.txt
 * 
 *         Algoritam èešæe nade najoptimalnije rješenje s veæom populacijom npr.
 *         1000 ili 1500 uz manji ili jednak broj generacija tj. bolji parametri bi bili 
 *         13-SantaFeAntTrail.txt 50 1500 89 bestSolution.txt
 *
 */
public class AntTrailGA {
	public static int MAX_MOVES = 600;

	public static void main(String[] args) throws IOException {
		if (args.length != 5) {
			System.out.println("Invalid number of arguments!");
			return;
		}
		Path map = Paths.get(args[0]);
		int maxGenerations = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		double minFitness = Double.parseDouble(args[3]);
		Path bestSolutionFile = Paths.get(args[4]);

		AntWorld worldinit = new AntWorld(map, MAX_MOVES);

		GenerationAlgorithm algorithm = new GenerationAlgorithm(populationSize, maxGenerations, minFitness,
				worldinit.copy());
		Chromosome best = algorithm.run();
		AntWorld world = worldinit.copy();
		best.evaluate(world, true);

		FileWriter fw = new FileWriter(bestSolutionFile.toFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Util.printTree(best.getRoot(), 0, new StringBuilder()));
		bw.close();

		INode[] actionArray = new INode[world.actionList.size()];
		for (int i = 0; i < actionArray.length; i++) {
			actionArray[i] = world.actionList.get(i);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame(worldinit, actionArray).setVisible(true);
			}
		});
	}

}
