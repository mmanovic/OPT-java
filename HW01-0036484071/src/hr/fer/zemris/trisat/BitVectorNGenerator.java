package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	private BitVector assignment;
	private int currIndex;

	public BitVectorNGenerator(BitVector assignment) {
		super();
		this.assignment = assignment;
		currIndex = 0;
	}

	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Iterator<MutableBitVector>() {

			@Override
			public boolean hasNext() {
				return currIndex < assignment.getSize();
			}

			@Override
			public MutableBitVector next() {
				MutableBitVector vector = assignment.copy();
				vector.set(currIndex, !vector.get(currIndex++));
				return vector;
			}
		};
	}

	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] neighborhoods = new MutableBitVector[assignment.getSize()];
		Iterator<MutableBitVector> iterator = new BitVectorNGenerator(assignment.copy()).iterator();
		int length = neighborhoods.length;
		for (int i = 0; i < length; i++) {
			neighborhoods[i] = iterator.next();
		}
		return neighborhoods;
	}

}
