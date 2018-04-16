package org.apache.submit.connect.http;


import static org.apache.submit.connect.http.StockPriceSourceConnector.STOCK_MARKETS;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class StockPriceSourceConnectorTest {

  @Test
  public void testConfigurationPerTasks(){
    StockPriceSourceConnector connector = new StockPriceSourceConnector();
    HashMap<String, String> conf = new HashMap<>();
    connector.start(conf);
    conf.put(STOCK_MARKETS, "tky,nyc,rom");

    List<Map<String, String>> out = connector.taskConfigs(1);
    assertTrue(out.size() == 1);


    out = connector.taskConfigs(2);
    assertTrue(out.size() == 2);
    System.out.print(out);

  }


}