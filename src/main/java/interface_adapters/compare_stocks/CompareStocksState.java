package interface_adapters.compare_stocks;

import java.time.LocalDate;

public class CompareStocksState {
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstStockName;
    private String secondStockName;

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

    public String getFirstStockName() {
        return firstStockName;
    }

    public void setFirstStockName(String firstStockName) {
        this.firstStockName = firstStockName;
    }

    public String getSecondStockName() {
        return secondStockName;
    }

    public void setSecondStockName(String secondStockName) {
        this.secondStockName = secondStockName;
    }
}
