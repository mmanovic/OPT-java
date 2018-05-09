package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Crossover {
	private Random random = new Random();

	public Crossover() {
		super();
	}

	public Chromosome cross(Chromosome x, Chromosome y) {
		List<Bin> yBins = y.getBins();
		int lengthY = yBins.size();
		int lengthX = x.getBins().size();
		int crossPoint1 = random.nextInt(lengthY - 1);
		int crossPoint2 = -1;
		while (crossPoint2 <= crossPoint1) {
			crossPoint2 = random.nextInt(lengthY);
		}

		List<Bin> binsFromY = new ArrayList<>();
		Set<Stick> sticksFromY = new HashSet<>();
		// dohvaæamo kante iz y kromosoma izmeðu dvije toèke prijeloma
		for (int i = crossPoint1; i < crossPoint2; i++) {
			Bin duplicate = yBins.get(i).duplicate();
			binsFromY.add(duplicate);
			sticksFromY.addAll(duplicate.getSticks());
		}

		int indexToInsert = random.nextInt(lengthX);// prva toèka prijeloma od
													// kromosoma x
		List<Bin> childBins = new ArrayList<>();
		List<Bin> xBins = x.getBins();
		List<Stick> nonDuplicate = new ArrayList<>();
		for (int i = 0; i < lengthX; i++) {
			// kada naiðemo na toèku prijeloma od x kromosoma dodajemo kante iz
			// y kromosoma
			if (i == indexToInsert) {
				childBins.addAll(binsFromY);
			}
			Bin tmp = xBins.get(i);
			List<Stick> tmpSticks = tmp.getSticks();
			boolean hasDuplicate = false;
			for (Stick stick : tmpSticks) {
				if (sticksFromY.contains(stick)) {
					hasDuplicate = true;
					break;
				}
			}
			// ako kanta nema duplikata dodajemo ju u dijete inaèe vadimo
			// štapiæe i uzimamo one koji nisu duplikati
			if (hasDuplicate) {
				for (Stick stick : tmpSticks) {
					if (!sticksFromY.contains(stick)) {
						nonDuplicate.add(stick);
					}
				}
			} else {
				childBins.add(tmp.duplicate());
			}
		}

		Chromosome child = new Chromosome(x.getFunction(), childBins);
		nonDuplicate.sort(Comparator.reverseOrder());
		child.addSticks(nonDuplicate);
		return child;

	}

}
