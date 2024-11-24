package main.java.use_cases.search;

import java.util.List;

/**
 * Data access interface for the use case.
 */
public interface SearchDataAccessInterface {

    /**
     * Retrieves a NEW list of symbols .
     *
     * @return a list of symbols
     */
    List<String> getSymbols();

}
