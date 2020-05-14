package com.ondot.kafka.sample.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ondot.kafka.sample.MessageObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;
import java.util.Map;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class MessageObjectDeserializer implements Deserializer
{
	@Override public void configure(Map map, boolean b)
	{
	}

	@Override public Object deserialize(String s, byte[] bytes)
	{
		ObjectMapper mapper = new ObjectMapper();
		MessageObject messageObject = null;
		try {
//			System.out.println("bytes = " + Arrays.toString(bytes));
			messageObject = mapper.readValue(bytes, MessageObject.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return messageObject;
	}

	@Override public void close()
	{
	}
}
