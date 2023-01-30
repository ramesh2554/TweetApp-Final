
### Elasticsearch

1. Open elasticsearch/bin/ -> Run the "**elasticsearch.bat**" using the command prompt. 
2.  Elasticsearch can accessed at localhost:9200

### Kibana 

1. Modify the "**kibana.yml**" to point to the "*elasticsearch instance*". In our case this will be 9200. So uncomment(http://localhost/9200) the following line in kibana.yml
2. Run -> goto kibana/bin/ -> cmd -> kibana.bat 
3. Kibana can accessed at localhost:5601

### Logstash

1. Create a configuration file named "**logstash.conf**"-> inside logstash/bin
2. Run->cmd-> logstash -f logstash.conf
3. logstash can accessed at localhost:9600

![ELK-Processing-Image](https://raw.githubusercontent.com/rameswarakonakalla/Tweetapp-Final/main/TweetAppComponent2/ELK%20Processing.png)
