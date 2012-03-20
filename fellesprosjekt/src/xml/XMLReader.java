package xml;
//package utilities;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import client.connection.MessageType;
//import dataobjects.Appointment;
//import dataobjects.Person;
//
//public class XMLReader {
//
//		public static void main(String[] args){
//			String doc2 = "res/4AppointmentTEST.xml";
//			String doc3 = "res/5meetingTEST.xml";
//			readXML(doc3);
//		}
//	
//		public static void readXML(String doc2){
//	
//			try{
//				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder db;
//				db = dbf.newDocumentBuilder();
//				Document doc = db.parse(doc2);
//				Element messageType = doc.getDocumentElement();
//				XMLReader xml = new XMLReader();
//	
//				if(messageType.getNodeName().equals(MessageType.RECEIVE_APPOINTMENTS)){
//					xml.parseAppointments(doc);
//	
//				}
//				else if(messageType.getNodeName().equals(MessageType.RECEIVE_MEETINGS)){
//					xml.receiveMeeting(doc);
//				}
//				else if (messageType.getNodeName().equals(MessageType.RECEIVE_PARTICIPANTS)){
//					NodeList nodeList = doc.getElementsByTagName(XMLElements.PARTICIPANT);
//				}
//	
//	
//			}
//			catch (ParserConfigurationException e) {
//				e.printStackTrace();
//			}
//			catch (SAXException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//	private Person parseLeader(Node node){
//		//TODO
//		return null;
//	}
//
//	public ArrayList<Appointment> parseAppointments(Document doc){
//
//		NodeList nodeList = doc.getElementsByTagName(XMLElements.APPOINTMENT);
//
//		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
//
//		for (int i = 0; i < nodeList.getLength(); i++) {
//
//			Appointment app = new Appointment();
//
//			Node firstNode = nodeList.item(i);
//			//System.out.println(firstNode.getNodeName());
//			Element e = (Element)firstNode;
//
//			NodeList idElementList = e.getElementsByTagName(XMLElements.APPOINTMENT_ID);
//			Element id = (Element) idElementList.item(0);
//			NodeList idList = id.getChildNodes();
//			app.setId(Integer.parseInt(idList.item(0).getNodeValue()));
//			//System.out.println("AvtaleID : "  + ((Node) idList.item(0)).getNodeValue());
//
//			NodeList leaderElementList = e.getElementsByTagName(XMLElements.LEADER);
//			Element leader = (Element) leaderElementList.item(0);
//			NodeList leaderList = leader.getChildNodes();
//			app.setLeader(idList.item(0).getNodeValue());
//			//System.out.println("Leder : "  + ((Node) leaderList.item(0)).getNodeValue());
//
//			NodeList titleElementList = e.getElementsByTagName(XMLElements.TITLE);
//			Element title = (Element) titleElementList.item(0);
//			NodeList titleList = title.getChildNodes();
//			//System.out.println("Tittel : "  + ((Node) titleList.item(0)).getNodeValue());
//
//			NodeList descriptionElementList = e.getElementsByTagName(XMLElements.DESCRIPTION);
//			Element description = (Element) descriptionElementList.item(0);
//			NodeList descriptionList = description.getChildNodes();
//			//System.out.println("Beskrivelse : "  + ((Node) descriptionList.item(0)).getNodeValue());
//
//			NodeList startTimeElementList = e.getElementsByTagName(XMLElements.STARTTIME);
//			Element startTime = (Element) startTimeElementList.item(0);
//			NodeList startTimeList = startTime.getChildNodes();
//			//System.out.println("Starttidspunkt : "  + ((Node) startTimeList.item(0)).getNodeValue());
//
//			NodeList endTimeElementList = e.getElementsByTagName(XMLElements.ENDTIME);
//			Element endTime = (Element) endTimeElementList.item(0);
//			NodeList endTimeList = endTime.getChildNodes();
//			//System.out.println("Sluttidspunkt : "  + ((Node) endTimeList.item(0)).getNodeValue());
//
//			NodeList placeElementList = e.getElementsByTagName(XMLElements.PLACE);
//			Element place = (Element) placeElementList.item(0);
//			NodeList placeList = place.getChildNodes();
//			//System.out.println("Sted : "  + ((Node) placeList.item(0)).getNodeValue());
//
//		}
//		return appointments;
//	}
//
//	public void receiveMeeting(Document doc){
//		NodeList nodeList = doc.getElementsByTagName(XMLElements.MEETING);
//
//		for (int i = 0; i < nodeList.getLength(); i++) {
//			Node firstNode = nodeList.item(i);
//			System.out.println(firstNode.getNodeName());
//			Element e = (Element)firstNode;
//
//			NodeList idElementList = e.getElementsByTagName(XMLElements.MEETING_ID);
//			Element id = (Element) idElementList.item(0);
//			NodeList idList = id.getChildNodes();
//			System.out.println("MøteID : "  + (idList.item(0)).getNodeValue());
//
//			NodeList leaderElementList = e.getElementsByTagName(XMLElements.LEADER);
//			Element leader = (Element) leaderElementList.item(0);
//			NodeList leaderList = leader.getChildNodes();
//			System.out.println("Leder : "  + ((Node) leaderList.item(0)).getNodeValue());
//
//			NodeList titleElementList = e.getElementsByTagName(XMLElements.TITLE);
//			Element title = (Element) titleElementList.item(0);
//			NodeList titleList = title.getChildNodes();
//			System.out.println("Tittel : "  + ((Node) titleList.item(0)).getNodeValue());
//
//			NodeList descriptionElementList = e.getElementsByTagName(XMLElements.DESCRIPTION);
//			Element description = (Element) descriptionElementList.item(0);
//			NodeList descriptionList = description.getChildNodes();
//			System.out.println("Beskrivelse : "  + ((Node) descriptionList.item(0)).getNodeValue());
//
//			NodeList startTimeElementList = e.getElementsByTagName(XMLElements.STARTTIME);
//			Element startTime = (Element) startTimeElementList.item(0);
//			NodeList startTimeList = startTime.getChildNodes();
//			System.out.println("Starttidspunkt : "  + ((Node) startTimeList.item(0)).getNodeValue());
//
//			NodeList endTimeElementList = e.getElementsByTagName(XMLElements.ENDTIME);
//			Element endTime = (Element) endTimeElementList.item(0);
//			NodeList endTimeList = endTime.getChildNodes();
//			System.out.println("Sluttidspunkt : "  + ((Node) endTimeList.item(0)).getNodeValue());
//
//			NodeList placeElementList = e.getElementsByTagName(XMLElements.PLACE);
//			Element place = (Element) placeElementList.item(0);
//			NodeList placeList = place.getChildNodes();
//			System.out.println("Sted : "  + ((Node) placeList.item(0)).getNodeValue());
//
//			NodeList participantElementList = e.getElementsByTagName(XMLElements.PARTICIPANT);
//			HashMap<Person, Integer> participants = new HashMap<Person, Integer>();
//			for (int j = 0; j < participantElementList.getLength() ;j++) {
//				Participant p = parseParticipant(participantElementList.item(j));
//				participants.put(p.p, p.svar);
//			}
//
//		}
//	}
//
//	public Participant parseParticipant(Node node){
//		
//		Person person = new Person();
//		
//		Element e = (Element)node;
//
//		NodeList idElementList = e.getElementsByTagName(XMLElements.PERSON_ID);
//		Element id = (Element) idElementList.item(0);
//		NodeList idList = id.getChildNodes();
//		person.setPersonID(Integer.parseInt(idList.item(0).getNodeValue()));
//		//System.out.println("PersonID : "  + ((Node) idList.item(0)).getNodeValue());
//
//		NodeList firstNameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
//		Element firstName = (Element) firstNameElementList.item(0);
//		NodeList firstNameList = firstName.getChildNodes();
//		person.setFirstname(firstNameList.item(0).getNodeValue());
//		//System.out.println("Fornavn: " + ((Node)firstNameList.item(0)).getNodeValue());
//
//		NodeList surNameElementList = e.getElementsByTagName(XMLElements.SURNAME);
//		Element surName = (Element) surNameElementList.item(0);
//		NodeList surNameList = surName.getChildNodes();
//		person.setSurname(surNameList.item(0).getNodeValue());
//		//System.out.println("Etternavn: " + ((Node)surNameList.item(0)).getNodeValue());
//
//		NodeList emailElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
//		Element email = (Element) emailElementList.item(0);
//		NodeList emailList = email.getChildNodes();
//		person.setEmail(emailList.item(0).getNodeValue());
//		//System.out.println("Email: " + ((Node)emailList.item(0)).getNodeValue());
//
//		NodeList usernameElementList = e.getElementsByTagName(XMLElements.USERNAME);
//		Element username = (Element) usernameElementList.item(0);
//		NodeList usernameList = username.getChildNodes();
//		person.setUsername(usernameList.item(0).getNodeValue());
//		//System.out.println("Username: " + ((Node)usernameList.item(0)).getNodeValue());
//
//		NodeList telephoneElementList = e.getElementsByTagName(XMLElements.TELEPHONE);
//		Element telephone = (Element) telephoneElementList.item(0);
//		NodeList telephoneList = telephone.getChildNodes();
//		person.setTelephone(telephoneList.item(0).getNodeValue());
//		//System.out.println("telephone: " + ((Node)telephoneList.item(0)).getNodeValue());
//		
//		int svar = 0; //TODO Implement svar properly
//		Participant participant = new Participant(person, svar);
//		
//		return participant;
//	}
//
//	private class Participant {
//		public Person p;
//		public int svar;
//		
//		public Participant(Person person, int svar) {
//			this.p  = person;
//			this.svar = svar;
//		}
//		
//	}
//}
//
//
