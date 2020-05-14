package com.ondot.kafka.sample;

import com.ondot.kafka.sample.kafkastreams.configuration.ConfigurationReader;
import com.ondot.kafka.sample.serialization.MessageObjectSerializer;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class InputProducerMain
{
	public static void main(String[] args)
	{
		PropertiesConfiguration propertiesConfiguration = ConfigurationReader.getProperties("config.properties");
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfiguration.getString("bootstrapServers"));
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageObjectSerializer.class.getName());

		KafkaProducer<String, MessageObject> producer = new KafkaProducer<>(properties);

		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Input > ");
			String message = sc.nextLine();
			MessageObject messageObject = new MessageObject(message + "_KEY", message);

			ProducerRecord<String, MessageObject> record = new ProducerRecord<>(propertiesConfiguration.getString("inputTopic"), messageObject);
			try {
				producer.send(record, (metadata, exception) -> {
					if (exception != null) {
						exception.printStackTrace();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				producer.flush();
			}

			if(StringUtils.equals(message, "exit")) {
				producer.close();
				break;
			}
		}
	}
}