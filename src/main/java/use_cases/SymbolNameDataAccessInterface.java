package use_cases;

import java.util.List;

/**
 * Data access interface for the use case.
 */
public interface SymbolNameDataAccessInterface {

    /**
     * Retrieves a list of symbols .
     *
     * @return a list of symbols
     */
    List<String> getSymbols();

    /**
     * Retrieves name of company by its symbol.
     * @param symbol stock symbol
     * @return name of company
     */
    String getCompany(String symbol);

    /**
     * Retrives stock symbol by its company.
     * @param company company name
     * @return stock symbol
     */
    String getSymbol(String company);

}
