/*
 *Developer: Alexandra Kearney (Kearney@Ualberta.ca)
 *Last Edited: 13/2/13 By Alex Kearney
 */
package stockticker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Alex's overall project notes:
 * 10/02/13
 *  - Added graphing for reward, but can be expanded
 *  - Debugged actions
 *  - Debugged actor writing
 * 
 * 11/02/13
 *  - Reads and writes to a text file to carry over the values of the state space.
 *  - If statement makes shure that the agent is only trading within viable hours.
 *  - Should read this: http://www.cs.utexas.edu/~ai-lab/pubs/AMEC04-plat.pdf
 *  - Re-factored the graphing; like the quoting pane, it now belongs to the actor.
 * 
 * 13/02/13
 *  - For some reason data wasn't being fetched well, but the bugs appear to be fixed
 *  - We really need to make a front end to deal with multiple stocks, like a
 *  JFrame to deal with the messy particulars of working with the Nikkei or FTSE
 *  That way we can run this all day long.
 * 
 * 14/02/13
 * - Extended the application to three timezones
 * - partially fixed FTSE pane bug, still doesn't recognize it's down
 * - pane index out of bounds bug in the while loop
 * - fixed the index out of bounds error, I hope it doesn't reccur
 * - fixed the versioning mistake.
 */ 
public class StockTicker {

    /* To-do: 
     *  - Add nikkei and FTSE as their own exchange times
     *  - Fix the discritization for a more optimum statespace:
     * The states are varying by fractions of a percentage, figure out what 
     * those fractions may be and update Actor.discritize(), such that there are
     * more states for the smaller movements and less for the larger movements
     *  - Fix the reward for greater control
     *  - Add an equity field that's added to the agent
     *  - update the gui with a more aesthetically put together feel:
     * This means seperation and less chunky 
     *  - Create a broker class that bets money given the stock 'Analyst's' Calls
     *  - Find a way to get investment-house analyst calls (API?)
     */
    public static void main(String[] args) {
        double THIRTY_MIN = 180000;
        double FIFTEEN_MIN = 900000;
        double ONE_MIN = 60000;
        
//        GOOG actor
        StockBean stockGOOG = StockTickerDAO.getInstance().getStockPrice("GOOG");
        Actor actorGOOG = new Actor("GOOG");
        actorGOOG.setPrice(stockGOOG.getPrice());
        actorGOOG.setChange(stockGOOG.getChange());
//        NIKKEI actor
        StockBean stockNIKKEI = StockTickerDAO.getInstance().getStockPrice("^N225");
        Actor actorNIKKEI = new Actor("^N225");
        actorNIKKEI.setChange(stockNIKKEI.getChange());
        actorNIKKEI.setPrice(stockNIKKEI.getPrice());
//        FTSE actor
        StockBean stockFTSE = StockTickerDAO.getInstance().getStockPrice("^FTSE");
        Actor actorFTSE = new Actor("^FTSE");
        actorFTSE.setPrice(stockFTSE.getPrice());
        actorFTSE.setChange(stockFTSE.getChange());
        
        long time = System.currentTimeMillis();

//      Timing for NYSE
        TimeZone est = TimeZone.getTimeZone("EST");
        Calendar calendarEST = Calendar.getInstance(est);
//      Timing for NIKKEI
        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar calendarJST = Calendar.getInstance(jst);
//      Timing got FTSE
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        Calendar calendarGMT = Calendar.getInstance(gmt);
        
        while (true) {
//            Case for NYSE
            if ((calendarEST.get(Calendar.HOUR_OF_DAY) == 9 && calendarEST.get(Calendar.MINUTE) >= 30)
                    || (calendarEST.get(Calendar.HOUR_OF_DAY) >= 10 && calendarEST.get(Calendar.HOUR_OF_DAY) < 16)) {
//                 if the hours are between 9:30 AM and 4:00 pm, trade.

                stockGOOG = StockTickerDAO.getInstance().getStockPrice("GOOG");
                actorGOOG.act(stockGOOG.getPrice(), stockGOOG.getChange());

            } else {
                actorGOOG.holdPane(calendarEST.get(Calendar.HOUR_OF_DAY), "9:30");
            }
//           Case for NIKKEI
            if (
                    (calendarJST.get(Calendar.HOUR_OF_DAY) == 9) ||
                    (calendarJST.get(Calendar.HOUR_OF_DAY) >= 9 && calendarJST.get(Calendar.HOUR_OF_DAY) <= 15)) {
                stockNIKKEI = StockTickerDAO.getInstance().getStockPrice("^N225");
                actorNIKKEI.act(stockNIKKEI.getPrice(), stockNIKKEI.getChange());
                
            } else{
                actorNIKKEI.holdPane(calendarJST.get(Calendar.HOUR_OF_DAY), "9:00");
            }
//           Case for FTSE
            if (
                    (calendarGMT.get(Calendar.HOUR_OF_DAY) == 8) ||
                    (calendarGMT.get(Calendar.HOUR_OF_DAY) >= 8 && calendarGMT.get(Calendar.HOUR_OF_DAY) <= 12)) {
                stockFTSE = StockTickerDAO.getInstance().getStockPrice("^FTSE");
                actorFTSE.act(stockFTSE.getPrice(), stockFTSE.getChange());
            }else{
                actorFTSE.holdPane(calendarGMT.get(Calendar.HOUR_OF_DAY), "8:00");
            }
        }
    }
}