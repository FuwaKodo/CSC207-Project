package entities;

public class FavoriteStock {
    private final String symbol;
    private final String company;

    public FavoriteStock(String symbol, String company){
        this.symbol = symbol;
        this.company = company;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }
}
