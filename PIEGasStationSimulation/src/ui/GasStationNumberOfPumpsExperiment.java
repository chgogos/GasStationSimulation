package ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import simulation.Arrival;
import simulation.GasStationSimulation;
import simulation.Simulation;

public class GasStationNumberOfPumpsExperiment {
	public static void main(String[] args) {
		double start_time;
		double end_time;
		// int number_of_pumps;
		double interarrival;
		double refuelling;

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			start_time = Integer.parseInt(prop.getProperty(
					"simulation_start_time").trim()) * 60.0;
			end_time = Integer.parseInt(prop.getProperty("simulation_end_time")
					.trim()) * 60.0;
			// number_of_pumps = Integer
			// .parseInt(prop.getProperty("number_of_pumps").trim());
			interarrival = Double.parseDouble(prop.getProperty("interarrival")
					.trim());
			refuelling = Double.parseDouble(prop.getProperty("refuelling")
					.trim());
			input.close();
			GasStationNumberOfPumpsExperiment app = new GasStationNumberOfPumpsExperiment();
			app.mainLoop(start_time, end_time, interarrival, refuelling);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// System.out.printf("Simulation start time: %f\n",start_time);

	}

	public void mainLoop(double start_time, double end_time,
			double interarrival, double refuelling) {
		int[] number_of_pumps = { 2, 3, 4, 5, 6 };
		double[] avg_wait_time = new double[number_of_pumps.length];
		for (int i = 0; i < number_of_pumps.length; i++) {
			Simulation sim = new GasStationSimulation(number_of_pumps[i],
					interarrival, refuelling);
			sim.addEvent(new Arrival(start_time));
			sim.run(start_time, end_time);
			avg_wait_time[i] = sim.getAverageServiceTime();
		}

		for (int i = 0; i < number_of_pumps.length; i++) {
			System.out.printf("Pumps %d --> %.2f\n", number_of_pumps[i],
					avg_wait_time[i]);
		}
	}
}
