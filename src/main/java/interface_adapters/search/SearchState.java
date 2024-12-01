package interface_adapters.search;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String input = "";
    private List<String> symbols = new ArrayList<>();
    private int pageNumber;
    private String searchStockError;

    public String getInput() {
        return input;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public String getStringPageNumber() {
        return String.valueOf(pageNumber);
    }

    public int getIntPageNumber() {
        return pageNumber;
    }

    public String getSearchStockError() {
        return searchStockError;
    }

    public void setInput(String newInput) {
        this.input = newInput;
    }

    public void setSymbols(List<String> newSymbols) {
        this.symbols = newSymbols;
    }

    /**
     * Decreases page number by 1.
     */
    public void minusPageNumber() {
        this.pageNumber -= 1;
    }

    /**
     * Increases page number by 1.
     */
    public void addPageNumber() {
        this.pageNumber += 1;
    }

    public void setSearchError(String newSearchError) {
        this.searchStockError = newSearchError;
    }

}
