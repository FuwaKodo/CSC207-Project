package interface_adapters.compare_stocks;

/**
 * Represents the state of the stock comparison.
 * This class holds the summary of the comparison results.
 */
public class CompareStocksState {
    private String summary;

    /**
     * Retrieves the comparison summary.
     *
     * @return the comparison summary as a string
     *         (can be null if no summary is set)
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the comparison summary.
     *
     * @param summary the comparison summary as a string
     *                (can be null to indicate no summary)
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
