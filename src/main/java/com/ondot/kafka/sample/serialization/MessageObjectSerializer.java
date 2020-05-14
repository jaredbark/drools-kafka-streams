package com.ondot.kafka.sample.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Arrays;
import java.util.Map;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class MessageObjectSerializer implements Serializer
{
	@Override public void configure(Map map, boolean b)
	{
	}

	@Override public byte[] serialize(String s, Object o)
	{
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(o).getBytes();
//			System.out.println("retVal = " + Arrays.toString(retVal));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override public void close()
	{
	}
}
