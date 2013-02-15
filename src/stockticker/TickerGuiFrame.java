/*
 * Author: Alex Kearney (Konga.kearney@gmail.com)
 */
package stockticker;

import javax.swing.JLabel;
import org.jfree.chart.ChartPanel;

/*Created: 13/2/12 by Alex Kearney
 *  
 * 13/2/12
 * - This is still in a crappy spaghetti phase; I had to smash a few things up
 * due to not really knowing what I was doing with the gui. Sorry guys, I like
 * robots.
 * 
 * - there's a new refresh to prevent the mitosis bug, everything's graphing well
 * it should be easy to refactor for more graphs if need be
 * 
 * - this belongs to the actor, with every instance of the actor, another one of
 * these will be made with all the bells an whistles included. If we want to 
 * expand to keep this form, we can make a higher level panel for managing
 * multiple stocks, which would likely work well with broker functionality.
 *
 * 14/02/13
 * - found a bug: the pane behind will pop up when the exchange is closed
 */
public class TickerGuiFrame extends javax.swing.JFrame {

    /**
     * Creates new form TickerGuiFrame
     */
    public TickerGuiFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        agentDataTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        agentDataPanel = new javax.swing.JPanel();
        agentDataLabel = new javax.swing.JLabel();
        quoteDataPanel = new javax.swing.JPanel();
        quoteLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        agentDataLabel.setText("Herrrrro");

        javax.swing.GroupLayout agentDataPanelLayout = new javax.swing.GroupLayout(agentDataPanel);
        agentDataPanel.setLayout(agentDataPanelLayout);
        agentDataPanelLayout.setHorizontalGroup(
            agentDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(agentDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        agentDataPanelLayout.setVerticalGroup(
            agentDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentDataPanelLayout.createSequentialGroup()
                .addComponent(agentDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addGap(149, 149, 149))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(agentDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(agentDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        agentDataTab.addTab("Agent Data", jPanel1);

        quoteLabel.setText("jLabel1");

        javax.swing.GroupLayout quoteDataPanelLayout = new javax.swing.GroupLayout(quoteDataPanel);
        quoteDataPanel.setLayout(quoteDataPanelLayout);
        quoteDataPanelLayout.setHorizontalGroup(
            quoteDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quoteLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        quoteDataPanelLayout.setVerticalGroup(
            quoteDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quoteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
        );

        agentDataTab.addTab("Quote Data", quoteDataPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        agentDataTab.addTab("tab3", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        agentDataTab.addTab("tab4", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        agentDataTab.addTab("tab5", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agentDataTab, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(agentDataTab)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel agentDataLabel;
    private javax.swing.JPanel agentDataPanel;
    private javax.swing.JTabbedPane agentDataTab;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel quoteDataPanel;
    private javax.swing.JLabel quoteLabel;
    // End of variables declaration//GEN-END:variables

  
  public void refresh(
          JLabel actorData,
          ChartPanel actorProfitGraph,
          ChartPanel priceGraph,
          ChartPanel rewardGraph,
          JLabel StockData){
/*          Created by Alex 13/2/13
 *  This is just a re-fresh, updating all the values in the frame with new
 * graph data .ect.
 */
        agentDataTab.setComponentAt(0, agentDataLabel);
        agentDataLabel.setText(actorData.getText());
        agentDataTab.setComponentAt(1, quoteLabel);
        quoteLabel.setText(StockData.getText());
        
        jPanel2 = rewardGraph;
        agentDataTab.setComponentAt(2, jPanel2);
        
        jPanel3 = actorProfitGraph;
        agentDataTab.setComponentAt(3, jPanel3);
        
        
        jPanel4 = priceGraph;
        agentDataTab.setComponentAt(4, jPanel4);
        
                
        
  }
  
  public void setStartGui(
          JLabel actorData,
          ChartPanel actorProfitGraph,
          ChartPanel priceGraph,
          ChartPanel rewardGraph,
          JLabel StockData){
/*          Created by Alex 13/2/13
 *  This initializes the values of the frame and is called by the Actor to which
 * the instance of the frame belongs to.
 */
         agentDataLabel.setText(actorData.getText());
         agentDataLabel.repaint();

         quoteLabel.setText(StockData.getText());
         quoteLabel.repaint();

         
         agentDataTab.add(jPanel2);
         agentDataTab.add(jPanel3);
         agentDataTab.add(jPanel4);
         jPanel2 = rewardGraph;
         jPanel3 = actorProfitGraph;
         jPanel4 = priceGraph;
         agentDataTab.setTitleAt(2, "Reward Graph");   
         agentDataTab.setTitleAt(3, "Actor Profit Graph"); 
         agentDataTab.setTitleAt(4, "Stock Price Graph");
         quoteDataPanel.repaint();
  }
}
