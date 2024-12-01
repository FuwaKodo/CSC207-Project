package frameworks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FavoriteStockData {
    private static final String FAVORITE_STOCKS_FILE = "src/main/java/use_cases/favorites/favorites_stocks.txt";

    public Set<String> loadFavoriteStocks() {
        Set<String> favoriteStocks = new HashSet<>();
        File file = new File(FAVORITE_STOCKS_FILE);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    favoriteStocks.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return favoriteStocks;
    }

    public void addFavoriteStock(String symbol) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITE_STOCKS_FILE, true))) {
            writer.write(symbol);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFavoriteStock(String symbol) {
        Set<String> favoriteStocks = loadFavoriteStocks();
        favoriteStocks.remove(symbol);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITE_STOCKS_FILE))) {
            for (String stock : favoriteStocks) {
                writer.write(stock);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}