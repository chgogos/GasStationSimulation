package simulation;

public class LogEntry implements Comparable<LogEntry> {
	int car_id;
	public double start_time;
	public double finish_time;
	public boolean non_fuel_service;

	public LogEntry(int car_id, double start_time, double finish_time,
			boolean nfs) {
		this.car_id = car_id;
		this.start_time = start_time;
		this.finish_time = finish_time;
		this.non_fuel_service = nfs;
	}

	public int getHour(double x) {
		return (int) (x / 60.0);
	}

	public double getMinute(double x) {
		return x - getHour(x) * 60;
	}

	public String toString() {
		return String
				.format("Car:%d arrival: %d:%.1f departure:%d:%.1f service time:%.2f mini_market=%s",
						car_id, getHour(start_time), getMinute(start_time),
						getHour(finish_time), getMinute(finish_time),
						finish_time - start_time, non_fuel_service ? "Yes"
								: "No");

	}

	public int compareTo(LogEntry compareFruit) {

		int compareQuantity = compareFruit.car_id;

		return this.car_id - compareQuantity;

	}
}
