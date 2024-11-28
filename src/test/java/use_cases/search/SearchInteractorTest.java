package use_cases.search;

import java.util.List;

import org.junit.Test;

public class SearchInteractorTest {
    // mock data access object to be used in all tests which also check that the data isn't mutated
    final private SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
        @Override
        public List<String> getSymbols() {
            return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
        }
    };

    @Test
    public void executeEmptyTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("A", "A", "AAA", "CBA", "IJ", "ZZZZ")));
                assert(searchOutputData.getInput().isEmpty());
                assert(dataAccessObject.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
            }
        };
        SearchInputData inputData = new SearchInputData("");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeExactMatchTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("CBA", "A", "A", "AAA")));
                assert(searchOutputData.getInput().equals("cba"));
                assert(dataAccessObject.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
            }
        };
        SearchInputData inputData = new SearchInputData("cba");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeCharTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("IJ")));
                assert(searchOutputData.getInput().equals("J"));
                assert(dataAccessObject.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
            }
        };
        SearchInputData inputData = new SearchInputData("J");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeWhiteSpaceTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("ZZZZ")));
                assert(searchOutputData.getInput().equals(" ZZ "));
                assert(dataAccessObject.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
            }
        };
        SearchInputData inputData = new SearchInputData(" ZZ ");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeNoMatchesTest() {
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of()));
                assert(searchOutputData.getInput().equals(" 0w0 "));
                assert(dataAccessObject.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
            }
        };
        SearchInputData inputData = new SearchInputData(" 0w0 ");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

}