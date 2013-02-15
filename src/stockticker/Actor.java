/*
 * Developer: Alexandra Kearney (Kearney@Ualberta.ca)
 *  Last edited: 13/2/13 By Alex Kearney
 */
package stockticker;

import com.sun.java_cup.internal.runtime.Symbol;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Time;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jfree.chart.ChartPanel;

/*  Alex's Notes:
 * 
 * 10/2/13
 * -Fixed the bug regarding the printing of the buy-in price; the close html tag
 * closes all of the writing, so it must be at the very end of every peice.
 * 
 * -added get reward to the output so the reward function can be tracked
 * 
 * 12/2/13
 * - Added a write and read for the statespace; there is now a rolling memory of
 * the stocks.
 * 
 * 13/2/13
 * - The GUI has really, really, really gotten a lot more developed now. I have a
 * specialized class for this
 * - It would probably be a not bad idea to go ahead and make something that would
 * generate a .txt file for the statespace if there wasn't one already
 * - It would also be a good idea to look into using other kinds of files to make
 * the retreival a bit faster.
 * 
 * 14/02/13
 * - pane bug in the price pane
 * - you really need to check over the spaghetti agent's actions
 */
public class Actor {
//  Stock related fields:
    private double buyInPrice = 0;
    private double sellPrice = 0;
    private boolean isHolding = false;
    private double price = 0;
    private double change;
    private double previousPrice = 0;
    private double previousChange = 0;
    private int previousState = 0;
    private int action = 0;
    private double profit = 0;
    private int indexOfPreviousBestMove;
    private int indexOfState;
    private int indexOfBestMove;
    private double reward;
    private double equity;
//  Agent Variables:
    private double temporalDifference;
    public static final double learningRate = 0.1;
    public static final double discountRate = 0.99;
    double[] stateSpace = new double[(21) + (21 * 21) + (21 * 21 * 3)];
    int timesteps = 0;
    
    TickerGuiFrame gui = new TickerGuiFrame();
    
    private String name;
    // file management variables
    BufferedReader bufferedReader;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    // Graphing object
    Grapher rewardGraph;
    Grapher equityGraph;
    Grapher priceGraph;

    public Actor(String quote) {
        name = quote;
//        Reads the pre-existing Statespace
        readSpace();
//       Sets the graphs that track agent behaviour
        rewardGraph = new Grapher("Reward", "Reward", "Time-steps", "Reward");
        equityGraph = new Grapher("Actor Equity", "Equity", "Time Steps", "Equity");
        priceGraph = new Grapher("Price", "Price", "Time Steps", "Price");
//        Creates the gui to monitor data
        gui.setStartGui(new JLabel(""), equityGraph.get(), priceGraph.get(), rewardGraph.get(), new JLabel(""));
        gui.setVisible(true);
    }
    
    public void guiManager(){
/* Created by: Alex Kearney 13/2/13
 *  This creates the data to pass into the Gui class on a step by step basis
 * - this could be improved as there is spaghetti left over from jerry-rigging
 * the panels into the GUI
 */        
        
         String tempAction = "";
        if (getAction() == 0) {
            tempAction = "Buy";
        }else if (getAction() == 1){
            tempAction = "Sell";
        }else if (getAction() == 2){
            tempAction = "Hold";
        }
        
        JLabel actorData = new JLabel(
                "<html>Quoting: " + name
                + "<br>Profit: " + getProfit()
                + "<br>Time step: " + timesteps
                + "<br>Action: " + tempAction
                + "<br>Previous index: " + getIndexOfBestMove()
                + "<br>Buy in price: " + getBuyInPrice()
                + "<br>Reward : " + getReward()
                + "<br>Is Holding: " + isHolding 
                + "</html>");
        
        JLabel stockData = new JLabel(
                "<html>"
                + "Quoting: " + name
                + "<br>Price: " + getPrice()
                + "<br>Change: " + getChange()
                + "<br>Time step: " + timesteps
                + "</html>");
        
        gui.refresh(actorData, equityGraph.get(), priceGraph.get(), rewardGraph.get(), stockData);
        gui.repaint();
    }
    
    public void act(double price, double change) {
        /* Created by: Alexandra Kearney
         * This is the method that will move the agent
         */
        if (previousPrice != price && change !=  previousChange) {
        
//        Updating Previous step    
            
        timesteps++;
        updateTD(price, change);
        updateStateVariables();
// writing
        updateHoldings();
        Graph();
        guiManager();
        writeStateSpace();
//        action
        setPrice(price);
        setChange(change);
        updateStateIndex();
        updateAction();
        
        
        }
    }

    public void write() {
    /* Created By: Alex Kearney
     * This is a bit of redundant code from the pre-gui phase, but it's still 
     * helpful for debug.
     */    
        String tempAction = "";
        if (getAction() == 0) {
            tempAction = "Buy";
        }else if (getAction() == 1){
            tempAction = "Sell";
        }else if (getAction() == 2){
            tempAction = "Hold";
        }
        
        System.out.println(
                " ***************" + timesteps
                + " Action :  " + tempAction
                + "\n Price : " + getPrice()
                + "\n Buy - in price " + getBuyInPrice()
                + "\n profit: " + profit
                + "\n previous move: " + getIndexOfPreviousBestMove()
                + "\n index:" + getIndexOfBestMove()
                + "\n index value" + stateSpace[getIndexOfBestMove()]
                + "\n reward: " + getReward()
                + "\n isHolding" + isIsHolding() + "\n");
    }

    public void writeStateSpace() {
        /* Created By: Alex Kearney 
         * This writes the statespace to a file for long-term use
         */
        try {

            fileWriter = new FileWriter("Statespace" + name + ".txt");
            bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < stateSpace.length; i++) {
                if (i == stateSpace.length - 1) {
                    bufferedWriter.write(stateSpace[i] + "");
                } else {
                    bufferedWriter.write(stateSpace[i] + "");
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error: In creation of the statespace");
        }
    }
    
    public void holdPane(int hour, String openingTime){
        /* Created By: Alex Kearney 
         * This holds the stock during after-hours trading so no actions occur.
         */
        JLabel holdLable = new JLabel(
                  "<html>" + name
                + "<br> Exchange is closed"
                + "<br> Opening time: " + openingTime 
                + "<br> Current time EST: " + hour
                + "</html>");
        gui.refresh(holdLable, priceGraph.get(), equityGraph.get(), rewardGraph.get(), holdLable);
    }
    
    public void Graph() {
        /* Created By: Alex Kearney 13/2/13
         * Updated the graphs with most recent information
         */
        rewardGraph.update(timesteps, getReward());
        equityGraph.update(timesteps, getEquity());
        priceGraph.update(timesteps, getPrice());
        
    }

    public void readSpace() {
        /* Created By: Alex Kearney 
         * Reads statespace from previous execution
         */
        try {
            bufferedReader = new BufferedReader(
                    new FileReader("Statespace" + name + ".txt"));
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                stateSpace[index] = Double.parseDouble(line);
                index++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error: In the reading of stateSpace");
        }
    }

    public void updateStateIndex() {
        /* Created By: Alex Kearney 
         * Determines the value for the current state
         */
        int indexPrice;
        int indexChange;

        if (change / previousChange >= 2) {
            indexChange = 20;
        } else {
            indexChange = (int) Math.round((change / previousChange) * 10);
        }
        setIndexOfState(discritize(price / previousPrice) + (21 * discritize((change / previousChange))));
    }

    public void updateAction() {
        /* Created By: Alex Kearney 
         * Determines the actor's next action
         */
        double globalMax = 0;
        int globalMaxIndex = 0;
        int tempAction = 0;

        if (isHolding) {
            if (stateSpace[getIndexOfState() + (21 * 21 * 1)] >= stateSpace[getIndexOfState() + (21 * 21 * 2)]) {
//                then sell
                tempAction = 1;
            }else{
//                then hold
                tempAction = 2;
            }
        }else if (!isHolding){
            if (stateSpace[getIndexOfState() + (21 * 21 * 0)] >= stateSpace[getIndexOfState() + (21 * 21 * 2)]) {
//                then buy
                tempAction = 0;
            }else{
//                then hold
                tempAction = 2;
            }
        }
        
        if (isHolding && tempAction == 0) {
            System.out.println("the machines are rising");
        }
        
        setIndexOfBestMove(globalMaxIndex);
        setAction(tempAction);


        if (Math.random() < 0.1) {
            Random random = new Random();
            int randomAction = random.nextInt(3);
            while ((isHolding && (randomAction == 1 || randomAction == 2)) || (!isHolding && (randomAction == 0 || randomAction == 2))) {

                randomAction = random.nextInt(3);
            }
            setAction(randomAction);
            setIndexOfBestMove(getIndexOfState() + (21 * 21 * randomAction));
        }
    }

    public void updateHoldings() {
        /* Created By: Alex Kearney 
         * Updates the isHolding based on actions
         */
        if (getAction() == 0) {
            profit -= getPrice();
            setBuyInPrice(getPrice());
            setIsHolding(true);

        } else if (action == 1) {
            profit += getPrice();
            equity += (getPrice() -getBuyInPrice());
            setBuyInPrice(0);
            setIsHolding(false);
        }
    }

    public void determineReward(double newPrice, double newChange) {
        /* Created By: Alex Kearney 
         * Determines the reward given the action
         */
        if (getAction() == 0) {
            setReward(-1.0 / (getPrice() / newPrice));
        } else if (getAction() == 1) {
            setReward(-1.0 / (newPrice - getBuyInPrice()));
        } else if (getAction() == 2) {
            if (isHolding) {
                setReward(-1.0 / (getPrice()/ newPrice));
            } else {
                setReward(-1.0 / (newPrice / getPrice()));
            }
        }
    }

    public void updateTD(double newPrice, double newChange) {
        /* Created By: Alex Kearney 
         * 
         */
        determineReward(newPrice, newChange);
        setTemporalDifference(
                learningRate
                * (getReward()
                + (discountRate
                * (stateSpace[getIndexOfBestMove()] - stateSpace[getIndexOfPreviousBestMove()]))));

        stateSpace[getIndexOfPreviousBestMove()] += learningRate * getTemporalDifference();
    }

    public void updateStateVariables() {
        /* Created By: Alex Kearney 
         * 
         */
        setIndexOfPreviousBestMove(getIndexOfBestMove());
        setPreviousPrice(getPrice());
        setPreviousChange(getChange());
    }

//    ******** END OF ACTOR CODE **************
//            Getters and Setters
    public double getBuyInPrice() {
        return buyInPrice;
    }

    public void setBuyInPrice(double buyInPrice) {
        this.buyInPrice = buyInPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isIsHolding() {
        return isHolding;
    }

    public void setIsHolding(boolean isHolding) {
        this.isHolding = isHolding;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    public double getPreviousChange() {
        return previousChange;
    }

    public void setPreviousChange(double previousChange) {
        this.previousChange = previousChange;
    }

    public int getPreviousState() {
        return previousState;
    }

    public void setPreviousState(int previousState) {
        this.previousState = previousState;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getIndexOfPreviousBestMove() {
        return indexOfPreviousBestMove;
    }

    public void setIndexOfPreviousBestMove(int indexOfPreviousBestMove) {
        this.indexOfPreviousBestMove = indexOfPreviousBestMove;
    }

    public double getTemporalDifference() {
        return temporalDifference;
    }

    public void setTemporalDifference(double temporalDifference) {
        this.temporalDifference = temporalDifference;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getIndexOfState() {
        return indexOfState;
    }

    public void setIndexOfState(int indexOfBestMove) {
        this.indexOfState = indexOfBestMove;
    }

    public int getIndexOfBestMove() {
        return indexOfBestMove;
    }

    public void setIndexOfBestMove(int indexOfBestMove) {
        this.indexOfBestMove = indexOfBestMove;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    private int discritize(double value) {
        if (value < 0.85) {
            return 0;
        } else if (value >= 0.85 && value < 0.9) {
            return 1;
        } else if (value >= 0.91 && value < 0.92) {
            return 2;
        } else if (value >= 0.92 && value < 0.93) {
            return 3;
        } else if (value >= 0.93 && value < 0.94) {
            return 4;
        } else if (value >= 0.94 && value < 0.95) {
            return 5;
        } else if (value >= 0.95 && value < 0.96) {
            return 6;
        } else if (value >= 0.96 && value < 0.97) {
            return 7;
        } else if (value >= 0.97 && value < 0.98) {
            return 8;
        } else if (value >= 0.98 && value < 0.99) {
            return 9;
        } else if (value >= 0.99 && value < 1) {
            return 10;
        } else if (value >= 1 && value < 1.01) {
            return 11;
        } else if (value >= 1.01 && value < 1.02) {
            return 12;
        } else if (value >= 1.02 && value < 1.03) {
            return 13;
        } else if (value >= 1.03 && value < 1.04) {
            return 14;
        } else if (value >= 1.04 && value < 1.05) {
            return 15;
        } else if (value >= 1.05 && value < 1.06) {
            return 16;
        } else if (value >= 1.06 && value < 1.07) {
            return 17;
        } else if (value >= 1.07 && value < 1.08) {
            return 18;
        } else if (value >= 1.08 && value < 1.09) {
            return 19;
        } else if (value >= 1.10 && value < 1.15) {
            return 20;
        } else {
            return 21;
        }
    }

    /**
     * @return the equity
     */
    public double getEquity() {
        return equity;
    }

    /**
     * @param equity the equity to set
     */
    public void setEquity(double equity) {
        this.equity = equity;
    }
}
