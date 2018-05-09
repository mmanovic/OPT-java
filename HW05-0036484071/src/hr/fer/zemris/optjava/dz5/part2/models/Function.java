package hr.fer.zemris.optjava.dz5.part2.models;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.optjava.dz5.part2.models.Chromosome;

public class Function {
	private int n;
	private int[][] distance;
	private int[][] transport;

	public Function(int n, int[][] distance, int[][] transport) {
		super();
		this.n = n;
		this.distance = distance;
		this.transport = transport;
	}

	public int getN() {
		return n;
	}

	public Function(String path) {
		try {
			Scanner scanner = new Scanner(Paths.get(path));
			n = scanner.nextInt();
			distance = new int[n][n];
			transport = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					distance[i][j] = scanner.nextInt();
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					transport[i][j] = scanner.nextInt();
				}
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("Cannot open file.");
		}
	}

	public double valueAt(Chromosome x) {
		int[] distribution = x.getDistribution();
		double value = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				value += transport[i][j] * distance[distribution[i]][distribution[j]];
			}
		}
		return value;
	}

}
