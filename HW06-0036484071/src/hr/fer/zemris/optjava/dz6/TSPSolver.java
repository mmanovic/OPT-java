package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Preporuèena konfiguracija: ./problems/bays29.tsp 5 30 3000 Isto vrijedi i za
 * ostale datoteke izuzev pr2392.tsp
 * 
 * Za bays29.tsp najbolje što sam dobio približno 9074, za att48.tsp približno
 * 33600,a za ch150.tsp sam uspio dobit približno 7018.
 * 
 * Za pr2392.tsp sam stavio ovu konfiguraciju ./problems/pr2392.tsp 8 30 500
 * koja se vrti približno dvije minute i rezultat je malo iznad 1200000.
 * 
 * @author Mato
 *
 */
public class TSPSolver {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Invalid number of arguments.");
			return;
		}
		Path file = Paths.get(args[0]);
		if (!Files.exists(file)) {
			System.out.println("File " + file.toString() + " doesn't exist.");
			return;
		}
		List<String> lines;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Unable to open file.");
			return;
		}
		int candidateSize = Integer.parseInt(args[1]);
		int antsNumber = Integer.parseInt(args[2]);
		int maxIterations = Integer.parseInt(args[3]);

		double[][] distances;
		double[][] heuristicInfo;
		int[][] candidates;

		int limit = lines.size();
		int dimension = -1;
		List<String> data = null;
		for (int i = 0; i < limit; i++) {
			String line = lines.get(i);
			if (line.startsWith("DIMENSION")) {
				String[] tmp = line.split(":");
				dimension = Integer.parseInt(tmp[1].trim());
			}
			if (line.startsWith("NODE_COORD_SECTION") || line.startsWith("DISPLAY_DATA_SECTION")) {
				data = lines.subList(i + 1, limit - 1);
				break;
			}
		}
		distances = new double[dimension][dimension];
		heuristicInfo = new double[dimension][dimension];
		double[][] coordinates = new double[dimension][2];
		int k = 0;
		for (String line : data) {
			String[] tmp = line.trim().split("\\s+");
			coordinates[k][0] = Double.parseDouble(tmp[1]);
			coordinates[k][1] = Double.parseDouble(tmp[2]);
			k++;
		}
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j <= i; j++) {
				distances[i][j] = distances[j][i] = calculateDistance(coordinates[i], coordinates[j]);
				heuristicInfo[i][j] = heuristicInfo[j][i] = Math.pow((1.0 / distances[i][j]), MMAS.beta);
			}
		}

		candidates = new int[dimension][dimension - 1];
		for (int i = 0; i < dimension; i++) {
			Map<Integer, Double> tmp = new HashMap<>();
			for (int j = 0; j < dimension; j++) {
				if (i != j) {
					tmp.put(j, distances[i][j]);
				}
			}
			List<Entry<Integer, Double>> list = new ArrayList<>(tmp.entrySet());
			list.sort((x, y) -> Double.compare(x.getValue(), y.getValue()));
			for (int l = 0; l < dimension - 1; l++) {
				candidates[i][l] = list.get(l).getKey();
			}
		}
		System.out.println("Final solution:\n"
				+ new MMAS(antsNumber, maxIterations, distances, heuristicInfo, candidates, candidateSize).run());
	}

	private static double calculateDistance(double[] first, double[] second) {
		double x = Math.abs(first[0] - second[0]);
		double y = Math.abs(first[1] - second[1]);
		return Math.sqrt(x * x + y * y);
	}

}
