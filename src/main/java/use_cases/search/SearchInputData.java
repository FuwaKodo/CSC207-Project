package main.java.use_cases.search;

/**
 * Input data for searching stocks.
 */
public class SearchInputData {
    private final String input;

    public SearchInputData(String typed_string) {
        this.input = typed_string.strip().toUpperCase();
    }

    String getInput() {
        return input;
    }
}
