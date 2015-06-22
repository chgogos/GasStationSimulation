package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class PumpsVsWaitTime extends JFrame {

	public PumpsVsWaitTime(int[] pumps, double[] avgWaitTime) {
		DataTable data = new DataTable(Integer.class, Double.class);
		for (int i = 0; i < pumps.length; i++) {
			data.add(pumps[i], avgWaitTime[i]);
		}
		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(data, lines);
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(data).setColor(color);
		plot.getLineRenderer(data).setColor(color);
		plot.getAxisRenderer(XYPlot.AXIS_X).setLabel("Number of Pumps");
        plot.getAxisRenderer(XYPlot.AXIS_X).setTickSpacing(1.0);
		plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Average Wait Time (sec)");
        
    
		double insetsTop = 20.0, insetsLeft = 60.0, insetsBottom = 60.0, insetsRight = 40.0;
		plot.setInsets(new Insets2D.Double(insetsTop, insetsLeft, insetsBottom,
				insetsRight));
		plot.getTitle().setText("Effect of pumps number on average wait time");

		getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(800, 600);
	}

	public static void main(String[] args) {
		int[] pumps = { 3, 4, 5, 6 };
		double[] avgWaitTime = { 17.64, 4.89, 3.72, 3.14, 3.06 };
		PumpsVsWaitTime frame = new PumpsVsWaitTime(pumps, avgWaitTime);
		frame.setVisible(true);
	}
}
