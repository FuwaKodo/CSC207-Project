package interface_adapters.gateways;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.MetricValues;
import entities.SharePrices;
import use_cases.StockDataInterface;

/**
 * StockDataLoader is responsible for loading stock data from a cache or an API.
 *
 * <p>This class checks the cache for stock data before making a request to the
 * API through the gateway. If data is fetched from the API, it updates the cache
 * to optimize future requests.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Check and retrieve stock data from the cache.</li>
 *     <li>Request stock data from the API if not available in the cache.</li>
 *     <li>Update the cache with API data.</li>
 * </ul>
 */
public class StockDataLoader implements StockDataInterface {
    private ApiDataLoader gatewayLoader;
    private String workingDir = System.getProperty("user.dir");
    private String filePath = workingDir + "/src/main/java/frameworks/StockData.json";
    private String statusKey = "status";

    public StockDataLoader() {
        // The stock data loader uses the Polygon API gateway, but an interface allows
        // future users to switch to other APIs if data structures change.
        gatewayLoader = new PolygonApiLoader();
        createStockDataJSON();
    }

    /**
     * Checks whether the stock data JSON file exists at the specified file path.
     *
     * <p>This method verifies the existence of a file located at {@code filePath}.
     * It returns {@code true} if the file exists, and {@code false} otherwise.</p>
     *
     * @return {@code true} if the stock data JSON file exists; {@code false} otherwise.
     */
    public boolean checkStockDataJSON() {
        final File jsonFile = new File(filePath);
        return jsonFile.exists();
    }

    /**
     * Creates a new stock data JSON file if it does not already exist.
     *
     * <p>This method checks whether the stock data JSON file exists at the specified
     * {@code filePath}. If the file does not exist, it attempts to create the file
     * along with any necessary parent directories. If the file is successfully created,
     * the method returns {@code true}; otherwise, it returns {@code false}.</p>
     *
     * @return {@code true} if the file was successfully created; {@code false} if the
     *         file already exists or an error occurred during creation.
     */
    public boolean createStockDataJSON() {
        boolean isNewFile = false;
        if (!checkStockDataJSON()) {
            final File jsonFile = new File(filePath);
            try {
                // Create parent directories if they don't exist
                jsonFile.getParentFile().mkdirs();
                // Create the file
                if (jsonFile.createNewFile()) {
                    isNewFile = true;
                }
            }

            catch (IOException ioException) {
                System.out.println("createStockDataJSON Error: " + ioException.getMessage());
            }
        }

        return isNewFile;
    }

    /**
     * Converts the content of a file specified by the `filePath` into a {@link JSONArray}.
     *
     * <p>This method reads the file line by line, formats the content as a JSON string
     * array, and parses it into a {@link JSONArray}. If an {@link IOException} occurs
     * during the file reading process, an error message is printed, and the method
     * returns a {@link JSONArray} based on the partially constructed content (or an
     * empty array if no content was read).</p>
     *
     * @return a {@link JSONArray} containing the parsed content of the file. Returns an
     *      empty {@link JSONArray} if an error occurs or the file is empty.
     */
    public JSONArray convertFileToJsonArray() {
        final StringBuilder jsonContent = new StringBuilder();
        try {
            String jsonEntry;
            final BufferedReader reader = new BufferedReader(new FileReader(filePath));
            jsonContent.append("[");
            while ((jsonEntry = reader.readLine()) != null) {
                jsonContent.append(jsonEntry);
            }
            jsonContent.append("]");
            reader.close();
        }

        catch (IOException ioException) {
            System.out.println("convertFileToJsonArray Error: " + ioException.getMessage());
        }

        return new JSONArray(jsonContent.toString());
    }

    /**
     * Updates the stock data file with a new stock entry for the specified stock symbol and date.
     *
     * <p>This method retrieves a new stock entry using the {@code gatewayLoader}, appends it
     * to the existing stock data file (if it is not empty), and writes the updated content
     * back to the file. If the file is empty, it writes only the new stock entry. In case
     * of an {@link IOException}, an error message is logged, and the method continues to
     * return the new entry.</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date the date for which the stock data is being updated.
     * @return a {@link JSONObject} representing the newly added stock entry.
     */
    public JSONObject updateStockData(String stockSymbol, Date date) {
        final JSONObject newEntry = gatewayLoader.loadOneEntry(stockSymbol, date);
        try {
            String stockDataAdditionalContent = "";
            final String stockDataContent = Files.readString(Paths.get(filePath));
            final String stockDataContentTrim = stockDataContent.trim();
            // Format StockData.json
            if (stockDataContentTrim.isEmpty()) {
                stockDataAdditionalContent = newEntry.toString();
            }

            else {
                stockDataAdditionalContent = stockDataContent + "," + "\n" + newEntry.toString();
            }

            Files.writeString(Paths.get(filePath), stockDataAdditionalContent);
        }

        catch (IOException ioException) {
            System.out.println("updateStockData Error: " + ioException.getMessage());
        }

        return newEntry;
    }

    /**
     * Clears the time fields of the given Calendar object, setting the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     */
    public void clearTimeFields(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Adds the specified number of days to the given Calendar object and resets the time to midnight.
     * @param calendar the Calendar object to modify. Must not be null.
     * @param days the number of days to add. Can be positive or negative.
     * @return Date object representing the updated day after adding specified number of days
     */
    public Date addDay(Calendar calendar, int days) {
        calendar.add(Calendar.DATE, days);
        clearTimeFields(calendar);
        return calendar.getTime();
    }

    /**
     * Converts a {@link Date} object to a string in the format "yyyy-MM-dd".
     *
     * <p>This method uses {@link SimpleDateFormat} to format the provided {@link Date}
     * into a string representation with the pattern "yyyy-MM-dd". For example,
     * a {@link Date} representing January 1, 2024, would be converted to "2024-01-01".</p>
     *
     * @param date the {@link Date} to be converted to a string.
     * @return a {@link String} representing the formatted date in "yyyy-MM-dd" format.
     * @throws NullPointerException if the provided {@link Date} is {@code null}.
     */
    public String dateToString(Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * Checks if a given JSON object matches the specified stock symbol and date.
     *
     * <p>This method compares the "symbol" and "date" fields of the provided {@link JSONObject}
     * with the given stock symbol and date. It returns {@code true} if both fields match;
     * otherwise, it returns {@code false}.</p>
     *
     * @param stockSymbol the stock symbol to check (e.g., "AAPL" for Apple Inc.).
     * @param date        the date to check for the stock symbol.
     * @param jsonObject  the {@link JSONObject} to compare against.
     * @return {@code true} if the JSON object matches both the stock symbol and date; {@code false} otherwise.
     */
    public boolean matchesStockSymbolAndDate(String stockSymbol, Date date, JSONObject jsonObject) {
        boolean isValidEntry = false;
        if (jsonObject.getString("from").equals(dateToString(date))
                && jsonObject.getString("symbol").equals(stockSymbol)) {
            isValidEntry = true;
        }

        return isValidEntry;
    }

    /**
     * Checks whether a given {@link JSONObject} represents a valid entry for stock data.
     *
     * <p>This method validates a JSON object by checking if it contains a "status" key
     * with the value "OK". Entries with a status of "NOT_FOUND" or missing the "status"
     * key are considered invalid. This is useful for filtering out data for non-trading
     * days (e.g., weekends or holidays).</p>
     *
     * @param jsonObject the {@link JSONObject} to validate.
     * @return {@code true} if the JSON object contains a "status" key with the value "OK";
     *         {@code false} otherwise.
     */
    public boolean isValidEntry(JSONObject jsonObject) {
        return jsonObject.has(statusKey) && jsonObject.getString(statusKey).equals("OK");
    }

    /**
     * Retrieves the reason for missing stock data for a specified stock symbol and date.
     *
     * <p>This method ensures that an entry for the given stock symbol and date exists in the
     * JSON data file (e.g., StockData.json). If the entry does not exist, it is created.
     * The method then returns the "status" field from the corresponding JSON entry, which
     * indicates the reason for the missing data.
     * </p>
     *
     * @param stockSymbol the stock symbol for which to query the missing data reason
     *                    (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which to query the missing data reason.
     * @return a {@code String} representing the "status" field of the JSON entry.
     *         If the "status" field is missing, returns "Unknown status".
     * @throws IllegalStateException if the entry could not be found or created.
     * @throws RuntimeException      if an unexpected error occurs during processing.
     */
    public String reasonMissingData(String stockSymbol, Date date) {
        // At this point the entry should already be stored within the StockData.json
        final JSONObject jsonMissing = findOrCreateEntry(stockSymbol, date);
        return jsonMissing.getString(statusKey);
    }

    /**
     * Finds an existing JSON entry for the specified stock symbol and date, or creates a new one if it does not exist.
     *
     * <p>This method iterates through a {@link JSONArray} to search for a {@link JSONObject} that matches
     * the given stock symbol and date. If a matching entry is found, it is returned. If no match is found,
     * a new entry is created by updating the stock data through the gateway, and the newly created entry
     * is returned.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which the stock data is being searched.
     * @return a {@link JSONObject} containing the stock data for the specified stock symbol and date.
     *         If no matching entry exists, a newly created {@link JSONObject} is returned.
     */
    private JSONObject findOrCreateEntry(String stockSymbol, Date date) {
        final JSONArray contentJsonArray = convertFileToJsonArray();
        JSONObject matchingEntry = null;

        for (int i = 0; i < contentJsonArray.length(); i++) {
            final JSONObject jsonObject = contentJsonArray.getJSONObject(i);
            if (matchesStockSymbolAndDate(stockSymbol, date, jsonObject)) {
                matchingEntry = jsonObject;
                break;
            }
        }

        if (matchingEntry == null) {
            matchingEntry = updateStockData(stockSymbol, date);
        }

        return matchingEntry;
    }

    @Override
    public Double getVolume(String stockSymbol, Date date) {
        double volumeValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            volumeValue = entry.getDouble("volume");
        }

        return volumeValue;
    }

    @Override
    public MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate) {
        MetricValues volumesData = null;
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            final ArrayList<Double> volumesValue = new ArrayList<>();
            final ArrayList<Date> volumesDate = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFields(calendar);
            while (!calendar.getTime().after(endDate)) {
                final Date currentDate = calendar.getTime();
                volumesValue.add(getVolume(stockSymbol, currentDate));
                volumesDate.add(currentDate);
                addDay(calendar, 1);
            }

            volumesData = new MetricValues(volumesValue, volumesDate);
        }

        return volumesData;
    }

    @Override
    public Double getAfterHour(String stockSymbol, Date date) {
        double afterHourValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            afterHourValue = entry.getDouble("afterHours");
        }

        return afterHourValue;
    }

    @Override
    public MetricValues getAfterHours(String stockSymbol, Date startDate, Date endDate) {
        MetricValues afterHoursData = null;
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            final ArrayList<Double> afterHoursValue = new ArrayList<>();
            final ArrayList<Date> afterHoursDate = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFields(calendar);
            while (!calendar.getTime().after(endDate)) {
                final Date currentDate = calendar.getTime();
                afterHoursValue.add(getAfterHour(stockSymbol, currentDate));
                afterHoursDate.add(currentDate);
                addDay(calendar, 1);
            }

            afterHoursData = new MetricValues(afterHoursValue, afterHoursDate);
        }

        return afterHoursData;
    }

    @Override
    public Double getPremarket(String stockSymbol, Date date) {
        double premarketValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            premarketValue = entry.getDouble("preMarket");
        }

        return premarketValue;
    }

    @Override
    public MetricValues getPremarkets(String stockSymbol, Date startDate, Date endDate) {
        MetricValues premarketsData = null;
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            final ArrayList<Double> premarketsValue = new ArrayList<>();
            final ArrayList<Date> premarketsDate = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFields(calendar);
            while (!calendar.getTime().after(endDate)) {
                final Date currentDate = calendar.getTime();
                premarketsValue.add(getPremarket(stockSymbol, currentDate));
                premarketsDate.add(currentDate);
                addDay(calendar, 1);
            }

            premarketsData = new MetricValues(premarketsValue, premarketsDate);
        }

        return premarketsData;
    }

    @Override
    public SharePrices getSharePrice(String stockSymbol, Date date) {
        final List<Date> datesData = List.of(date);
        final List<Double> openPrices = new ArrayList<>();
        final List<Double> closePrices = new ArrayList<>();
        final List<Double> highPrices = new ArrayList<>();
        final List<Double> lowPrices = new ArrayList<>();

        Double openPrice = Double.NaN;
        Double closePrice = Double.NaN;
        Double highPrice = Double.NaN;
        Double lowPrice = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            openPrice = entry.getDouble("open");
            closePrice = entry.getDouble("close");
            highPrice = entry.getDouble("high");
            lowPrice = entry.getDouble("low");
        }

        openPrices.add(openPrice);
        closePrices.add(closePrice);
        highPrices.add(highPrice);
        lowPrices.add(lowPrice);

        return new SharePrices(datesData, openPrices, closePrices, highPrices, lowPrices);
    }

    @Override
    public SharePrices getSharePrices(String stockSymbol, Date startDate, Date endDate) {
        SharePrices sharePrices = null;
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            final List<Date> datesData = new ArrayList<>();
            final List<Double> openPrices = new ArrayList<>();
            final List<Double> closePrices = new ArrayList<>();
            final List<Double> highPrices = new ArrayList<>();
            final List<Double> lowPrices = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFields(calendar);
            while (!calendar.getTime().after(endDate)) {
                final Date currentDate = calendar.getTime();
                final SharePrices currentSharePrice = getSharePrice(stockSymbol, currentDate);
                openPrices.addAll(currentSharePrice.getOpenPrices());
                closePrices.addAll(currentSharePrice.getClosePrices());
                highPrices.addAll(currentSharePrice.getHighPrices());
                lowPrices.addAll(currentSharePrice.getLowPrices());
                datesData.add(currentDate);
                addDay(calendar, 1);
            }

            sharePrices = new SharePrices(datesData, openPrices, closePrices, highPrices, lowPrices);
        }

        return sharePrices;
    }
}
