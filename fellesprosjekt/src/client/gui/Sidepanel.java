package client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Sidepanel extends JPanel implements FocusListener, ActionListener{
	
	private JButton message;
	private JButton newAppointment;
	private JButton newMeeting;
	private JButton myAppointments;
	private JButton employeesAppointments;
	private JButton logOut;
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
		message.setBounds(GuiConstants.DISTANCE, GuiConstants.DISTANCE, 160, 35);
		message.setFont(GuiConstants.BUTTON_FONT);
		
		newAppointment.setBounds(GuiConstants.DISTANCE, message.getY() + message.getHeight()+
				GuiConstants.GROUP_DISTANCE, 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(GuiConstants.BUTTON_FONT);
		
		
		newMeeting.setBounds(GuiConstants.DISTANCE, newAppointment.getY() + GuiConstants.DISTANCE
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(GuiConstants.BUTTON_FONT);

		
		myAppointments.setBounds(GuiConstants.DISTANCE, newMeeting.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		employeesAppointments.setBounds(GuiConstants.DISTANCE, myAppointments.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		search.setBounds(GuiConstants.DISTANCE, employeesAppointments.getY() + GuiConstants.GROUP_DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		//legge til scrolling
		
		scroll.setBounds(GuiConstants.DISTANCE, search.getY() + 2 + message.getHeight(), message.getWidth(), message.getHeight()*4);
		
		
		for (int i = 0; i < countEmployee; i++) {
			checkBox = new JCheckBox();
			nameLabel = new JLabel();
			nameLabel.setText("hei");
			checkBox.setBounds(x, y, width, height);
			checkBox.setOpaque(false);
			nameLabel.setBounds(x+width, y, width*2, height);
			employeeList.add(checkBox);
			employeeList.add(nameLabel);
			y+=22;
			employeeList.setSize(employeeList.getWidth(), y);
		}
		employeeList.setBounds(GuiConstants.DISTANCE, search.getY() + 2 + message.getHeight(), message.getWidth(), employeeList.getHeight());
		employeeList.setPreferredSize(employeeList.getSize());
				
		addEmployee.setBounds(GuiConstants.DISTANCE, scroll.getY() + GuiConstants.DISTANCE + scroll.getHeight(), message.getWidth()/2, message.getHeight());
		
		scrollSelectedEmployee.setBounds(GuiConstants.DISTANCE, addEmployee.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight()*4);
//		selectedEmployeeList.setBounds(guiConstants.DISTANCE, addEmployee.getY() + guiConstants.DISTANCE + message.getHeight(), message.getWidth(), selectedEmployeeList.getHeight());
//		selectedEmployeeList.setPreferredSize(employeeList.getSize());
		
		logOut.setBounds(GuiConstants.DISTANCE, scrollSelectedEmployee.getY() + scrollSelectedEmployee.getHeight() + GuiConstants.DISTANCE, message.getWidth(), message.getHeight());
		logOut.setFont(GuiConstants.BUTTON_FONT);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}