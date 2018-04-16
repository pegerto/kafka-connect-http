package org.apache.submit.connect.http.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;

public class StockURLRecommender implements ConfigDef.Recommender{

  public static final List<Object> VALID_URLS =
      Arrays.asList("https://api.eu-west-1.stockprice.com", "https://api.us-west-1.stockprice.com");

  @Override
  public List<Object> validValues(String name, Map<String, Object> parsedConfig) {
    return VALID_URLS;
  }

  @Override
  public boolean visible(String name, Map<String, Object> parsedConfig) {
    return true;
  }
}
