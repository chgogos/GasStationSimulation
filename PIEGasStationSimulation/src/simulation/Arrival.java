package simulation;
public class Arrival extends Event {

	public Arrival(double time) {
		super(time);
	}

	public void process(Simulation sim) {
		double now = sim.getCurrentTime();
		GasStationSimulation bank = (GasStationSimulation) sim;
		Car c = new Car(now);
		bank.add(c);
	}
}
