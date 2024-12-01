package interface_adapters.gateways;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import use_cases.search.SearchDataAccessInterface;

/**
 * Implementation of data access interface of search use case.
 */
public class StockSymbolsLoader implements SearchDataAccessInterface {
    private final String workingDir = System.getProperty("user.dir");
    private final String filePath = workingDir + "/src/main/java/frameworks/StockSymbols.txt";

    /**
     * Retrieves a list of symbols .
     *
     * @return a list of symbols
     */
    @Override
    public List<String> getSymbols() {
        final List<String> result = new ArrayList<>();
        try {
            result.addAll(Files.readAllLines(Path.of(filePath)));
            for (int i = 0; i < result.size(); i++) {
                result.set(i, result.get(i).substring(0, result.get(i).indexOf('-')));
            }
        }
        catch (IOException error) {
            // Handle exception if file not found or can't be read
            System.err.println("Error reading file: " + error.getMessage());
        }
        return result;
    }
}
