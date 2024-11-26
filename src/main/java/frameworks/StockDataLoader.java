package frameworks;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.SharePrices;
import org.json.JSONArray;
import org.json.JSONObject;

import entities.MetricValues;
import interface_adapters.gateways.ApiDataLoader;
import interface_adapters.gateways.PolygonApiLoader;

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
public class StockDataLoader {
    private ApiDataLoader gatewayLoader;
    private String workingDir = System.getProperty("user.dir");
    private String filePath = workingDir + "/src/main/java/app/StockData.json";
    private JSONArray stockData;

    public StockDataLoader() {
        // The stock data loader uses the Polygon API gateway, but an interface allows
        // future users to switch to other APIs if data structures change.
        gatewayLoader = new PolygonApiLoader();
        stockData = convertFileToJsonArray();
        createStockDataJSON();
    }

    /*
    public Double getDataExample(String stockSymbol, Date date) {
        Double volumeValue = Double.NaN;
        if (checkStockDataJSON()){
            final String workingDir = System.getProperty("user.dir");
            final String filePath = workingDir + "/src/main/java/app/StockData.json";
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read the JSON file into a String
                StringBuilder jsonContent = new StringBuilder();
                String line;
                jsonContent.append("[");
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                jsonContent.append("]");
                reader.close();

                // Parse the JSON content as a JSONArray
                JSONArray jsonArray = new JSONArray(jsonContent.toString());

                // Iterate over each JSON object in the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Access individual fields
                    double volume = jsonObject.getDouble("volume");
                    String symbol = jsonObject.getString("symbol");
                    double high = jsonObject.getDouble("high");
                    double preMarket = jsonObject.getDouble("preMarket");
                    double low = jsonObject.getDouble("low");
                    // String date = jsonObject.getString("from");
                    double afterHours = jsonObject.getDouble("afterHours");
                    double close = jsonObject.getDouble("close");
                    double open = jsonObject.getDouble("open");
                    String status = jsonObject.getString("status");

                    // Print the JSON object or its fields
                    System.out.println("Entry " + (i + 1) + ":");
                    System.out.println("Symbol: " + symbol);
                    System.out.println("Volume: " + volume);
                    System.out.println("High: " + high);
                    System.out.println("Date: " + date);
                    System.out.println("Status: " + status);
                    System.out.println("------------------------");
                }
            }

            catch (IOException ioException) {
                volumeValue = Double.NaN;
            }
        }

        return volumeValue;
    }
    Just experimentation.
    */

    /*
    public JSONArray loadJSONArray() {
        // May not be necessary at all!
        JSONArray jsonArray = null;
        if (checkStockDataJSON()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                final StringBuilder jsonContent = new StringBuilder();
                String line;
                jsonContent.append("[");
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                jsonContent.append("]");
                reader.close();

                jsonArray = new JSONArray(jsonContent.toString());
            }
            catch (IOException ioException) {
                System.out.println("loadJSONArray Error: " + ioException.getMessage());
            }
        }
        return jsonArray;
    }
    */

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
        return jsonObject.has("status") && jsonObject.getString("status").equals("OK");
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

    /**
     * Retrieves the trading volume for a specific company identified by its stock symbol on a given date.
     *
     * <p>The method first checks if the requested stock data is present in the existing file.
     * If found, it parses the file to retrieve the volume value. If the data is not found locally,
     * the method updates the stock data by fetching it from the gateway and retrieves the volume
     * value from the updated entry.</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date the date for which the trading volume is requested.
     * @return the trading volume as a {@link Double} value. Returns {@link Double#NaN}
     *         if the data is unavailable or an I/O error occurs while reading the file.
     */
    public Double getVolume(String stockSymbol, Date date) {
        double volumeValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            volumeValue = entry.getDouble("volume");
        }

        return volumeValue;
    }

    /**
     * Retrieves the trading volumes for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the trading volume for each date. The volumes and corresponding dates are then stored
     * in a {@link MetricValues} object, which is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of trading volume values for each date in the range.</li>
     *             <li>A list of corresponding dates for the volume values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
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

    /**
     * Retrieves the after-hours trading value for a specific company identified by its stock symbol on a given date.
     *
     * <p>This method checks whether the after-hours trading data for the specified stock symbol and date is
     * available in the local JSON file. If the entry exists and is valid, it extracts the "afterHours" value.
     * If the entry is not found, the method updates the stock data by fetching a new entry from the gateway
     * and then retrieves the "afterHours" value from the updated entry.</p>
     *
     * <p>Entries are validated using {@code isValidEntry} to ensure they are relevant (e.g., not from non-trading
     * days or invalid stock symbols).</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date the date for which the after-hours trading value is requested.
     * @return the after-hours trading value as a {@link Double}. Returns {@link Double#NaN} if the data is
     *         unavailable or invalid.
     */
    public Double getAfterHour(String stockSymbol, Date date) {
        double afterHourValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            afterHourValue = entry.getDouble("afterHours");
        }

        return afterHourValue;
    }

    /**
     * Retrieves the after-hours trading values for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the after-hours trading value for each date using {@link #getAfterHour(String, Date)}.
     * The after-hours values and corresponding dates are stored in a {@link MetricValues} object, which
     * is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of after-hours trading values for each date in the range.</li>
     *             <li>A list of corresponding dates for the after-hours values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
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

    /**
     * Retrieves the premarket trading value for a specific company identified by its stock symbol on a given date.
     *
     * <p>The method first checks whether the requested stock data is present in the local JSON file.
     * If found, it parses the file to retrieve the "preMarket" value. If the data is not found locally,
     * the method updates the stock data by fetching it from the gateway and retrieves the "preMarket" value
     * from the newly created entry.</p>
     *
     * <p>Entries are validated using {@code isValidEntry} to ensure they are relevant (e.g., not from
     * non-trading days or invalid stock symbols).</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which the premarket trading value is requested.
     * @return the premarket trading value as a {@link Double}. Returns {@link Double#NaN} if the data is
     *         unavailable or invalid.
     */
    public Double getPremarket(String stockSymbol, Date date) {
        double premarketValue = Double.NaN;

        final JSONObject entry = findOrCreateEntry(stockSymbol, date);

        if (entry != null && isValidEntry(entry)) {
            premarketValue = entry.getDouble("preMarket");
        }

        return premarketValue;
    }

    /**
     * Retrieves the premarket trading values for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the premarket trading value for each date using {@link #getPremarket(String, Date)}.
     * The premarket values and corresponding dates are stored in a {@link MetricValues} object, which
     * is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of premarket trading values for each date in the range.</li>
     *             <li>A list of corresponding dates for the premarket values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
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

    /**
     * Retrieves the share price data (open, close, high, and low prices) for a specific stock symbol on a given date.
     *
     * <p>This method retrieves the stock data for the specified stock symbol and date from a local JSON file
     * or fetches it from the gateway if the data does not exist locally. The method validates the entry to
     * ensure it is relevant (e.g., not from non-trading days or invalid stock symbols) before extracting the
     * open, close, high, and low prices.</p>
     *
     * <p>The extracted data is returned as a {@link SharePrices} object, which contains:
     * <ul>
     *     <li>A list of dates (one in this case).</li>
     *     <li>Lists of open, close, high, and low prices corresponding to the date.</li>
     * </ul></p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which the share price data is requested.
     * @return a {@link SharePrices} object containing:
     *         <ul>
     *             <li>The date of the requested stock data.</li>
     *             <li>The open, close, high, and low prices for the stock on the given date.</li>
     *         </ul>
     *         If the data is unavailable or the entry is invalid, the prices are set to {@link Double#NaN}.
     */
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

    /**
     * Retrieves the share prices (open, close, high, and low) for a specific stock symbol over a given date range.
     *
     * <p>This method iterates through all the dates in the specified range (inclusive) and aggregates the
     * share prices for each day into a {@link SharePrices} object. The resulting {@link SharePrices} object
     * contains lists of dates and their corresponding open, close, high, and low prices.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link SharePrices} object containing:
     *         <ul>
     *             <li>Lists of open, close, high, and low prices for the specified date range.</li>
     *             <li>A list of corresponding dates for the retrieved prices.</li>
     *         </ul>
     *         Returns {@code null} if the date range is invalid (e.g., {@code startDate} or {@code endDate}
     *         is {@code null}).
     */
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
