package hr.fer.zemris.optjava.dz12.GUI;

import java.awt.GridLayout;

import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class AntWorldCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private AntWorldField[][] fields;

	public AntWorldCanvas(AntWorld world) {
		super();
		int height = world.getWorldHeight();
		int width = world.getWorldWidth();
		fields = new AntWorldField[height][width];
		setLayout(new GridLayout(height, width));
		boolean[][] foodMap = world.getFoodMap();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				fields[i][j] = new AntWorldField(foodMap[i][j], null);
				add(fields[i][j], i * width + j);
			}
		}
		fields[world.getAntRow()][world.getAntColumn()].setDirection(world.getDirection());
	}

	public void refresh(AntWorld world) {
		int height = world.getWorldHeight();
		int width = world.getWorldWidth();

		boolean[][] foodMap = world.getFoodMap();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				fields[i][j].setHasFood(foodMap[i][j]);
				fields[i][j].setDirection(null);
			}
		}
		fields[world.getAntRow()][world.getAntColumn()].setDirection(world.getDirection());
	}

}
