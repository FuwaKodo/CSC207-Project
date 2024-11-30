package use_cases.favorites;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteStockFileStorage {
    private static final String FILE_PATH = "favorite_stocks.txt";

    public void saveFavoriteStocks(List<String> stockSymbols) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String symbol : stockSymbols) {
                writer.write(symbol);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving favorite stocks: " + e.getMessage());
        }
    }

    public List<String> loadFavoriteStocks() {
        List<String> stockSymbols = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stockSymbols.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
            return stockSymbols;
        } catch (IOException e) {
            System.err.println("Error loading favorite stocks: " + e.getMessage());
        }

        return stockSymbols;
    }

    public boolean addFavoriteStock(String stockSymbol) {
        List<String> stocks = loadFavoriteStocks();
        if (!stocks.contains(stockSymbol)) {
            stocks.add(stockSymbol);
            saveFavoriteStocks(stocks);
            return true;
        }
        return false;
    }

    public boolean removeFavoriteStock(String stockSymbol) {
        List<String> stocks = loadFavoriteStocks();
        if (stocks.remove(stockSymbol)) {
            saveFavoriteStocks(stocks);
            return true;
        }
        return false;
    }
}