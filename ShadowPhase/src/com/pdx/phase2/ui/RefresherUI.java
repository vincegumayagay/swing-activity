package com.pdx.phase2.ui;

import java.awt.Color;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.pdx.phase2.model.Person;

public class RefresherUI {
	
	JFrame mainFrame;
	
	JLabel lastNameLabel, firstNameLabel, genderLabel, birthdateLabel;
	
	JTextField lastNameTextField, firstNameTextField;
	JButton addButton, saveButton, deleteButton;

	String[] genders = {"Male", "Female", "Others"};
	JComboBox<String> genderComboBox;
	
	UtilDateModel dateModel;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	String[] columnNames = {"ID", "Lastname", "Firstname", "Gender", "Birthdate"};
	DefaultTableModel tableModel;
	JTable table;
	
	List<Person> personList = new LinkedList<>();
	
	public RefresherUI() {
		mainFrame = new JFrame("Refresher Activity");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(null);
		
		//Generate Components
		createLabels();
		createTextFields();
		createComboBox();
		createDatePicker();
		createButtons();
		createTable();
		
		addActionListenerToButtons();
		
		mainFrame.setVisible(true);
	}
	
	public void createLabels() {
		lastNameLabel = new JLabel("Lastname:");
		lastNameLabel.setBounds(20, 50, 100, 25);
		mainFrame.add(lastNameLabel);
		
		firstNameLabel = new JLabel("Firstname:");
		firstNameLabel.setBounds(20, 100, 100, 25);
		mainFrame.add(firstNameLabel);
		
		genderLabel = new JLabel("Gender:");
		genderLabel.setBounds(20, 150, 100, 25);
		mainFrame.add(genderLabel);
		
		birthdateLabel = new JLabel("Birthdate:");
		birthdateLabel.setBounds(20, 200, 100, 25);
		mainFrame.add(birthdateLabel);
		
	}
	
	public void createTextFields() {
		lastNameTextField = new JTextField();
		lastNameTextField.setBounds(100, 50, 200, 30);
		lastNameTextField.setEditable(false);
		mainFrame.add(lastNameTextField);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(100, 100, 200, 30);
		firstNameTextField.setEditable(false);
		mainFrame.add(firstNameTextField);
		
	}

	public void createComboBox() {
		genderComboBox = new JComboBox<String>(genders);
		genderComboBox.setBounds(100, 150, 200, 30);
		genderComboBox.setEnabled(false);
		genderComboBox.setVisible(true);
		
		mainFrame.add(genderComboBox);
	}
	
	public void createDatePicker() {
		dateModel = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		
		
		datePanel = new JDatePanelImpl(dateModel, properties);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		
		datePicker.setBounds(100, 200, 200, 30);
		datePicker.getComponent(1).setEnabled(false);
		
		mainFrame.add(datePicker);
	}
	
	public void createButtons() {
		addButton = new JButton("Add New");
		addButton.setBackground(new Color(103, 173, 28));
		addButton.setForeground(Color.WHITE);
		addButton.setBounds(20, 250, 100, 30);
		mainFrame.add(addButton);
		
		saveButton = new JButton("Save");
		saveButton.setBackground(new Color(58, 119, 224));
		saveButton.setForeground(Color.WHITE);
		saveButton.setBounds(125, 250, 100, 30);
		saveButton.setEnabled(false);
		mainFrame.add(saveButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBackground(new Color(217, 47, 41));
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setBounds(230, 250, 100, 30);
		deleteButton.setEnabled(false);
		mainFrame.add(deleteButton);
		
	}
	
	public void createTable() {
		
		table = new JTable();
		tableModel = new DefaultTableModel();	
		tableModel.setColumnIdentifiers(columnNames);
		
		table.setModel(tableModel);
		table.getTableHeader().setBackground(new Color(108, 150, 217));
		table.getTableHeader().setReorderingAllowed(false);
		table.getSelectionModel().addListSelectionListener(e -> {
			deleteButton.setEnabled(true);
		});
	
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setBounds(20, 300, 745, 200);	
		
		mainFrame.add(tablePane);
		
	}
	
	
	public void addActionListenerToButtons() {
		//Add Button
		addButton.addActionListener(e -> {
			addButton.setEnabled(false);
			saveButton.setEnabled(true);
			
			lastNameTextField.setEditable(true);
			firstNameTextField.setEditable(true);
			
			genderComboBox.setEnabled(true);
			
			datePicker.getComponent(1).setEnabled(true);
			
		});
		
		//Save Button
		saveButton.addActionListener(e -> {
			if(lastNameTextField.getText().equals("") || firstNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(mainFrame, "Fields must not be empty");
			} else {
				
				LocalDate date = LocalDate.of(
						datePicker.getModel().getYear(), 
						datePicker.getModel().getMonth(), 
						datePicker.getModel().getDay());
				
				Person newPerson = new Person(
						lastNameTextField.getText(), 
						firstNameTextField.getText(), 
						genderComboBox.getSelectedItem().toString(), 
						date);
				
				Object[] data = {
						newPerson.getId(), 
						newPerson.getLastName(), 
						newPerson.getFirstName(), 
						newPerson.getGender(), 
						newPerson.getBirthdate()
						};
				
				tableModel.addRow(data);
				
				resetForm();
				
			}
			
		});
		
		//Delete Button
		deleteButton.addActionListener(e -> {
			if(table.getSelectedRowCount() == 1) {
				tableModel.removeRow(table.getSelectedRow());
				deleteButton.setEnabled(false);
			}
		});
		
	}
	
	public void resetForm() {
		addButton.setEnabled(true);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		lastNameTextField.setEditable(false);
		lastNameTextField.setText(null);
		firstNameTextField.setEditable(false);
		firstNameTextField.setText(null);
		
		genderComboBox.setEnabled(false);
		genderComboBox.setSelectedIndex(0);
		
		datePicker.getComponent(1).setEnabled(false);
		datePicker.getModel().setValue(null);
	}
	
	public static void main(String[] args) {
		new RefresherUI();
	}

}
