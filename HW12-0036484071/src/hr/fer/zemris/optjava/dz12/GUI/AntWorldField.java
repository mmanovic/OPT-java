package hr.fer.zemris.optjava.dz12.GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.optjava.dz12.models.AntDirection;

public class AntWorldField extends JComponent {
	private static final long serialVersionUID = 1L;
	private boolean hasFood;
	private AntDirection direction;

	public AntWorldField(boolean hasFood, AntDirection direction) {
		super();
		this.hasFood = hasFood;
		this.direction = direction;
		setBorder(BorderFactory.createLineBorder(Color.RED, 1));
	}

	public boolean isHasFood() {
		return hasFood;
	}

	public void setHasFood(boolean hasFood) {
		this.hasFood = hasFood;
	}

	public AntDirection getDirection() {
		return direction;
	}

	public void setDirection(AntDirection direction) {
		this.direction = direction;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (hasFood) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.YELLOW);
		}
		int width = getWidth();
		int height = getHeight();
		g.fillRect(0, 0, width, height);

		if (direction != null) {
			g.setColor(Color.BLACK);
			switch (direction) {
			case UP:
				g.fillPolygon(new int[] { width / 2, width, 0 }, new int[] { 0, height, height }, 3);
				break;
			case DOWN:
				g.fillPolygon(new int[] { width / 2, width, 0 }, new int[] { height, 0, 0 }, 3);
				break;
			case LEFT:
				g.fillPolygon(new int[] { 0, width, width }, new int[] { height / 2, 0, height }, 3);
				break;
			case RIGHT:
				g.fillPolygon(new int[] { width, 0, 0 }, new int[] { height / 2, 0, height }, 3);
				break;
			}
		}

	}
}
