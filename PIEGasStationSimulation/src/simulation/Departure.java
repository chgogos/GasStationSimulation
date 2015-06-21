package simulation;
/**
 * This event describes a Car departing from a pump.
 */
public class Departure extends Event {
	private int pump;

	/**
	 * @param time
	 *            the departure time
	 * @param pump
	 *            the pump serving the car
	 */
	public Departure(double time, int pump) {
		super(time);
		this.pump = pump;
	}

	public void process(Simulation sim) {
		GasStationSimulation gasStation = (GasStationSimulation) sim;
		gasStation.remove(pump);
	}
}
