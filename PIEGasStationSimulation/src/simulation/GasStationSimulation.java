package simulation;

import java.util.LinkedList;
import java.util.Queue;

public class GasStationSimulation extends Simulation {

	private Queue<Car> carQueue;
	private Car[] pumps;

	private int totalCars;
	private double totalTime;
	private double interarrival;
	private double refuelling;

	public GasStationSimulation(int numberOfPumps, double interarrival,
			double refuelling) {
		pumps = new Car[numberOfPumps];
		carQueue = new LinkedList<Car>();
		totalCars = 0;
		totalTime = 0;
		this.interarrival = interarrival;
		this.refuelling = refuelling;
	}

	public void add(Car c) {
		boolean addedToPump = false;
		for (int i = 0; !addedToPump && i < pumps.length; i++) {
			if (pumps[i] == null) {
				addToPump(i, c);
				addedToPump = true;
			}
		}
		if (!addedToPump) {
			carQueue.add(c);
		}

		addEvent(new Arrival(getCurrentTime() + expdist(interarrival)));
	}

	private void addToPump(int i, Car c) {
		pumps[i] = c;
		addEvent(new Departure(getCurrentTime() + expdist(refuelling), i));
	}

	public void remove(int i) {
		Car c = pumps[i];
		pumps[i] = null;

		// Update statistics
		totalCars++;
		totalTime = totalTime + getCurrentTime() - c.getArrivalTime();

		if (carQueue.size() > 0) {
			addToPump(i, carQueue.remove());
		}
	}

	public void display() {
		for (int i = 0; i < pumps.length; i++) {
			if (pumps[i] == null) {
				System.out.print(".");
			} else {
				System.out.print("C");
			}
		}
		System.out.print("<");
		int q = carQueue.size();
		for (int j = 1; j <= q; j++) {
			System.out.print("C");
		}
		System.out.println();
	}

	public void displaySummary() {
		double averageTime = 0;
		if (totalCars > 0) {
			averageTime = totalTime / totalCars;
		}
		System.out.println(totalCars + " cars. Average time " + averageTime
				+ " minutes.");
	}

	public double getAverageServiceTime() {
		double averageTime = 0;
		if (totalCars > 0) {
			averageTime = totalTime / totalCars;
		}
		return averageTime;
	}
}
