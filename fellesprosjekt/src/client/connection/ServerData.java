package client.connection;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dataobjects.Appointment;
import dataobjects.Calender;
import dataobjects.Inbox;
import dataobjects.Meeting;
import dataobjects.Message;
import dataobjects.Person;
import dataobjects.Room;

public class ServerData {
	
	private static Calender calender;
	private static Inbox inbox;
	
	private static HashMap<Integer, Appointment> appointments;
	private static HashMap<Integer, Meeting> meetings;
	private static HashMap<Integer, Room> rooms;
	private static HashMap<Integer, Person> persons;
	private static HashMap<Integer, Message> messages;
	
	private static Connection connection;
	
	public static void main(String[] args) throws IOException {
		initialise();
	}
	
	public static void initialise() {
		connection = new Connection();
		try{
			connection.connect();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		appointments = new HashMap<Integer, Appointment>();
		meetings = new HashMap<Integer, Meeting>();
		rooms = new HashMap<Integer, Room>();
		persons = new HashMap<Integer, Person>();
		messages = new HashMap<Integer, Message>();
	}
	
	public static void receiveMessage(Document document){
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = document;
	     
			doc.getDocumentElement().normalize();
	     
			NodeList nodeLst = doc.getElementsByTagName("employee");
	     
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s); 
	    
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	  	            Element fstElmnt = (Element) fstNode;
	  	            NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("firstname");
	  	            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
	  	            NodeList fstNm = fstNmElmnt.getChildNodes();

	  	            NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("lastname");
	  	            Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
	  	            NodeList lstNm = lstNmElmnt.getChildNodes();
				} 

			}
			} catch(ParserConfigurationException pce) {
				pce.printStackTrace();
			}
		
		
	/*	Element rootElement = document.getRootElement();
		Element typeElement = rootElement.getFirstChildElement(XMLElements.MESSAGE_TYPE);
		int type = Integer.parseInt(typeElement.getValue());
		
		if(type == MessageType.RECEIVE_LOGIN){
			
			Element loginresult = rootElement.getFirstChildElement("result");
			int result = Integer.parseInt(loginresult.getValue());
			
		}else if(type == MessageType.RECEIVE_APPOINTMENTS){
			
		}else if(type == MessageType.RECEIVE_MEETINGS){
			
		}*/
		
		
	}
}
