package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MMAS {
	public static double alpha = 1.25;
	public static double beta = 2.5;
	public static double a; // jednak dimension
	public static double ro = 0.02;

	private int antsNumber;
	private int maxIterations;
	private double[][] distances;
	private double[][] heuristicInfo;
	private int[][] candidates;
	private int candidateSize;
	private int dimension;

	private Random random = new Random();
	private double[][] pheromone;
	private double tauMin;
	private double tauMax;
	private Ant bestAnt;

	public MMAS(int antsNumber, int maxIterations, double[][] distances, double[][] heuristicInfo, int[][] candidates,
			int candidateSize) {
		super();
		this.antsNumber = antsNumber;
		this.maxIterations = maxIterations;
		this.distances = distances;
		this.heuristicInfo = heuristicInfo;
		this.candidates = candidates;
		this.candidateSize = candidateSize;
		dimension = heuristicInfo.length;
		pheromone = new double[dimension][dimension];
		a = dimension;
	}

	public Ant run() {
		double greedySolution = greedySolution();
		updateTauMinAndMax(greedySolution);
		initPheromone();
		// int stagnation = 0; //detektiranje stagnacije gotovo uopæe ne pomaže,
		// treba previše da krene ponovo davati dobra rješenja
		for (int i = 0; i < maxIterations; i++) {
			Ant iterationBest = createColony();
			updateTauMinAndMax(bestAnt.getDistance());
			// if (iterationBest.getDistance() < bestAnt.getDistance()) {
			// stagnation = 0;
			// } else {
			// stagnation++;
			// if (stagnation > 0.5 * (double) maxIterations) {
			// initPheromone();
			// }
			// }
			evaporatePheromones();
			// u poèetku feromone najèešæe ažuriramo s najboljim mravom u
			// iteraciji, a kasnije iskljuèivo najboljim
			if (i <= 0.5 * maxIterations) {
				double randomValue = random.nextDouble();
				if (randomValue < 0.75) {
					updatePheromones(iterationBest);
				} else {
					updatePheromones(bestAnt);
				}
			} else {
				updatePheromones(bestAnt);
			}

			if (i % 10 == 0) {
				System.out.println("Distance: " + bestAnt.getDistance());
			}
		}

		return bestAnt;
	}

	private void updatePheromones(Ant ant) {
		double delta = 1.0 / ant.getDistance();
		int[] path = ant.getPath().stream().mapToInt(i -> i).toArray();
		for (int i = 0; i < dimension; i++) {
			double tmp = pheromone[path[i]][path[i + 1]];
			pheromone[path[i]][path[i + 1]] = Double.min(tmp + delta, tauMax);
			pheromone[path[i + 1]][path[i]] = Double.min(tmp + delta, tauMax);
		}
	}

	private void evaporatePheromones() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				pheromone[i][j] = Double.max(tauMin, pheromone[i][j] * (1.0 - ro));
			}
		}
	}

	private void initPheromone() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				pheromone[i][j] = tauMax;
			}
		}
	}

	private Ant createColony() {
		Ant iterationBestAnt = null;
		for (int i = 0; i < antsNumber; i++) {
			List<Integer> path = new ArrayList<>();
			double distance = 0;
			int startCity = random.nextInt(dimension);
			Set<Integer> unvisited = new HashSet<>();
			for (int j = 0; j < dimension; j++) {
				unvisited.add(j);
			}
			unvisited.remove(new Integer(startCity));
			path.add(startCity);
			int currentCity = startCity;
			while (!unvisited.isEmpty()) {
				double probabilitiesSum = 0;
				List<Integer> possibleNeighborhoods = new ArrayList<>();
				for (int j = 0; j < candidateSize; j++) {
					int tmp = candidates[currentCity][j];
					if (unvisited.contains(tmp)) {
						possibleNeighborhoods.add(tmp);
						probabilitiesSum += heuristicInfo[currentCity][tmp]
								* Math.pow(pheromone[currentCity][tmp], alpha);
					}
				}

				if (possibleNeighborhoods.size() > 0) {
					double randomValue = random.nextDouble();
					double tmpValue = 0;
					for (Integer possibleNext : possibleNeighborhoods) {
						tmpValue += (heuristicInfo[currentCity][possibleNext]
								* Math.pow(pheromone[currentCity][possibleNext], alpha)) / probabilitiesSum;
						if (tmpValue > randomValue) {
							unvisited.remove(new Integer(possibleNext));
							distance += distances[currentCity][possibleNext];
							path.add(possibleNext);
							currentCity = possibleNext;
							break;
						}
					}
				} else {
					// ovdje sluèajno odabirem neki od preostalih gradova koji
					// nisu meðu kandidatima
					int nextCity = new ArrayList<>(unvisited).get(random.nextInt(unvisited.size()));
					unvisited.remove(new Integer(nextCity));
					distance += distances[currentCity][nextCity];
					path.add(nextCity);
					currentCity = nextCity;
				}
			}
			distance += distances[currentCity][startCity];
			path.add(startCity);
			Ant newAnt = new Ant(distance, path);
			if (iterationBestAnt == null || iterationBestAnt.getDistance() >= newAnt.getDistance()) {
				iterationBestAnt = newAnt;
			}
		}
		if (iterationBestAnt.getDistance() <= bestAnt.getDistance()) {
			bestAnt = iterationBestAnt;
		}
		return iterationBestAnt;
	}

	private void updateTauMinAndMax(double distance) {
		tauMax = 1.0 / ((1.0 - ro) * distance);
		tauMin = tauMax / a;
	}

	private double greedySolution() {
		int startCity = random.nextInt(dimension);
		List<Integer> unvisited = new ArrayList<>();
		for (int i = 0; i < dimension; i++) {
			unvisited.add(i);
		}
		unvisited.remove(new Integer(startCity));
		double solution = 0;
		int currentCity = startCity;
		while (!unvisited.isEmpty()) {
			for (int i = 0; i < dimension - 1; i++) {
				if (unvisited.contains(candidates[currentCity][i])) {
					int nextCity = candidates[currentCity][i];
					solution += distances[currentCity][nextCity];
					unvisited.remove(new Integer(nextCity));
					currentCity = nextCity;
				}
			}
		}
		bestAnt = new Ant(solution, null);
		return solution + distances[currentCity][startCity];
	}

}
