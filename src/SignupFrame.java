import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SignupFrame extends JPanel implements ActionListener {

	public boolean completedRegistration = false;
	
	private JPanel SignupScreen;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField emailField;
	private JComboBox subjectComboBox;
	private boolean tutee = false;
	private JTextArea datesAvailableField;
	private JTextArea notesField;
	private JButton saveButton;
	private JButton cancelButton;
	private int subjectNum = 0; //if tutee, this is the index of their class in the array
								//if tutor, this is the index of the highest level class they can teach
	private boolean editing = false;
	private String[] existingInfo;
	
	private String[] subjectList = {"Algebra 1", "Geometry", "Honors Geometry", "Bridge to Algebra 2", 
			"Algebra 2", "Honors Algebra 2", "Pre-Calculus", "Honors Pre-Calculus",
			"Calculus with Applications", "Statistics and Mathematical Modeling",
			"Quantitative Literacy", "College Test Prep", "AP Calculus AB", "AP Calculus BC",
			"AP Statistics", "Magnet Functions", "Magnet Pre-Calculus AB", "Magnet Pre-Calculus CD",
			"Magnet Analysis", "Multi-Variable Calculus", "Vector Calculus",
			"Linear Algebra", "Applied Statistics"};
	
	public SignupFrame(boolean t) {
		
		tutee = t;
		
		SignupScreen = new JPanel();
		SignupScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		fnameField = new JTextField(25);
		lnameField = new JTextField(25);
		emailField = new JTextField(25);
		subjectComboBox = new JComboBox(subjectList);
		subjectComboBox.addActionListener(this);
		datesAvailableField = new JTextArea(2,25);
		datesAvailableField.setLineWrap(true);
		datesAvailableField.setWrapStyleWord(true);
		notesField = new JTextArea(2,25);
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
			SignupScreen.add(new JLabel("<html>Subject - Be sure that the<br />class you select matches"
					+ "<br />your current math<br />class name EXACTLY: </html>"), c);
			
			c.gridx = 1;
			c.gridheight = 1;
			SignupScreen.add(subjectComboBox, c);
			
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
			c.gridheight = 3;
			y += 3;
			JScrollPane scrollPane1 = new JScrollPane(datesAvailableField);
			SignupScreen.add(scrollPane1, c);
			
			c.gridx = 0;
			c.gridy = y;
			c.gridheight = 1;
			SignupScreen.add(new JLabel("Additional Notes: "), c);
			
			c.gridx = 1;
			c.gridheight = 3;
			y += 3;
			JScrollPane scrollPane2 = new JScrollPane(notesField);
			SignupScreen.add(scrollPane2, c);
		}
		
		c.gridx = 0;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(20,0,15,0);
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1,2));
		savePanel.add(saveButton);
		savePanel.add(cancelButton);
		SignupScreen.add(savePanel,c);
		
		this.add(SignupScreen);
	}
	
	
	public void setEditing(String f, String l, String em) {
		editing = true;
		
		BufferedReader br = null;
		
		try {
			if (tutee) {
				br = new BufferedReader(new FileReader("tuteelist.csv"));
				String currentLine = br.readLine();
				
				while (currentLine != null) {
					if (currentLine.contains(f+","+l+","+em)) {
						existingInfo = currentLine.split(",");
						break;
					}
					currentLine = br.readLine();
				}
			} else {
				br = new BufferedReader(new FileReader("tutorlist.csv"));
				String currentLine = br.readLine();
				while (currentLine != null) {
					if (currentLine.contains(f+","+l+","+em)) {
						existingInfo = currentLine.split(",");
						break;
					}
					currentLine = br.readLine();
				}
				datesAvailableField.setText(existingInfo[5]);
				notesField.setText(existingInfo[6]);
			}
			
			fnameField.setText(existingInfo[0]);
			lnameField.setText(existingInfo[1]);
			emailField.setText(existingInfo[2]);
			subjectComboBox.setSelectedIndex(Integer.parseInt(existingInfo[3]));
		} catch (Exception e) {
		}
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		MatcherMain parent = (MatcherMain) SwingUtilities.getWindowAncestor(this).
				getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(subjectComboBox)) {
			subjectNum = subjectComboBox.getSelectedIndex();
		} else if (event.getSource().equals(saveButton)) {
			boolean alreadyExists = false;
			BufferedReader br = null;
			try {
				
				String currentLine = "";
				
				if (tutee) {
					br = new BufferedReader(new FileReader("tuteelist.csv"));
				} else {
					br = new BufferedReader(new FileReader("tutorlist.csv"));
				}

				
				while (currentLine != null) {
					currentLine = br.readLine();
					if (currentLine.contains(fnameField.getText()+","+lnameField.getText()+","+emailField.getText())) {
						alreadyExists = true;
					}
					
					if (alreadyExists) break;
					currentLine = br.readLine();
				}
				
			} catch (Exception e) {
			}
			
			if (fnameField.getText().equals("") || lnameField.getText().equals("") || emailField.getText().equals("")) {
				JOptionPane.showMessageDialog(SignupScreen,"Please enter information in all of the fields.");
			} else if (alreadyExists){
				JOptionPane.showMessageDialog(SignupScreen,"This user already exists.");
			} else {
				Writer output;
				try {
					File file = new File("tutorlist.csv");
					String writeInfo = "";
					if (tutee) {
						file = new File("tuteelist.csv");
						String f = fnameField.getText().replace("\n","    ");
						String l = lnameField.getText().replace("\n","    ");
						String e = emailField.getText().replace("\n","    ");
						writeInfo = f+","+l+","+e+","+subjectNum+","+subjectList[subjectNum]+"\n";
					} else {
						String f = fnameField.getText().replace("\n","    ");
						String l = lnameField.getText().replace("\n","    ");
						String e = emailField.getText().replace("\n","    ");
						String d = datesAvailableField.getText().replace("\n","    ");
						String n = datesAvailableField.getText().replace("\n","    ");
						writeInfo = f+","+l+","+e+","+subjectNum+","+subjectList[subjectNum]+","+d+","+n+","+"[]"+"\n";
					}
					
					output = new BufferedWriter(new FileWriter(file.getName(), true));
					output.write(writeInfo);
					output.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(SignupScreen, "Sorry, something went wrong!");
				}
				
				this.setVisible(false);
				this.getParent().remove(this);
				MatcherMain.setOptionsFrame(subjectList[subjectNum], fnameField.getText(), lnameField.getText(), emailField.getText(), tutee);
				parent.setContentPane(MatcherMain.optionsframe);
			}	
		} else if (event.getSource().equals(cancelButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setMainFrame();
			parent.setContentPane(MatcherMain.mainframe);
		}
	}

}
