package com.ondot.kafka.sample;

import com.ondot.kafka.sample.kafkastreams.configuration.ConfigurationReader;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class KafkaStreamsDroolsMain
{

    public static void main(String[] args) {
        PropertiesConfiguration properties = ConfigurationReader.getProperties("config.properties");
        KafkaStreamsRunner.runKafkaStream(properties);
    }
}
