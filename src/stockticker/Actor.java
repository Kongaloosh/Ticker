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
 * specialized 
 */
public class Actor {

    private double buyInPrice = 0;
    private double sellPrice = 0;
    private boolean isHolding = false;
    private double price;
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
    //Agent Variables:
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
    Grapher actorProfit;
    Grapher priceGraph;

    public Actor(String quote) {
        name = quote;
        
        // create real-time lable
        
        // createStatespace
        readSpace();

        // create graph
     
        rewardGraph = new Grapher("Reward", "Reward", "Time-steps", "Reward");
        actorProfit = new Grapher("Actor Profit", "Actor Profit", "Time Steps", "Actor Profit");
        priceGraph = new Grapher("Price", "Price", "Time Steps", "Price");
        
        
        gui.setStartGui(new JLabel(""), actorProfit.get(), priceGraph.get(), rewardGraph.get(), new JLabel(""));
        gui.setVisible(true);
        
    }
    
    public void guiManager(){
        
        JLabel actorData = new JLabel(
                "<html>Quoting: " + name
                + "<br>Profit: " + getProfit()
                + "<br>Time step: " + timesteps
                + "<br>Action: " + getAction()
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
        
        gui.refresh(actorData, priceGraph.get(), actorProfit.get(), rewardGraph.get(), stockData);
        gui.repaint();
    }
    
    public void act(double price, double change) {
        
            
        if (previousPrice != price && change !=  previousChange) {
        
        setPrice(price);
        setChange(change);
        updateStateIndex();
        updateAction();
        updateHoldings();
        guiManager();
        Graph();
        writeStateSpace();
        timesteps++;
        updateTD();
        updateStateVariables();
        
        }
    }

    public void write() {
        System.out.println(
                " ***************" + timesteps
                + " Action :  " + getAction()
                + "\n Price : " + getPrice()
                + "\n Buy - in price " + getBuyInPrice()
                + "\n profit: " + profit
                + "\n previous move: " + getIndexOfPreviousBestMove()
                + "\n index:" + getIndexOfBestMove()
                + "\n index value" + stateSpace[getIndexOfBestMove()]
                + "\n reward: " + getReward()
                + "\n isHolding" + isIsHolding() + "\n");
//        System.out.println("is Holding" + isHolding
//                + "\n action" + action
//                + "\n..............................");
    }

    public void writeStateSpace() {
        // will write out the statespace as a string with a new line for all doubles
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
    
    public void holdPane(int hour){
        JLabel holdLable = new JLabel(
                  "<html> Exchange is closed"
                + "<br> Opening time: 9:30" 
                + "<br> Current time EST: " + hour
                + "</html>");
        gui.refresh(holdLable, new ChartPanel(null), new ChartPanel(null), new ChartPanel(null), new JLabel());
    }
    
    public void Graph() {
        rewardGraph.update(timesteps, getReward());
        actorProfit.update(timesteps, getProfit());
        priceGraph.update(timesteps, getPrice());
        
    }

    public void readSpace() {
//        reads all of the values in the file for statespace
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
        int indexPrice;
        int indexChange;

        if (price / previousPrice >= 2) {
            indexPrice = 20;
        } else {
            indexPrice = (int) Math.round((price / previousPrice) * 10);
        }

        if (change / previousChange >= 2) {
            indexChange = 20;
        } else {
            indexChange = (int) Math.round((change / previousChange) * 10);
        }

        setIndexOfState(indexChange + (21 * indexPrice));
    }

    public void updateAction() {
        /* 
         * 0 = Buy
         * 1 = Sell
         * 2 = Hold
         */
        double globalMax = 0;
        int globalMaxIndex = 0;
        int tempAction = 0;

        for (int i = 0; i < 3; i++) {

            if ((i == 0 && !isHolding) || // if not holding and buy index
                    (i == 1 && isHolding) || // if holding and sell index
                    i == 2) {             // if hold
//                System.out.println("i " + i + "is holding "+ isHolding );
                // if the agent is holding stocks it can hold or sell
                // if the agent is not holding stocks it can hold or buy

                if (stateSpace[getIndexOfState() + (21 * 21 * i)] >= globalMax) {

                    globalMax = stateSpace[getIndexOfState() + (21 * 21 * i)];
                    globalMaxIndex = (getIndexOfState() + (21 * 21 * i));
                    tempAction = i;
                }
            }
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
        if (getAction() == 0) {
            profit -= getPrice();
            setBuyInPrice(getPrice());
            setIsHolding(true);

        } else if (action == 1) {
            profit += getPrice();
            setBuyInPrice(0);
            setIsHolding(false);
        }
    }

    public void determineReward() {
        
        if (getAction() == 0) {
            setReward(-1.0 / (getPreviousPrice() / getPrice()));
        } else if (getAction() == 1) {
            setReward(-1.0 / (getPrice() - getBuyInPrice()));
        } else if (getAction() == 2) {
            if (isHolding) {
                setReward(-1.0 / (getPreviousPrice() / getPrice()));
            } else {
                setReward(-1.0 / (getPrice() / getPreviousPrice()));
            }
        }
    }

    public void updateTD() {
        determineReward();
        setTemporalDifference(
                learningRate
                * (getReward()
                + (discountRate
                * (stateSpace[getIndexOfBestMove()] - stateSpace[getIndexOfPreviousBestMove()]))));

        stateSpace[getIndexOfPreviousBestMove()] += learningRate * getTemporalDifference();
    }

    public void updateStateVariables() {
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
        if (value < -0.1) {
            return 0;
        } else if (value >= -0.09 && value < -0.08) {
            return 1;
        } else if (value >= -0.08 && value < -0.7) {
            return 2;
        } else if (value >= -0.07 && value < -0.6) {
            return 3;
        } else if (value >= -0.06 && value < -0.5) {
            return 4;
        } else if (value >= -0.04 && value < -0.3) {
            return 5;
        } else if (value >= -0.02 && value < -0.1) {
            return 6;
        } else if (value >= -0.01 && value < -0.0) {
            return 7;
        } else if (value >= 0 && value < 0.1) {
            return 8;
        } else if (value >= 0.1 && value < 0.2) {
            return 9;
        } else if (value >= 0.2 && value < 0.3) {
            return 10;
        } else if (value >= 0.3 && value < 0.4) {
            return 11;
        } else if (value >= 0.4 && value < 0.5) {
            return 12;
        } else if (value >= 0.5 && value < 0.6) {
            return 13;
        } else if (value >= 0.6 && value < 0.7) {
            return 14;
        } else if (value >= 0.7 && value < 0.8) {
            return 15;
        } else if (value >= 0.8 && value < 0.9) {
            return 16;
        } else {
            return 17;
        }
    }
}
