package common.dataobjects;

import java.io.Serializable;
import java.util.HashMap;

public class ComMessage implements Serializable{

	private static final long serialVersionUID = 6969440257451501265L;
	
	private Object data;
	private String type;
	private HashMap<String, String> properties;
	
	public ComMessage(Object data, String type){
		this.data = data;
		this.type = type;
		this.properties = new HashMap<String, String>();
	}
	public void setProperty(String key, String value){
		properties.put(key, value);
	}
	public String getProperty(String key){
		return properties.get(key);
	}
	public Object getData(){
		return data;
	}
	public String getType(){
		return type;
	}
}
