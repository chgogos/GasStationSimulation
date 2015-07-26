package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class ParetoFront extends JFrame {

	public ParetoFront(double[][] d) {
		DataTable data = new DataTable(Double.class, Double.class);
		for (int i = 0; i < d.length; i++) {
			data.add(d[i][0], d[i][1]);
		}
		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));
		PointRenderer points = new DefaultPointRenderer2D();
		plot.setPointRenderer(data, points);
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(data).setColor(color);
		plot.getPointRenderer(data).setColor(color);
		plot.getAxisRenderer(XYPlot.AXIS_X).setLabel("% High Energy Periods");
		plot.getAxisRenderer(XYPlot.AXIS_X).setTickSpacing(10);
		plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel("% Unhappy Customers");
		double insetsTop = 20.0, insetsLeft = 60.0, insetsBottom = 60.0, insetsRight = 40.0;
		plot.setInsets(new Insets2D.Double(insetsTop, insetsLeft, insetsBottom, insetsRight));
		plot.getTitle().setText("Percentage of high energy periods vs Percentage of unhappy customers");

		getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);
	}
}
