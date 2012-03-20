package client.gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Sidepanel extends JPanel implements FocusListener{
	
	private JButton message;
	private JButton newAppointment;
	private JButton newMeeting;
	private JButton myAppointments;
	private JButton employeesAppointments;
	private JButton logOut;
	private GuiConstants guiConstants;
	private JButton addEmployee;
	private JPanel employeeList;
	private JList selectedEmployeeList;
	private JTextField search;
	private int countEmployee;
	private int x;
	private int y;
	private int width;
	private int height;
	private JCheckBox checkBox;
	private JLabel nameLabel;
	private JScrollPane scroll;
	private JScrollPane scrollSelectedEmployee;
	private DefaultListModel listModel;
	
	public Sidepanel() {
		countEmployee = 10;
		x = 5;
		y = 5;
		width = 30;
		height = 30;
		
		listModel = new DefaultListModel();
		message = new JButton("Meldinger");
		newAppointment = new JButton("Ny Avtale");
		newMeeting = new JButton("Nytt møte");
		myAppointments = new JButton("Mine Avtaler");
		employeesAppointments = new JButton("Ansattes Avtaler");
		logOut = new JButton("Logg Ut");
		addEmployee = new JButton("Legg Til");
		guiConstants = new GuiConstants();
		employeeList = new JPanel();
		employeeList.setBackground(Color.WHITE);
		employeeList.setLayout(null);
		selectedEmployeeList = new JList();
		selectedEmployeeList.setCellRenderer(new EmployeeListCellRenderer());
		search = new JTextField();
		
		scroll = new JScrollPane(employeeList);
		
		selectedEmployeeList.setModel(listModel);
		scrollSelectedEmployee = new JScrollPane(selectedEmployeeList);
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		listModel.addElement("");
		
		
		setLayout(null);
		//employeeList.setLayout(null);
		//scroll.setLayout(null);
		resize();

		
		add(message);
		add(newAppointment);
		add(newMeeting);
		add(myAppointments);
		add(employeesAppointments);
		add(logOut);
		add(addEmployee);
		//add(employeeList);
		//add(selectedEmployeeList);
		add(search);
		add(scroll);
		add(scrollSelectedEmployee);
	}
	
	public void resize(){
		message.setBounds(guiConstants.getDistance(), guiConstants.getDistance(), 160, 35);
		message.setFont(guiConstants.getJButtonFont());
		
		newAppointment.setBounds(guiConstants.getDistance(), message.getY() + message.getHeight()+
				guiConstants.getGroupDistance(), 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(guiConstants.getJButtonFont());
		
		
		newMeeting.setBounds(guiConstants.getDistance(), newAppointment.getY() + guiConstants.getDistance()
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(guiConstants.getJButtonFont());

		
		myAppointments.setBounds(guiConstants.getDistance(), newMeeting.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(guiConstants.getJButtonFont());
		
		
		employeesAppointments.setBounds(guiConstants.getDistance(), myAppointments.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(guiConstants.getJButtonFont());
		
		
		search.setBounds(guiConstants.getDistance(), employeesAppointments.getY() + guiConstants.getGroupDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		//legge til scrolling
		
		scroll.setBounds(guiConstants.getDistance(), search.getY() + 2 + message.getHeight(), message.getWidth(), message.getHeight()*4);
		
		
		for (int i = 0; i < countEmployee; i++) {
			checkBox = new JCheckBox();
			nameLabel = new JLabel();
			nameLabel.setText("hei");
			checkBox.setBounds(x, y, width, height);
			nameLabel.setBounds(x+width, y, width*2, height);
			employeeList.add(checkBox);
			employeeList.add(nameLabel);
			y+=22;
			employeeList.setSize(employeeList.getWidth(), y);
		}
		employeeList.setBounds(guiConstants.getDistance(), search.getY() + 2 + message.getHeight(), message.getWidth(), employeeList.getHeight());
		employeeList.setPreferredSize(employeeList.getSize());
				
		addEmployee.setBounds(guiConstants.getDistance(), scroll.getY() + guiConstants.getDistance() + scroll.getHeight(), message.getWidth()/2, message.getHeight());
		
		scrollSelectedEmployee.setBounds(guiConstants.getDistance(), addEmployee.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight()*4);
//		selectedEmployeeList.setBounds(guiConstants.getDistance(), addEmployee.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), selectedEmployeeList.getHeight());
//		selectedEmployeeList.setPreferredSize(employeeList.getSize());
		
		logOut.setBounds(guiConstants.getDistance(), scrollSelectedEmployee.getY() + scrollSelectedEmployee.getHeight() + guiConstants.getDistance(), message.getWidth(), message.getHeight());
		logOut.setFont(guiConstants.getJButtonFont());
	}
	
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		Sidepanel s = new Sidepanel();
		test.add(s);
		
		test.getContentPane();
		test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		test.pack();
		test.setSize(700, 700);
		test.setVisible(true);
	}



	@Override
	public void focusGained(FocusEvent arg0) {
		if (search.getText().equals("Søk")) {
			search.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (!search.getText().equals("Søk")) {
			search.setText("Søk");
		}
		
	}
}