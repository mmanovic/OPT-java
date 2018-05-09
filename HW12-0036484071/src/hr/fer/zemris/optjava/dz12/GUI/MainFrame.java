package hr.fer.zemris.optjava.dz12.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import hr.fer.zemris.optjava.dz12.models.AntWorld;
import hr.fer.zemris.optjava.dz12.nodes.INode;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private AntWorld worldInit;
	private AntWorld world;
	private AntWorldCanvas canvas;
	private Timer timer;
	private INode[] actionArray;
	private int pointer = 0;

	public MainFrame(AntWorld world, INode[] actionArray) {
		this.actionArray = actionArray;
		this.worldInit = world;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
			}
		});
		setLocation(100, 100);
		setSize(640, 560);
		setTitle("AntWorld window");
		getContentPane().setLayout(new BorderLayout());
		initGUI();
	}

	private void initGUI() {

		canvas = new AntWorldCanvas(worldInit);
		world = worldInit.copy();
		getContentPane().add(canvas, BorderLayout.CENTER);
		timer = new Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pointer >= 0 && pointer < actionArray.length) {
					actionArray[pointer++].execute(world, false);
					canvas.refresh(world);
				}
				canvas.validate();
				canvas.repaint();
			}
		});

		JPanel buttonsPanel = new JPanel();

		JButton nextButton = new JButton("Next");
		JButton autoButton = new JButton("Auto");
		JButton pauseButton = new JButton("Pause");
		JButton resetButton = new JButton("Reset");
		pauseButton.setEnabled(false);

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pointer >= 0 && pointer < actionArray.length) {
					actionArray[pointer++].execute(world, false);
					canvas.refresh(world);
				}
				canvas.validate();
				canvas.repaint();
			}
		});

		autoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!timer.isRunning()) {
					timer.start();
					nextButton.setEnabled(false);
					pauseButton.setEnabled(true);
					autoButton.setEnabled(false);
				}
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					timer.stop();
					nextButton.setEnabled(true);
					autoButton.setEnabled(false);
				} else {
					nextButton.setEnabled(false);
					timer.start();
				}
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					timer.stop();
				}
				pointer = 0;
				world = worldInit.copy();
				pauseButton.setEnabled(false);
				autoButton.setEnabled(true);
				nextButton.setEnabled(true);
				canvas.refresh(worldInit);
				canvas.validate();
				canvas.repaint();
			}
		});

		buttonsPanel.add(nextButton);
		buttonsPanel.add(autoButton);
		buttonsPanel.add(pauseButton);
		buttonsPanel.add(resetButton);

		add(buttonsPanel, BorderLayout.SOUTH);
	}
}
