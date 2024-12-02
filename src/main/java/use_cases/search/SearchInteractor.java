package use_cases.search;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import app.Constants;
import use_cases.SymbolNameDataAccessInterface;

/**
 * Interactor of the search use case.
 */
public class SearchInteractor implements SearchInputBoundary {

    private final SearchOutputBoundary searchPresenter;
    private final SymbolNameDataAccessInterface dataAccessObject;

    public SearchInteractor(SearchOutputBoundary searchPresenter,
                            SymbolNameDataAccessInterface dataAccessObject) {
        this.searchPresenter = searchPresenter;
        this.dataAccessObject = dataAccessObject;
    }

    /**
     * Executes the use case.
     *
     * @param searchInputData input data for the use case.
     */
    @Override
    public void execute(SearchInputData searchInputData) {
        final String input = searchInputData.getInput().strip().toUpperCase();
        final List<String> allSymbols = dataAccessObject.getSymbols();

        // different lists for how similar a symbol is to the input
        final List<String> exactMatches = new ArrayList<>();
        final List<String> substringMatches = new ArrayList<>();
        final List<String> characterMatches = new ArrayList<>();
        final Hashtable<Integer, List<String>> listTable = new Hashtable<>();
        listTable.put(Constants.SIMILAR_BY_SOME_CHAR, characterMatches);
        listTable.put(Constants.SIMILAR_AS_SUBSTRING, substringMatches);
        listTable.put(Constants.EXACTLY_SAME, exactMatches);

        // filtering symbols by input
        for (String s: allSymbols) {
            final String symbol = s.strip().toUpperCase();

            if (!symbol.isEmpty() && similarIndex(input, symbol) != Constants.NOT_SIMILAR) {
                listTable.get(similarIndex(input, symbol)).add(symbol);
            }
        }

        // the search result
        final List<String> symbols = new ArrayList<>();
        symbols.addAll(listTable.get(Constants.EXACTLY_SAME));
        symbols.addAll(listTable.get(Constants.SIMILAR_AS_SUBSTRING));
        symbols.addAll(listTable.get(Constants.SIMILAR_BY_SOME_CHAR));

        // output data
        final SearchOutputData searchOutputData = new SearchOutputData(searchInputData.getInput(), symbols);

        searchPresenter.displayResult(searchOutputData);
    }

    /**
     * Compares how similar two strings are. If condition for multiple similarity indexes are met,
     * the highest will be returned. Strings are allowed to be empty.
     *
     * <p>
     *     0: not similar at all
     *     1: at least 1 char matches
     *     2: one is the substring of the other
     *     3: the two are exactly the same
     * </p>
     *
     * @param string1 the first string to be compared
     * @param string2 the second string to be compared
     * @return an int representing their similarity
     */
    private int similarIndex(String string1, String string2) {
        int similarity = Constants.NOT_SIMILAR;

        if (string1.equals(string2)) {
            similarity = Constants.EXACTLY_SAME;
        }
        else if (string1.contains(string2) || string2.contains(string1)) {
            similarity = Constants.SIMILAR_AS_SUBSTRING;
        }
        else if (similarByChar(string1, string2)) {
            similarity = Constants.SIMILAR_BY_SOME_CHAR;
        }

        return similarity;
    }

    private boolean similarByChar(String string1, String string2) {
        boolean result = false;
        for (char c : string1.toCharArray()) {
            if (string2.contains(String.valueOf(c))) {
                result = true;
                break;
            }
        }
        return result;
    }

}
