# Naive http connector
 

Fetch a http resource by time intervals.

```
{
  "name": "http",
  "config": {
    "connector.class": "org.apache.submit.connect.http.HttpSourceConnector",
    "tasks.max": 1,
    "http.url": "https://api.github.com/repos/apache/kafka/commits",
    "http.interval": 30000,
    "http.topic": "kafka_github_commits"
  }
}
```
