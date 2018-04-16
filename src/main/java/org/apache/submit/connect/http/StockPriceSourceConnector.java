package org.apache.submit.connect.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

public class StockPriceSourceConnector extends SourceConnector {


  public static final String HTTP_URL = "stock.http.url";
  public static final String HTTP_INTERVAL = "stock.http.interval";
  public static final String HTTP_TOPIC = "stock.topic";

  private static ConfigDef CONFIG_DEF = new ConfigDef()
        .define(HTTP_URL, Type.STRING, Importance.HIGH, "Url to publish")
        .define(HTTP_INTERVAL, Type.LONG, Importance.HIGH, "Frequency in ms")
        .define(HTTP_TOPIC, Type.STRING, Importance.HIGH, "Topic to publish");

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
    for (int i = 0; i < maxTasks; i++)
      configs.add(configProperties);

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