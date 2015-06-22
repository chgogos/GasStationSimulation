package ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import simulation.Arrival;
import simulation.GasStationSimulation;
import simulation.Simulation;

public class CustomerSatisfactionExperiment {
	public static void main(String[] args) {
		double start_time;
		double end_time;
		int number_of_pumps;
		double interarrival;
		double refuelling;
		double probability_of_non_fuel_services;

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config2.properties");
			prop.load(input);
			start_time = Integer.parseInt(prop.getProperty(
					"simulation_start_time").trim()) * 60.0;
			end_time = Integer.parseInt(prop.getProperty("simulation_end_time")
					.trim()) * 60.0;
			number_of_pumps = Integer.parseInt(prop.getProperty(
					"number_of_pumps").trim());
			interarrival = Double.parseDouble(prop.getProperty("interarrival")
					.trim());
			refuelling = Double.parseDouble(prop.getProperty("refuelling")
					.trim());
			probability_of_non_fuel_services = Double.parseDouble(prop
					.getProperty("probability_non_fuel_services").trim());
			input.close();
			CustomerSatisfactionExperiment app = new CustomerSatisfactionExperiment();
			app.mainLoop(start_time, end_time, number_of_pumps, interarrival,
					refuelling, probability_of_non_fuel_services);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// System.out.printf("Simulation start time: %f\n",start_time);

	}

	// after 10 minutes of no cars mini market turns to economy mode
	// if a customer finds the mini-market in economy he is dissatisfied
	public void mainLoop(double start_time, double end_time,
			int number_of_pumps, double interarrival, double refuelling,
			double probability_non_fuel_services) {
		Simulation sim = new GasStationSimulation(number_of_pumps,
				interarrival, refuelling, probability_non_fuel_services);
		sim.addEvent(new Arrival(start_time));
		sim.run(start_time, end_time);
		sim.displayLog();
		sim.analyzeLogEntries(10);
	}

}
