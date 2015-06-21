package simulation;
public class Car {
	private double arrivalTime; // time at which the car entered the gas station

	public Car(double time) {
		arrivalTime = time;
	}

	double getArrivalTime() {
		return arrivalTime;
	}
}
