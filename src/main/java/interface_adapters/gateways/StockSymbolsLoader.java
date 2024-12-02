package interface_adapters.gateways;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import use_cases.SymbolNameDataAccessInterface;

/**
 * Implementation of data access interface of search use case.
 */
public class StockSymbolsLoader implements SymbolNameDataAccessInterface {
    private final String workingDir = System.getProperty("user.dir");
    private final String filePath = workingDir + "/src/main/java/frameworks/StockSymbols.txt";

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

    @Override
    public String getCompany(String symbol) {
        final List<String> symbolNameList = new ArrayList<>();
        String result = "";
        try {
            symbolNameList.addAll(Files.readAllLines(Path.of(filePath)));
        }
        catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
        }
        for (String str: symbolNameList) {
            if (str.contains(symbol)) {
                result = str.substring(str.indexOf('-') + 1);
                break;
            }
        }
        return result;
    }

    @Override
    public String getSymbol(String company) {
        final List<String> symbolNameList = new ArrayList<>();
        String result = "";
        try {
            symbolNameList.addAll(Files.readAllLines(Path.of(filePath)));
        }
        catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
        }
        for (String str: symbolNameList) {
            if (str.contains(company)) {
                result = str.substring(0, str.indexOf('-'));
                break;
            }
        }
        return result;
    }

}
