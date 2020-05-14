package com.ondot.kafka.sample;

import java.io.Serializable;

/**
 * @author jared (Sangwon.Park@ondotsystems.com)
 * @version 1.0 2020/05/12
 */
public class MessageObject implements Serializable
{
	private static final long serialVersionUID = -4675072300733916513L;
	private String key;
	private String message;
	private String remark;

	public MessageObject()
	{
	}

	public MessageObject(String key, String message)
	{
		this.key = key;
		this.message = message;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override public String toString()
	{
		return "MessageObject{" + "key='" + key + '\'' + ", message='" + message + '\'' + ", remark='" + remark + '\'' + '}';
	}
}