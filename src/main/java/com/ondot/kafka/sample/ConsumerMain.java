package com.ondot.kafka.sample;

import com.ondot.kafka.sample.kafkastreams.configuration.ConfigurationReader;
import com.ondot.kafka.sample.serialization.MessageObjectDeserializer;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class ConsumerMain
{
	public static void main(String[] args) {
		PropertiesConfiguration propertiesConfiguration = ConfigurationReader.getProperties("config.properties");
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfiguration.getString("bootstrapServers"));
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageObjectDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, propertiesConfiguration.getString("outputTopic"));

		KafkaConsumer<String, MessageObject> consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Collections.singletonList(propertiesConfiguration.getString("outputTopic")));

		MessageObject messageObject = null;
		try {
			do {
				ConsumerRecords<String, MessageObject> records;
				records = consumer.poll(100000);
				for (ConsumerRecord<String, MessageObject> record : records) {
					messageObject = record.value();
					System.out.println(messageObject.getKey() + "->" + messageObject.getMessage() + ":" + messageObject.getRemark());
				}
			} while (!StringUtils.equals(messageObject.getMessage(), "exit"));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}
}
