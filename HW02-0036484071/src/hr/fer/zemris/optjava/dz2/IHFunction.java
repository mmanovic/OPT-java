package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IHFunction extends IFunction {
	public Matrix getHesseMatrix(Matrix matrix);
}
