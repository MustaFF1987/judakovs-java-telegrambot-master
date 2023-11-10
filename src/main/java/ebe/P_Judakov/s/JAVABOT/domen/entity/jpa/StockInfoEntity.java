package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;

public class StockInfoEntity {

    private String symbol;
    private String name;
    private String type;
    private String region;
    private String marketOpen;
    private String marketClose;
    private String timezone;
    private String currency;
    private String matchScore;
    private StockDataEntity stockDataEntity;

    public StockInfoEntity() {
    }

    public StockInfoEntity(String symbol, String name, String type, String region, String marketOpen, String marketClose, String timezone, String currency, String matchScore, StockDataEntity stockDataEntity) {
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.region = region;
        this.marketOpen = marketOpen;
        this.marketClose = marketClose;
        this.timezone = timezone;
        this.currency = currency;
        this.matchScore = matchScore;
        this.stockDataEntity = stockDataEntity;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public String getMarketOpen() {
        return marketOpen;
    }

    public String getMarketClose() {
        return marketClose;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public StockDataEntity getStockDataEntity() {
        return stockDataEntity;
    }

    @Override
    public String toString() {
        return "StockInfoEntity{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", region='" + region + '\'' +
                ", marketOpen='" + marketOpen + '\'' +
                ", marketClose='" + marketClose + '\'' +
                ", timezone='" + timezone + '\'' +
                ", currency='" + currency + '\'' +
                ", matchScore='" + matchScore + '\'' +
                ", stockDataEntity=" + stockDataEntity +
                '}';
    }
}
