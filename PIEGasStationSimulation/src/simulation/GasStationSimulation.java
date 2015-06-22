package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GasStationSimulation extends Simulation {
	private static long SEED = 1234567890L;
	private Random randomGenerator;

	public List<LogEntry> logEntries;

	private Queue<Car> carQueue;
	private Car[] pumps;

	private int totalCars;
	private double totalTime;
	private double interarrival;
	private double refuelling;
	private double probability_non_fuel_services = 0.0;

	public GasStationSimulation(int numberOfPumps, double interarrival,
			double refuelling, double propability_non_fuel_services) {
		this(numberOfPumps, interarrival, refuelling);
		this.probability_non_fuel_services = propability_non_fuel_services;
	}

	public GasStationSimulation(int numberOfPumps, double interarrival,
			double refuelling) {
		pumps = new Car[numberOfPumps];
		carQueue = new LinkedList<Car>();
		totalCars = 0;
		totalTime = 0;
		this.interarrival = interarrival;
		this.refuelling = refuelling;
		logEntries = new ArrayList<LogEntry>();
		randomGenerator = new Random(SEED);
	}

	public void add(Car c) {
		if (randomGenerator.nextDouble() < probability_non_fuel_services)
			c.non_fuel_service = true;
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
		logEntries.add(new LogEntry(c.id, c.getArrivalTime(), getCurrentTime(),
				c.non_fuel_service));
		if (carQueue.size() > 0) {
			addToPump(i, carQueue.remove());
		}
	}

	public void display() {
		for (int i = 0; i < pumps.length; i++) {
			if (pumps[i] == null) {
				System.out.print("[ X ]");
			} else {
				// System.out.print("C");
				System.out.printf("[%3d]", pumps[i].id);
			}
		}
		System.out.print("|Queue: ");
		Object[] cars = carQueue.toArray();
		int q = carQueue.size();
		for (int j = 1; j <= q; j++) {
			// System.out.print("C");
			System.out.printf("[%3d]", ((Car) cars[j-1]).id);
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

	public void displayLog() {
		Collections.sort(logEntries);
		for (LogEntry le : logEntries) {
			System.out.println(le.toString());
		}
	}

	public double getAverageServiceTime() {
		double averageTime = 0;
		if (totalCars > 0) {
			averageTime = totalTime / totalCars;
		}
		return averageTime;
	}

	public void analyzeLogEntries(int inactivity_minutes) {
		Collections.sort(logEntries);
		double currentTime = logEntries.get(0).start_time;
		double finishTime = logEntries.get(logEntries.size() - 1).finish_time;
		int minutes_without_cars = 0;
		boolean economy = false;
		int unsatisfied_customers = 0;
		int max_minutes_without_cars = 0;
		while (currentTime < finishTime) {
			if (!car_present(currentTime)) {
				minutes_without_cars++;
				if (minutes_without_cars > max_minutes_without_cars)
					max_minutes_without_cars = minutes_without_cars;
			} else {
				minutes_without_cars = 0;
				if (economy)
					unsatisfied_customers++;
			}
			if (minutes_without_cars >= inactivity_minutes)
				economy = true;
			else
				economy = false;
			currentTime++;
		}
		System.out.printf(
				"Unsatisfied customers=%d max inactive period in minutes=%d\n",
				unsatisfied_customers, max_minutes_without_cars);
	}

	public boolean car_present(double time) {
		for (LogEntry le : logEntries) {
			if ((time >= le.start_time) && (time <= le.finish_time))
				return true;
		}
		return false;
	}
}
