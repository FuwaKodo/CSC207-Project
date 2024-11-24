package main.java.interface_adapters.search;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String input = "";
    private List<String> symbols = new ArrayList<>();
    private String searchStockError;

    public String getInput() {
        return input;
    }

    public List<String> getSymbols() {
        return symbols;
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

    public void setSearchError(String newSearchError) {
        this.searchStockError = newSearchError;
    }

}
