package use_cases.search;

import java.util.List;

import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchViewModel;
import org.junit.Test;

public class SearchInteractorTest {

    @Test
    public void executeEmptyTest() {
        SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
            }
        };

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ")));
                assert(searchOutputData.getInput().isEmpty());
            }

            @Override
            public void error(String error) {
                assert(false);
            }
        };
        SearchInputData inputData = new SearchInputData("");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeExactMatchTest() {
        SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
            }
        };

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("CBA", "a", "A", "AAA")));
                assert(searchOutputData.getInput().equals("cba"));
            }

            @Override
            public void error(String error) {
                assert(false);
            }
        };
        SearchInputData inputData = new SearchInputData("cba");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeCharTest() {
        SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
            }
        };

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("IJ")));
                assert(searchOutputData.getInput().equals("J"));
            }

            @Override
            public void error(String error) {
                assert(false);
            }
        };
        SearchInputData inputData = new SearchInputData("J");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeWhiteSpaceTest() {
        SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
            }
        };

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of("ZZZZ")));
                assert(searchOutputData.getInput().equals(" ZZ "));
            }

            @Override
            public void error(String error) {
                assert(false);
            }
        };
        SearchInputData inputData = new SearchInputData(" ZZ ");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

    @Test
    public void executeNoMatchesTest() {
        SearchDataAccessInterface dataAccessObject = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("a", "A", "AAA", "CBA", "Ij", "ZZZZ");
            }
        };

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchOutputBoundary searchPresenter = new SearchOutputBoundary() {
            @Override
            public void displayResult(SearchOutputData searchOutputData) {
                assert(searchOutputData.getSymbols().equals(List.of()));
                assert(searchOutputData.getInput().equals(" 0w0 "));
            }

            @Override
            public void error(String error) {
                assert(false);
            }
        };
        SearchInputData inputData = new SearchInputData(" 0w0 ");
        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);

        searchInteractor.execute(inputData);
    }

}