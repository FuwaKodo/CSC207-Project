package test.use_cases.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.search.SearchInputBoundary;
import use_cases.search.SearchInputData;
import use_cases.search.SearchInteractor;
import use_cases.search.SearchOutputBoundary;
import use_cases.search.SearchOutputData;

public class SearchTest {
    // mock data access object to be used in all tests which also check that the data isn't mutated
    private final List<String> symbols = List.of("", "a", "A", "AAA", "CBA", "Ij", "ZZZZ");
    final private SymbolNameDataAccessInterface dataAccessObject = new SymbolNameDataAccessInterface() {
        @Override
        public List<String> getSymbols() {
            return new ArrayList<>(symbols);
        }

        @Override
        public String getCompany(String symbol) {
            return "";
        }
    };

    @Test
    public void executeEmptyTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("A", "A", "AAA", "CBA", "IJ", "ZZZZ")));
                assert(searchOutputData.getInput().isEmpty());
                assert(dataAccessObject.getSymbols().equals(symbols));
            }
        };
        SearchInputData inputData = new SearchInputData("");
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeResultOrderTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("CBA", "A", "A", "AAA")));
                assert(searchOutputData.getInput().equals("cba"));
                assert(dataAccessObject.getSymbols().equals(symbols));
            }
        };
        SearchInputData inputData = new SearchInputData("cba");
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeCharTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("IJ")));
                assert(searchOutputData.getInput().equals("ji"));
                assert(dataAccessObject.getSymbols().equals(symbols));
            }
        };
        SearchInputData inputData = new SearchInputData("ji");
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeWhiteSpaceTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("ZZZZ")));
                assert(searchOutputData.getInput().equals(" ZZ "));
                assert(dataAccessObject.getSymbols().equals(symbols));
            }
        };
        SearchInputData inputData = new SearchInputData(" ZZ ");
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeNoMatchesTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of()));
                assert(searchOutputData.getInput().equals(" 0w0 "));
                assert(dataAccessObject.getSymbols().equals(symbols));
            }
        };
        SearchInputData inputData = new SearchInputData(" 0w0 ");
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

}