package solver;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class MOEA extends AbstractProblem {

	public static BiObjectiveSolver solver;

	public MOEA() {
		super(1, 2);
	}


	@Override
	public void evaluate(Solution solution) {
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		double[] objs = new double[numberOfObjectives];
		Schedule aSchedule = new Schedule(d);
		objs[0] = solver.getHighEnergyPeriodsObjective(aSchedule);
		objs[1] = solver.getUnhappyCustomersObjective(aSchedule);
//		System.out.printf("Periods in high energy mode:%.2f  Unhappy customers:%.2f\n", objs[0], objs[1]);
		solution.setObjectives(objs);
	}

	@Override
	public Solution newSolution() {
		Solution sol = new Solution(numberOfVariables, numberOfObjectives, numberOfConstraints);
		sol.setVariable(0, EncodingUtils.newBinary(solver.periods));
		return sol;
	}

}
