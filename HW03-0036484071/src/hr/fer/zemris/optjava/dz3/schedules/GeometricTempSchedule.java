package hr.fer.zemris.optjava.dz3.schedules;

public class GeometricTempSchedule implements ITempSchedules {
	private double alpha;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;

	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit) {

		this.alpha = alpha;
		this.tCurrent = tInitial / alpha;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	}

	@Override
	public double getNextTemperature() {
		tCurrent *= alpha;
		return tCurrent;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

}
