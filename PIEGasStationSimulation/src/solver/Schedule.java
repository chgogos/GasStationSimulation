package solver;

import java.util.Arrays;
import java.util.Random;

public class Schedule {
	int[] genes;

	public Schedule(int length) {
		Random random = new Random();
		genes = random.ints(0, 2).limit(length).toArray();
	}

	public Schedule(boolean[] b) {
		genes = new int[b.length];
		for (int i = 0; i < b.length; i++)
			if (b[i])
				genes[i] = 1;
			else
				genes[i] = 0;
	}

	public int getResourceStatusAt(int period) {
		return genes[period];
	}

	public String toString() {
		return genes.length + ": " + Arrays.toString(genes);
	}

}
