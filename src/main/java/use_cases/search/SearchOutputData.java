package use_cases.search;

import java.util.Collections;
import java.util.List;

/**
 * The output data for the search use case.
 */
public class SearchOutputData {
    private final String input;
    private final List<String> symbols;

    public SearchOutputData(String input, List<String> symbols) {
        this.input = input;
        this.symbols = symbols;
        Collections.sort(this.symbols);
    }

    public String getInput() {
        return input;
    }

    public List<String> getSymbols() {
        return symbols;
    }
}
