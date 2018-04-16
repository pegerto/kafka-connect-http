package org.apache.submit.connect.http;

import static org.apache.submit.connect.http.StockPriceSourceConnector.*;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;

public class StockPriceSourceTask extends SourceTask {

  private Logger logger = getLogger(SourceTask.class);
  private String topic;
  private Long interval;
  private String url;
  private Long last_execution = 0L;


  @Override
  public String version() {
    return null;
  }

  @Override
  public void start(Map<String, String> props) {
    topic = props.get(HTTP_TOPIC);
    interval = Long.valueOf(props.get(HTTP_INTERVAL));
    url = props.get(HTTP_URL);

    logger.info("Starting to fetch {} each {} ms ", url, interval);
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    if (System.currentTimeMillis() > (last_execution + interval)) {
      last_execution = System.currentTimeMillis();
      logger.info("Pooling url: {}", url);
      return Collections.singletonList(
          new SourceRecord(null,
              null,
              topic,Schema.BYTES_SCHEMA,
              getUrlContents(url)));

    }
    return Collections.EMPTY_LIST;
  }

  @Override
  public void stop() {

  }

  private static byte[] getUrlContents(String sourceUrl) {
    StringBuilder content = new StringBuilder();
    try
    {
      URL stockURL = new URL(sourceUrl);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(stockURL.openStream()));

      String inputLine;
      while ((inputLine = in.readLine()) != null)
        content.append(inputLine);
      in.close();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return content.toString().getBytes();
  }

}
