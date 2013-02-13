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
 */
public class StockTicker {

    /*
     * 
     */
    public static void main(String[] args) {
        double THIRTY_MIN = 180000;
        double FIFTEEN_MIN = 900000;
        double ONE_MIN = 60000;
        StockBean stock = StockTickerDAO.getInstance().getStockPrice("GOOG");
        Actor actor = new Actor("GOOG");
        actor.setPrice(stock.getPrice());
        actor.setChange(stock.getChange());
        long time = System.currentTimeMillis();



        TimeZone est = TimeZone.getTimeZone("EST");
        Calendar calendar = Calendar.getInstance(est);

        while (true) {
            if ((calendar.get(Calendar.HOUR_OF_DAY) == 9 && calendar.get(Calendar.MINUTE) >= 30)
                    || (calendar.get(Calendar.HOUR_OF_DAY) >= 10 && calendar.get(Calendar.HOUR_OF_DAY) < 16)) {
//                 if the hours are between 9:30 AM and 4:00 pm, trade.

                stock = StockTickerDAO.getInstance().getStockPrice("GOOG");
                actor.act(stock.getPrice(), stock.getChange());
                time = System.currentTimeMillis();

            } else {
                actor.holdPane(calendar.get(Calendar.HOUR_OF_DAY));
            }


        }
    }
}