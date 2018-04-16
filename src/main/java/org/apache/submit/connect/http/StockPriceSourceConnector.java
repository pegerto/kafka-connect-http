package org.apache.submit.connect.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Width;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.util.ConnectorUtils;
import org.apache.submit.connect.http.config.StockURLRecommender;
import org.apache.submit.connect.http.config.StockURLValidator;

public class StockPriceSourceConnector extends SourceConnector {


  public static final String STOCK_HTTP_URL = "stock.http.url";
  public static final String STOCK_HTTP_INTERVAL = "stock.http.interval";
  public static final String STOCK_TOPIC = "stock.topic";
  public static final String STOCK_MARKETS = "stock.markets";

  private static ConfigDef CONFIG_DEF = new ConfigDef()
        .define(STOCK_HTTP_URL, Type.STRING, null,  new StockURLValidator(), Importance.HIGH,
            "Url to publish", null, 0, Width.LONG, "URL to publish",
            Collections.EMPTY_LIST, new StockURLRecommender())
        .define(STOCK_HTTP_INTERVAL, Type.LONG, Importance.HIGH, "Frequency in ms")
        .define(STOCK_TOPIC, Type.STRING, Importance.HIGH, "Topic to publish")
        .define(STOCK_MARKETS, Type.STRING, Importance.HIGH, "Comma separated list of markets");


  private Map<String, String> configProperties;

  @Override
  public String version() {
    return "kafka-submit";
  }

  @Override
  public void start(Map<String, String> props) {
    configProperties = props;
  }

  @Override
  public Class<? extends Task> taskClass() {
    return StockPriceSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    final List<Map<String,String>> configs = new ArrayList<>();
    List<String> markets = Arrays.asList(configProperties.get(STOCK_MARKETS).split(","));
    int groups = Math.min(markets.size(), maxTasks);

    List<List<String>> marketsGrouped=  marketsGrouped =
        ConnectorUtils.groupPartitions(markets, groups);

    for (int i = 0; i < maxTasks; i++) {
      Map<String, String> taskProps = new HashMap<>(configProperties);
      taskProps.put(STOCK_MARKETS, String.join(",", marketsGrouped.get(i)));
      configs.add(taskProps);
    }

    return configs;
  }


  @Override
  public Config validate(Map<String, String> connectorConfigs) {
    return super.validate(connectorConfigs);
  }

  @Override
  public void stop() {

  }

  @Override
  public ConfigDef config() {
    return CONFIG_DEF;
  }
}