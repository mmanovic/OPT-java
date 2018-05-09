package hr.fer.zemris.optjava.dz7.pso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.optjava.dz7.models.IFunction;

public class Swarm implements Iterable<Particle> {
	private List<Particle> swarm;

	private int swarmSize;
	private int neighborhoodSize;
	private int dimension;
	private IFunction function;

	public Swarm(int swarmSize, int neighborhoodSize, int dimension, IFunction function) {
		super();
		this.function = function;
		this.swarmSize = swarmSize;
		this.neighborhoodSize = neighborhoodSize;
		this.dimension = dimension;
		swarm = new ArrayList<>(swarmSize);
		initialize();
	}

	public void updateParticlesSpeed(double inertion) {
		for (Particle particle : swarm) {
			particle.updateSpeed(inertion);
		}
	}

	private void initialize() {
		for (int i = 0; i < swarmSize; i++) {
			Particle particle = new Particle(dimension, function);
			particle.randomize();
			swarm.add(particle);
		}
		if (neighborhoodSize >= swarmSize / 2) {
			// globalno susjedstvo
			for (Particle particle : swarm) {
				particle.setNeighborhoods(swarm);
			}
		} else {
			for (int i = 0; i < swarmSize; i++) {
				for (int j = -neighborhoodSize; j <= neighborhoodSize; j++) {
					int index = (i + j + swarmSize) % swarmSize;
					swarm.get(i).getNeighborhoods().add(swarm.get(index));
				}
			}
		}
	}

	@Override
	public Iterator<Particle> iterator() {
		return swarm.iterator();
	}

}
