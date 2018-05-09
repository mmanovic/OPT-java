package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;

public interface IGAEvaluator<T> {
	public void evaluate(GASolution<T> p);

	public GrayScaleImage getTemplate();
}
