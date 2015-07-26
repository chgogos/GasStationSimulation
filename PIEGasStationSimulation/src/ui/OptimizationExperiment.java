package ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import simulation.Arrival;
import simulation.GasStationSimulation;
import simulation.Simulation;
import solver.BiObjectiveSolver;
import solver.Schedule;

public class OptimizationExperiment {
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
			start_time = Integer.parseInt(prop.getProperty("simulation_start_time").trim()) * 60.0;
			end_time = Integer.parseInt(prop.getProperty("simulation_end_time").trim()) * 60.0;
			number_of_pumps = Integer.parseInt(prop.getProperty("number_of_pumps").trim());
			interarrival = Double.parseDouble(prop.getProperty("interarrival").trim());
			refuelling = Double.parseDouble(prop.getProperty("refuelling").trim());
			probability_of_non_fuel_services = Double
					.parseDouble(prop.getProperty("probability_non_fuel_services").trim());
			input.close();
			OptimizationExperiment app = new OptimizationExperiment();
			app.mainLoop(start_time, end_time, number_of_pumps, interarrival, refuelling,
					probability_of_non_fuel_services);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mainLoop(double start_time, double end_time, int number_of_pumps, double interarrival,
			double refuelling, double probability_non_fuel_services) {
		Simulation sim = new GasStationSimulation(number_of_pumps, interarrival, refuelling,
				probability_non_fuel_services);
		sim.addEvent(new Arrival(start_time));
		sim.run(start_time, end_time);
		sim.displayLog();
		int periodLength = 15;
		BiObjectiveSolver solver = new BiObjectiveSolver(start_time, end_time, periodLength,
				((GasStationSimulation) sim).logEntries);
		solver.solve();
		
	}

}
