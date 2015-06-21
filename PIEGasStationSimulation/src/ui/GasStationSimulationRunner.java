package ui;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import simulation.Arrival;
import simulation.GasStationSimulation;
import simulation.Simulation;

public class GasStationSimulationRunner {
	public static void main(String[] args) {
		double start_time;
		double end_time;
		int number_of_pumps;
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
			number_of_pumps = Integer
					.parseInt(prop.getProperty("number_of_pumps").trim());
			interarrival = Double.parseDouble(prop.getProperty("interarrival")
					.trim());
			refuelling = Double.parseDouble(prop.getProperty("refuelling")
					.trim());
			input.close();
			GasStationSimulationRunner app = new GasStationSimulationRunner();
			app.mainLoop(start_time, end_time, number_of_pumps, interarrival, refuelling);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
//		System.out.printf("Simulation start time: %f\n",start_time);
		
	}

	public void mainLoop(double start_time, double end_time,
			int number_of_pumps, double interarrival, double refuelling) {
		Simulation sim = new GasStationSimulation(number_of_pumps,
				interarrival, refuelling);
		sim.addEvent(new Arrival(start_time));
		sim.run(start_time, end_time);
	}
}
