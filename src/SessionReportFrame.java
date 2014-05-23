import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class SessionReportFrame extends JPanel implements ActionListener {

	private String tutor;
	private String tutee;
	private JPanel ReportScreen;
	private JTextField minutesField;
	private JTextField supervisorField;
	private JTextArea notesField;
	private JButton saveButton;
	private JButton cancelButton;
	private JDateChooser jDateChooser;
	
	// passing info
	private String subject;
	private String email;
	
	public SessionReportFrame(String tuto, String tute, String email, String subject) {
		this.email = email;
		this.subject = subject;
		
		//***** jDatePicker code from http://toedter.com/jcalendar/
		jDateChooser = new com.toedter.calendar.JDateChooser();
		jDateChooser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jDateChooser.setDateFormatString("dd/MM/yyyy");
		
		//*******
		
		this.tutor = tuto;
		this.tutee = tute;
		ReportScreen = new JPanel();
		ReportScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);

		minutesField = new JTextField(3);
		JPanel minutesFieldWrapper = new JPanel(new FlowLayout(0, 0,
				FlowLayout.LEADING));
		minutesFieldWrapper.add(minutesField);
		supervisorField = new JTextField(3);
		notesField = new JTextArea(6, 30);
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
		c.insets = new Insets(10, 0, 15, 0);
		ReportScreen.add(new JLabel("Create Session Report",
				SwingConstants.CENTER), c);

		c.gridx = 0;
		c.gridy = y;
		y += 1;
		ReportScreen.add(new JLabel("Tutor: " + tutor + " " + " Tutee: "
				+ tutee, SwingConstants.CENTER), c);

		c.insets = oldInsets;
		c.gridy = y;
		y += 1;
		c.gridwidth = 1;
		ReportScreen.add(new JLabel("Date: "), c);

		c.gridx = 1;
		ReportScreen.add(jDateChooser, c);

		c.gridx = 0;
		c.gridy = y;
		y += 1;
		ReportScreen.add(new JLabel("Time Spent: (Minutes)"), c);

		c.gridx = 1;
		ReportScreen.add(minutesFieldWrapper, c);

		c.gridx = 0;
		c.gridy = y;
		y += 1;
		ReportScreen.add(new JLabel("Supervisor Email Address: "), c);

		c.gridx = 1;
		ReportScreen.add(supervisorField, c);

		c.gridx = 0;
		c.gridy = y;
		ReportScreen.add(new JLabel("Additional Notes: "), c);

		c.gridx = 1;
		c.gridheight = 6;
		y += 6;
		ReportScreen.add(notesField, c);

		c.gridx = 0;
		c.gridy = y;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.insets = new Insets(30, 0, 15, 0);
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1, 2));
		savePanel.add(saveButton);
		savePanel.add(cancelButton);
		ReportScreen.add(savePanel, c);

		this.add(ReportScreen);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		MatcherMain parent = (MatcherMain) SwingUtilities
				.getWindowAncestor(this).getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(saveButton)) {
			Writer output;
			try {
				File file = new File(tutor + ".csv");
				String writeInfo = tutee + ","
						+ jDateChooser.getDate().toString() + ","
						+ minutesField.getText() + ","
						+ supervisorField.getText() + ","
						+ notesField.getText() + "\n";
				output = new BufferedWriter(
						new FileWriter(file.getName(), true));
				output.write(writeInfo);
				output.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(ReportScreen,
						"Sorry, something went wrong!");
			}
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setMainFrame();
			parent.setContentPane(MatcherMain.mainframe);
		} else if (event.getSource().equals(cancelButton)) {
			MatcherMain.setOptionsFrame(subject, tutor.split(" ")[0], tutor.split(" ")[1], email, true);
			this.setVisible(false);
			parent.remove(this);
			parent.setContentPane(MatcherMain.optionsframe);
		}

	}

}
