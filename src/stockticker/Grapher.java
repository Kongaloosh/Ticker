/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockticker;

import java.awt.Panel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.demo.SWTBarChartDemo1;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Alexandr
 */
public class Grapher extends ApplicationFrame{
    
    XYSeries series;
    XYDataset xYDataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    
    public Grapher(final String title, String seriesName, String xAxis, String yAxis ){
          super(title);
          
          series = new XYSeries(seriesName);
          series.add(0,0);
          xYDataset = new XYSeriesCollection(series);
          chart = ChartFactory.createXYAreaChart(
                  title,
                  xAxis,
                  yAxis,
                  xYDataset,
                  PlotOrientation.VERTICAL,
                  rootPaneCheckingEnabled,
                  rootPaneCheckingEnabled,
                  rootPaneCheckingEnabled);
          
          chartPanel = new ChartPanel(chart);
          chartPanel.setVisible(true);     
          chartPanel.setPreferredSize(new java.awt.Dimension(500,270));
          setContentPane(chartPanel);
          
    }   
      
    public void update(double xPoint, double yPoint){
        series.add(xPoint,yPoint); 
    }
    
    public ChartPanel get(){
        return chartPanel;
    }
}
