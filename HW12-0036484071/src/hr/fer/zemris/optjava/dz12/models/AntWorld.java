package hr.fer.zemris.optjava.dz12.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz12.nodes.INode;

public class AntWorld {
	private boolean[][] foodMap;
	private AntDirection direction = AntDirection.RIGHT;
	private int worldWidth;
	private int worldHeight;
	private int antColumn = 0;
	private int antRow = 0;
	public int movesLeft = 600;
	public int foodEaten;
	public List<INode> actionList = new ArrayList<>();

	public AntWorld(Path file, int MaxMoves) {
		movesLeft = MaxMoves;
		if (!Files.exists(file)) {
			System.out.println("File " + file.toString() + " doesn't exist.");
			System.exit(1);
		}
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Unable to open file.");
			System.exit(1);
		}

		String dimension = lines.remove(0);
		String[] tmp = dimension.trim().split("x");
		worldHeight = Integer.parseInt(tmp[0]);
		worldWidth = Integer.parseInt(tmp[1]);
		foodMap = new boolean[worldHeight][worldWidth];
		for (int i = 0; i < worldHeight; i++) {
			char[] charLine = lines.get(i).toCharArray();
			for (int j = 0; j < worldWidth; j++) {
				if (charLine[j] == '.' || charLine[j] == '0') {
					foodMap[i][j] = false;
				} else {
					foodMap[i][j] = true;
				}
			}
		}
		if (foodMap[0][0]) {
			foodEaten = 1;
			foodMap[0][0] = false;
		} else {
			foodEaten = 0;
		}
	}

	private AntWorld() {

	}

	public AntWorld copy() {
		AntWorld copy = new AntWorld();
		copy.foodEaten = foodEaten;
		copy.worldHeight = worldHeight;
		copy.worldWidth = worldWidth;
		copy.foodMap = new boolean[worldHeight][worldWidth];
		for (int i = 0; i < worldHeight; i++) {
			for (int j = 0; j < worldWidth; j++) {
				copy.foodMap[i][j] = foodMap[i][j];
			}
		}
		copy.direction = direction;
		return copy;
	}

	public void move() {
		switch (direction) {
		case UP:
			antRow--;
			if (antRow < 0) {
				antRow += worldHeight;
			}
			break;
		case DOWN:
			antRow = (antRow + 1) % worldHeight;
			break;
		case LEFT:
			antColumn--;
			if (antColumn < 0) {
				antColumn += worldWidth;
			}
			break;
		case RIGHT:
			antColumn = (antColumn + 1) % worldWidth;
			break;
		}
		if (foodMap[antRow][antColumn]) {
			foodEaten++;
			foodMap[antRow][antColumn] = false;
		}

	}

	public boolean foodAhead() {
		int antColumnAhead = antColumn;
		int antRowAhead = antRow;
		switch (direction) {
		case UP:
			antRowAhead--;
			if (antRowAhead < 0) {
				antRowAhead += worldHeight;
			}
			break;
		case DOWN:
			antRowAhead = (antRowAhead + 1) % worldHeight;
			break;
		case LEFT:
			antColumnAhead--;
			if (antColumnAhead < 0) {
				antColumnAhead += worldWidth;
			}
			break;
		case RIGHT:
			antColumnAhead = (antColumnAhead + 1) % worldWidth;
			break;
		}
		return foodMap[antRowAhead][antColumnAhead];
	}

	public AntDirection getDirection() {
		return direction;
	}

	public void setDirection(AntDirection direction) {
		this.direction = direction;
	}

	public int getAntColumn() {
		return antColumn;
	}

	public void setAntColumn(int antColumn) {
		this.antColumn = antColumn;
	}

	public int getAntRow() {
		return antRow;
	}

	public void setAntRow(int antRow) {
		this.antRow = antRow;
	}

	public boolean[][] getFoodMap() {
		return foodMap;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

}
