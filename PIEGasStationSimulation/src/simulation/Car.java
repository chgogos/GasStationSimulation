package simulation;

public class Car {
	int id;
	boolean non_fuel_service=false;
	private double arrivalTime; // time at which the car entered the gas station

	public Car(double time, int id) {
		this.id = id;
		arrivalTime = time;
	}

	double getArrivalTime() {
		return arrivalTime;
	}
}
