package use_cases.search;

import java.util.List;

/**
 * The output data for the search use case. Input is included in output in case it is needed for display.
 */
public class SearchOutputData {
    private final String input;
    private final List<String> symbols;

    public SearchOutputData(String input, List<String> symbols) {
        this.input = input;
        this.symbols = symbols;
    }

    public String getInput() {
        return input;
    }

    public List<String> getSymbols() {
        return symbols;
    }
}
