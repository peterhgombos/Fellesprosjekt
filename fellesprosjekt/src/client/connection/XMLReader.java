package client.connection;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {



	public static void main(String[] args){


		String doc2 = "res/4AppointmentTEST.xml";
		readXML(doc2);

	}

	public static void readXML(String doc2){

		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(doc2);
			Element messageType = doc.getDocumentElement();

			if(messageType.getNodeName().equals("0")){

			}
			else if(messageType.getNodeName().equals("1")){

			}
			else if(messageType.getNodeName().equals("2")){

			}
			else if(messageType.getNodeName().equals("3")){

			}
			else if(messageType.getNodeName().equals(MessageType.RECEIVE_APPOINTMENTS)){
				NodeList nodeList = doc.getElementsByTagName(XMLElements.APPOINTMENT);

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node firstNode = nodeList.item(i);
					System.out.println(firstNode.getNodeName());
					Element e = (Element)firstNode;

					NodeList idElementList = e.getElementsByTagName(XMLElements.APPOINTMENT_ID);
					Element id = (Element) idElementList.item(0);
					NodeList idList = id.getChildNodes();
					System.out.println("AvtaleID : "  + ((Node) idList.item(0)).getNodeValue());

					NodeList leaderElementList = e.getElementsByTagName(XMLElements.LEADER);
					Element leader = (Element) leaderElementList.item(0);
					NodeList leaderList = leader.getChildNodes();
					System.out.println("Leder : "  + ((Node) leaderList.item(0)).getNodeValue());

					NodeList titleElementList = e.getElementsByTagName(XMLElements.TITLE);
					Element title = (Element) titleElementList.item(0);
					NodeList titleList = title.getChildNodes();
					System.out.println("Tittel : "  + ((Node) titleList.item(0)).getNodeValue());

					NodeList descriptionElementList = e.getElementsByTagName(XMLElements.DESCRIPTION);
					Element description = (Element) descriptionElementList.item(0);
					NodeList descriptionList = description.getChildNodes();
					System.out.println("Beskrivelse : "  + ((Node) descriptionList.item(0)).getNodeValue());

					NodeList startTimeElementList = e.getElementsByTagName(XMLElements.STARTTIME);
					Element startTime = (Element) startTimeElementList.item(0);
					NodeList startTimeList = startTime.getChildNodes();
					System.out.println("Starttidspunkt : "  + ((Node) startTimeList.item(0)).getNodeValue());

					NodeList endTimeElementList = e.getElementsByTagName(XMLElements.ENDTIME);
					Element endTime = (Element) endTimeElementList.item(0);
					NodeList endTimeList = endTime.getChildNodes();
					System.out.println("Sluttidspunkt : "  + ((Node) endTimeList.item(0)).getNodeValue());

					NodeList placeElementList = e.getElementsByTagName(XMLElements.PLACE);
					Element place = (Element) placeElementList.item(0);
					NodeList placeList = place.getChildNodes();
					System.out.println("Sted : "  + ((Node) placeList.item(0)).getNodeValue());

				}
			}
			else if(messageType.getNodeName().equals(MessageType.RECEIVE_MEETINGS)){
				NodeList nodeList = doc.getElementsByTagName(XMLElements.MEETING);

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node firstNode = nodeList.item(i);
					System.out.println(firstNode.getNodeName());
					Element e = (Element)firstNode;

					NodeList idElementList = e.getElementsByTagName(XMLElements.MEETING_ID);
					Element id = (Element) idElementList.item(0);
					NodeList idList = id.getChildNodes();
					System.out.println("MøteID : "  + ((Node) idList.item(0)).getNodeValue());

					NodeList leaderElementList = e.getElementsByTagName(XMLElements.LEADER);
					Element leader = (Element) leaderElementList.item(0);
					NodeList leaderList = leader.getChildNodes();
					System.out.println("Leder : "  + ((Node) leaderList.item(0)).getNodeValue());

					NodeList titleElementList = e.getElementsByTagName(XMLElements.TITLE);
					Element title = (Element) titleElementList.item(0);
					NodeList titleList = title.getChildNodes();
					System.out.println("Tittel : "  + ((Node) titleList.item(0)).getNodeValue());

					NodeList descriptionElementList = e.getElementsByTagName(XMLElements.DESCRIPTION);
					Element description = (Element) descriptionElementList.item(0);
					NodeList descriptionList = description.getChildNodes();
					System.out.println("Beskrivelse : "  + ((Node) descriptionList.item(0)).getNodeValue());

					NodeList startTimeElementList = e.getElementsByTagName(XMLElements.STARTTIME);
					Element startTime = (Element) startTimeElementList.item(0);
					NodeList startTimeList = startTime.getChildNodes();
					System.out.println("Starttidspunkt : "  + ((Node) startTimeList.item(0)).getNodeValue());

					NodeList endTimeElementList = e.getElementsByTagName(XMLElements.ENDTIME);
					Element endTime = (Element) endTimeElementList.item(0);
					NodeList endTimeList = endTime.getChildNodes();
					System.out.println("Sluttidspunkt : "  + ((Node) endTimeList.item(0)).getNodeValue());

					NodeList placeElementList = e.getElementsByTagName(XMLElements.PLACE);
					Element place = (Element) placeElementList.item(0);
					NodeList placeList = place.getChildNodes();
					System.out.println("Sted : "  + ((Node) placeList.item(0)).getNodeValue());

					NodeList participantElementList = e.getElementsByTagName(XMLElements.PARTICIPANT);
					Element participant = (Element) participantElementList.item(0);
					NodeList participantList = participant.getChildNodes();
					
					for (int j = 0; j < array.length; j++) {
						
					}
					//Rekursiv
					//i xmldokumentet må man sende bare navnet på fila
					readXML("res/"+((Node)participantList.item(0)).getNodeName() + ".xml");

				}
			}
			else if (messageType.getNodeName().equals(MessageType.RECEIVE_PARTICIPANTS)){
				NodeList nodeList = doc.getElementsByTagName(XMLElements.PARTICIPANT);

//				
//				public static final String PERSON = "person";
//				public static final String FIRSTNAME = "firstname";
//				public static final String SURNAME = "surname";
//				public static final String EMAIL = "email";
//				public static final String USERNAME = "username";
//				public static final String TELEPHONE = "telephone";
//				public static final String PERSON_ID = "pesonID";
				// møteid svar 
				
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node firstNode = nodeList.item(i);
					System.out.println(firstNode.getNodeName());
					Element e = (Element)firstNode;
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
					NodeList surnameElementList = e.getElementsByTagName(XMLElements.SURNAME);
					Element surname = (Element) surnameElementList.item(0);
					NodeList surnameList = surname.getChildNodes();
					System.out.println("Etternavn : "  + ((Node) surnameList.item(0)).getNodeValue());
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
					NodeList firstnameElementList = e.getElementsByTagName(XMLElements.FIRSTNAME);
					Element firstname = (Element) firstnameElementList.item(0);
					NodeList firstnameList = firstname.getChildNodes();
					System.out.println("Fornavns : "  + ((Node) firstnameList.item(0)).getNodeValue());
					
				}
			}

		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}