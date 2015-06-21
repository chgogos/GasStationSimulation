package simulation;

import java.util.PriorityQueue;

public class Simulation {
	private PriorityQueue<Event> eventQueue;
	private double currentTime;

	public Simulation() {
		eventQueue = new PriorityQueue<Event>();
	}

	public double getCurrentTime() {
		return currentTime;
	}

	/**
	 * Compute exponentially distributed random numbers.
	 * 
	 * @param mean
	 *            the mean of the number sequence
	 * @return a random number
	 */
	public static double expdist(double mean) {
		return -mean * Math.log(1 - Math.random());
	}

	public void addEvent(Event evt) {
		eventQueue.add(evt);
	}

	public void display() {

	}

	public double getAverageServiceTime() {
		return 0.0;
	}

	public void displaySummary() {
	}

	public void run(double startTime, double endTime) {
		currentTime = startTime;
		while (eventQueue.size() > 0 && currentTime <= endTime) {
			Event event = eventQueue.remove();
			currentTime = event.getTime();
			event.process(this);
			display();
		}
		displaySummary();
	}
}
