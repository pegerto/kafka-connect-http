package org.apache.submit.connect.http.config;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

public class StockURLValidator implements ConfigDef.Validator{

  @Override
  public void ensureValid(String name, Object value) {
    if (StockURLRecommender.VALID_URLS.contains(value))
      return;

    if (value != null)
      throw new ConfigException(name, value, "The url is not on the accepted list");

  }
}
