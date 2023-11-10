package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;

public class StockDataEntity {

    private String symbol;
    private String open;
    private String high;
    private String low;
    private String price;
    private String volume;
    private String latestTradingDay;
    private String previousClose;
    private String change;
    private String changePercent;

    public StockDataEntity() {
    }

    public StockDataEntity(String symbol, String open, String high, String low, String price, String volume, String latestTradingDay, String previousClose, String change, String changePercent) {
        this.symbol = symbol;
        this.open = open;
        this.high = high;
        this.low = low;
        this.price = price;
        this.volume = volume;
        this.latestTradingDay = latestTradingDay;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public String getLatestTradingDay() {
        return latestTradingDay;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public String getChange() {
        return change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    @Override
    public String toString() {
        return "StockDataEntity{" +
                "symbol='" + symbol + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", latestTradingDay='" + latestTradingDay + '\'' +
                ", previousClose='" + previousClose + '\'' +
                ", change='" + change + '\'' +
                ", changePercent='" + changePercent + '\'' +
                '}';
    }
}
