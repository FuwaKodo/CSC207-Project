package test.use_cases.view_stock;

import interface_adapters.gateways.StockDataLoader;
import org.junit.Test;
import use_cases.StockDataInterface;

public class ViewStockInteractorTest {
    private final StockDataInterface stockDataLoader = new StockDataLoader();

    @Test
    public void datelessExecuteTest() {

    }
}
