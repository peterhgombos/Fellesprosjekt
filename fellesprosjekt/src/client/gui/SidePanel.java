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
public class SidePanel extends JPanel implements FocusListener{
	
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
	private CalendarPanel calendarpanel;
	
	public SidePanel(CalendarPanel calendarPanel) {
		countEmployee = 10;
		x = 5;
		y = 5;
		width = 30;
		height = 30;
		calendarpanel = calendarPanel;
		
		listModel = new DefaultListModel();
		message = new JButton("Meldinger");
		message.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToMessages();
				
			}
		});
		newAppointment = new JButton("Ny Avtale");
		newAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewAppointment();
			}
		});
		newMeeting = new JButton("Nytt møte");
		newMeeting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewMeeting();
			}
		});
		myAppointments = new JButton("Mine Avtaler");
		myAppointments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToMyAppointments();
			}
		});
		employeesAppointments = new JButton("Ansattes Avtaler");
		employeesAppointments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// calendarPanel
				
			}
		});
		logOut = new JButton("Logg Ut");
		logOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// calendarPanel
				
			}
		});
		addEmployee = new JButton("Legg Til");
		addEmployee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
		resize();

		add(message);
		add(newAppointment);
		add(newMeeting);
		add(myAppointments);
		add(logOut);
		add(addEmployee);
		add(search);
		add(scroll);
		add(scrollSelectedEmployee);
	}
	
	public void resize(){
		message.setBounds(GuiConstants.DISTANCE, GuiConstants.DISTANCE, 160, 35);
		message.setFont(GuiConstants.BUTTON_FONT);
		
		newAppointment.setBounds(GuiConstants.DISTANCE, message.getY() + message.getHeight()+
				GuiConstants.GROUP_DISTANCE+5, 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(GuiConstants.BUTTON_FONT);
		
		
		newMeeting.setBounds(GuiConstants.DISTANCE, newAppointment.getY() + GuiConstants.DISTANCE
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(GuiConstants.BUTTON_FONT);

		
		myAppointments.setBounds(GuiConstants.DISTANCE, newMeeting.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		employeesAppointments.setBounds(GuiConstants.DISTANCE, myAppointments.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		search.setBounds(GuiConstants.DISTANCE, employeesAppointments.getY() + GuiConstants.GROUP_DISTANCE-30 + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		//legge til scrolling
		scroll.setBounds(GuiConstants.DISTANCE, search.getY() +2 + message.getHeight(), message.getWidth(), message.getHeight()*5);
		
		for (int i = 0; i < countEmployee; i++) {
			checkBox = new JCheckBox();
			nameLabel = new JLabel();
			nameLabel.setText("hei");
			checkBox.setBounds(x, y, width-6, height);
			checkBox.setOpaque(false);
			nameLabel.setBounds(x+width, y, width, height);
			employeeList.add(checkBox);
			employeeList.add(nameLabel);
			y+=22;
			employeeList.setSize(employeeList.getWidth(), y);
		}
		employeeList.setBounds(GuiConstants.DISTANCE, search.getY() + 2 + message.getHeight(), message.getWidth()-20, employeeList.getHeight());
		employeeList.setPreferredSize(employeeList.getSize());
				
		addEmployee.setBounds(GuiConstants.DISTANCE, scroll.getY() + GuiConstants.DISTANCE + scroll.getHeight(), message.getWidth()/2, message.getHeight());
		
		scrollSelectedEmployee.setBounds(GuiConstants.DISTANCE, addEmployee.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight()*4);

		
		logOut.setBounds(GuiConstants.DISTANCE, scrollSelectedEmployee.getY() + scrollSelectedEmployee.getHeight() + GuiConstants.DISTANCE, message.getWidth(), message.getHeight());
		logOut.setFont(GuiConstants.BUTTON_FONT);
	}
	
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		SidePanel s = new SidePanel(null);
		test.add(s);
		
		test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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