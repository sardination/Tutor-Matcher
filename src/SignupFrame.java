import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SignupFrame extends JPanel implements ActionListener {

	public boolean completedRegistration = false;
	
	private JPanel SignupScreen;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField emailField;
	private JComboBox subjectComboBox;
	private JTable availableTutorsTable;
	private boolean tutee = false;
	private JTextArea datesAvailableField;
	private JTextArea notesField;
	private JButton saveButton;
	private JButton cancelButton;
	private int subjectNum = 0; //if tutee, this is the index of their class in the array
								//if tutor, this is the index of the highest level class they can teach
	
	private String[] subjectList = {"Algebra 1", "Geometry", "Honors Geometry", "Bridge to Algebra 2", 
			"Algebra 2", "Honors Algebra 2", "Pre-Calculus", "Honors Pre-Calculus",
			"Calculus with Applications", "Statistics and Mathematical Modeling",
			"Quantitative Literacy", "College Test Prep", "AP Calculus AB", "AP Calculus BC",
			"AP Statistics", "Magnet Functions", "Magnet Pre-Calculus AB", "Magnet Pre-Calculus CD",
			"Magnet Analysis", "Multi-Variable Calculus", "Vector Calculus",
			"Linear Algebra", "Applied Statistics"};
	
	public SignupFrame(boolean tute) {
		
		tutee = tute;
		
		SignupScreen = new JPanel();
		SignupScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		fnameField = new JTextField(30);
		lnameField = new JTextField(30);
		emailField = new JTextField(30);
		subjectComboBox = new JComboBox(subjectList);
		subjectComboBox.addActionListener(this);
		availableTutorsTable = new JTable();
		datesAvailableField = new JTextArea(6,30);
		datesAvailableField.setLineWrap(true);
		datesAvailableField.setWrapStyleWord(true);
		notesField = new JTextArea(6,30);
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		int y = 0;
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		c.gridwidth = 2;
		Insets oldInsets = c.insets;
		c.insets = new Insets(10, 0, 24, 0);
		
		if (tutee) {
			SignupScreen.add(new JLabel("Tutee Signup", SwingConstants.CENTER), c);
		} else {
			SignupScreen.add(new JLabel("Tutor Signup", SwingConstants.CENTER), c);
		}
		
		c.insets = oldInsets;
		
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		SignupScreen.add(new JLabel("First Name: "), c);
		
		c.gridx = 1;
		SignupScreen.add(fnameField, c);
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		SignupScreen.add(new JLabel("Last Name: "), c);
		
		c.gridx = 1;
		SignupScreen.add(lnameField, c);
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		SignupScreen.add(new JLabel("Email: "), c);
		
		c.gridx = 1;
		SignupScreen.add(emailField, c);
		
		if (tutee) {

			c.gridx = 0;
			c.gridy = y;
			c.gridheight = 3;
			y += 3;
			SignupScreen.add(new JLabel("<html>Subject - Be sure that the class<br /> you select matches your"
					+ "<br />current math class name EXACTLY: </html>"), c);
			
			c.gridx = 1;
			c.gridheight = 1;
			SignupScreen.add(subjectComboBox, c);
			
			c.gridx = 0;
			c.gridy = y;
			y += 1;
			c.gridwidth = 2;
			SignupScreen.add(new JLabel("Tutors Available: "), c);
		} else {
			c.gridx = 0;
			c.gridy = y;
			c.gridheight = 2;
			y += 2;
			SignupScreen.add(new JLabel("<html>Subject - select the highest<br /> level class you can "
					+ "teach: </html>"), c);
			
			c.gridx = 1;
			c.gridheight = 1;
			SignupScreen.add(subjectComboBox, c);
			
			//dates available
			
			c.gridx = 0;
			c.gridy = y;
			c.gridheight = 1;
			SignupScreen.add(new JLabel("Dates Available: "), c);
			
			c.gridx = 1;
			c.gridheight = 6;
			y += 6;
			SignupScreen.add(datesAvailableField, c);
			
			c.gridx = 0;
			c.gridy = y;
			c.gridheight = 1;
			SignupScreen.add(new JLabel("Additional Notes: "), c);
			
			c.gridx = 1;
			c.gridheight = 7;
			y += 7;
			SignupScreen.add(notesField, c);
		}
		
		c.gridx = 0;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(50,0,15,0);
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1,2));
		savePanel.add(saveButton);
		savePanel.add(cancelButton);
		SignupScreen.add(savePanel,c);
		
		this.add(SignupScreen);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(subjectComboBox)) {
			subjectNum = subjectComboBox.getSelectedIndex();
		} else if (event.getSource().equals(saveButton)) {
			Writer output;
			try {
				File file = new File("tutorlist.csv");
				String writeInfo = "";
				if (tutee) {
					file = new File("tuteelist.csv");
					writeInfo = fnameField.getText()+","+lnameField.getText()+","
							+emailField.getText()+","+subjectNum+","+subjectList[subjectNum]+"\n";
				} else {
					writeInfo = fnameField.getText()+","+lnameField.getText()+","
							+emailField.getText()+","+subjectNum+","+subjectList[subjectNum]
							+datesAvailableField.getText()+","+notesField.getText()+","+"\n";
				}
				
				output = new BufferedWriter(new FileWriter(file.getName(), true));
				output.write(writeInfo);
				output.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(SignupScreen, "Sorry, Something Went Wrong.");
			}
		}
	}

}
