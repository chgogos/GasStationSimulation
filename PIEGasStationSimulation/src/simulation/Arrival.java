package simulation;
public class Arrival extends Event {
	static int ID = 0;
	public Arrival(double time) {
		super(time);
	}

	public void process(Simulation sim) {
		double now = sim.getCurrentTime();
		GasStationSimulation gasStation = (GasStationSimulation) sim;
		Car c = new Car(now, ++ID);
		gasStation.add(c);
	}
}
