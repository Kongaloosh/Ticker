package stockticker;

/*
 * Used as guidance for getting information from .csv:
 *  http://greatwebguy.com/programming/java/stock-quote-and-chart-from-yahoo-in-java/
 * 
 */

public class StockBean {

    private String ticker;
    private float price;
    private float change;
    private String chartUrlSmall;
    private String chartUrlLarge;
    private long lastUpdated;
    
    public String getTicker() {
    // returns the code of the name of the stock
        return ticker;
    }
    
    public void setTicker(String ticker) {
    // sets the name of the ticker that is being quoted
        this.ticker = ticker;
    }
    
    public float getPrice() {
    // returns the value of the price ( within 20 mins )
        return price;
    }
    
    public void setPrice(float price) {
    // sets the value of the stock ( withing 20 mins)
        this.price = price;
    }
    
    public float getChange() {
    // returns the difference between the current value and the last quote
        return change;
    }
    
    public void setChange(float change) {
    // sets the difference between the current value and the last quote
        this.change = change;
    }
    
    public String getChartUrlSmall() {
        return chartUrlSmall;
    }
    
    public void setChartUrlSmall(String chartUrlSmall) {
        this.chartUrlSmall = chartUrlSmall;
    }
    
    public String getChartUrlLarge() {
        return chartUrlLarge;
    }
    
    public void setChartUrlLarge(String chartUrlLarge) {
        this.chartUrlLarge = chartUrlLarge;
    }
    
    public long getLastUpdated() {
    // returns the time that the stock was last updatd
        return lastUpdated;
    }
    
    public void setLastUpdated(long lastUpdated) {
    // sets the time the stock was last updated
        this.lastUpdated = lastUpdated;
    }
    
}