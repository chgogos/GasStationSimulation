package solver;

import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import simulation.LogEntry;
import ui.ParetoFront;

public class BiObjectiveSolver {
	List<LogEntry> logEntries;
	int periods;
	int periodLength;
	double start_time;
	double end_time;

	public BiObjectiveSolver(double start_time, double end_time, int periodLength, List<LogEntry> logEntries) {
		this.start_time = start_time;
		this.end_time = end_time;
		this.periodLength = periodLength;
		this.logEntries = logEntries;
		double duration = end_time - start_time;
		periods = (int) duration / periodLength;
		if (periods * periodLength < duration)
			periods++;
	}

	public double getHighEnergyPeriodsObjective(Schedule sol) {
		int sum = 0;
		for (int i = 0; i < periods; i++)
			if (sol.getResourceStatusAt(i) == 1)
				sum++;
		return (double) sum;
	}

	public double getUnhappyCustomersObjective(Schedule sol) {
		int c = 0;
		for (LogEntry le : logEntries) {
			if (!le.non_fuel_service)
				continue;
			if (isUnhappy(le, sol))
				c++;
		}
		return (double) c;
	}

	private boolean isUnhappy(LogEntry le, Schedule sol) {
		int x1 = (int) (le.start_time - start_time) / periodLength;
		int x2 = (int) (le.finish_time - start_time) / periodLength;
		if (sol.getResourceStatusAt(x1) == 0)
			return true;
		if (x2 > periods)
			x2 = periods-1;
		if (sol.getResourceStatusAt(x2) == 0)
			return true;
		return false;
	}

	public void printProblemInfo() {
		double st = start_time;
		for (int i = 0; i < periods; i++) {
			System.out.printf("%.2f-%.2f\n", st, st + periodLength);
			st += periodLength;
		}
	}

	public void solve() {
		MOEA.solver = this;
		NondominatedPopulation result = new Executor().withProblemClass(MOEA.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(10000).run();

		double[][] data = new double[result.size()][2];
		for (int i = 0; i < result.size(); i++) {
			Solution solution = result.get(i);
			double[] objs = solution.getObjectives();
			System.out.printf("Periods in high energy mode:%.0f(%.2f%%)   Unhappy customers:%.0f(%.2f%%)\n", objs[0],
					objs[0] * 100 / periods, objs[1], objs[1] * 100 / logEntries.size());
			data[i][0] = objs[0] * 100 / periods;
			data[i][1] = objs[1] * 100 / logEntries.size();
		}

		ParetoFront pf = new ParetoFront(data);
		pf.setVisible(true);

		// Schedule sol = new Schedule(periods);
		// System.out.printf("NonEconomy:%.0f Unhappy:%.0f\n",
		// getNonEconomyObjective(sol), getUnhappyCustomersObjective(sol));
		// return sol;
	}
}
