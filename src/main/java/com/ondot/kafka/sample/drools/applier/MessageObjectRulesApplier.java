package com.ondot.kafka.sample.drools.applier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ondot.kafka.sample.MessageObject;
import com.ondot.kafka.sample.drools.DroolsSessionFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Arrays;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class MessageObjectRulesApplier
{
	private static KieSession KIE_SESSION;

	public MessageObjectRulesApplier(String sessionName) {
		KIE_SESSION = DroolsSessionFactory.createDroolsSession(sessionName);
	}

	/**
	 * Applies the loaded Drools rules to a given String.
	 *
	 * @param value the String to which the rules should be applied
	 * @return the String after the rule has been applied
	 */
	public String applyRule(String value) {
		MessageObject messageObject = (MessageObject) deserialize(value.getBytes());
//		System.out.println("messageObject = " + messageObject);
		FactHandle factHandle = KIE_SESSION.insert(messageObject);
		KIE_SESSION.fireAllRules();
		KIE_SESSION.update(factHandle, messageObject);
//		System.out.println("messageObject = " + messageObject);
		value = new String(serialize(messageObject));
		return value;
	}

	public Object deserialize(byte[] bytes)
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

	public byte[] serialize(Object o)
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
}
