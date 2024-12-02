package frameworks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for managing favorite stock data persistence.
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * @null This class does not accept null values
 */
public class FavoriteStockData {
    private static final String FAVORITE_STOCKS_FILE = "src/main/java/use_cases/favorites/favorites_stocks.txt";

    /**
     * Loads favorite stocks from file.
     * @return Set of favorite stock symbols
     */
    public Set<String> loadFavoriteStocks() {
        final Set<String> favoriteStocks = new HashSet<>();
        final File file = new File(FAVORITE_STOCKS_FILE);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    favoriteStocks.add(line);
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return favoriteStocks;
    }

    /**
     * Adds a stock symbol to favorites.
     * @param symbol Stock symbol to add
     */
    public void addFavoriteStock(String symbol) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITE_STOCKS_FILE, true))) {
            writer.write(symbol);
            writer.newLine();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Removes a stock symbol from favorites.
     * @param symbol Stock symbol to remove
     */
    public void removeFavoriteStock(String symbol) {
        final Set<String> favoriteStocks = loadFavoriteStocks();
        favoriteStocks.remove(symbol);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITE_STOCKS_FILE))) {
            for (String stock : favoriteStocks) {
                writer.write(stock);
                writer.newLine();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
