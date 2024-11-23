package interface_adapters.compare_stocks;

import entities.Stock;

import java.time.LocalDate;

public class CompareStocksState {
    private LocalDate startDate;
    private LocalDate endDate;
    private Stock firstStock;
    private Stock secondStock;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Stock getFirstStock() {
        return firstStock;
    }

    public void setFirstStock(Stock firstStock) {
        this.firstStock = firstStock;
    }

    public Stock getSecondStock() {
        return secondStock;
    }

    public void setSecondStock(Stock secondStock) {
        this.secondStock = secondStock;
    }
}
