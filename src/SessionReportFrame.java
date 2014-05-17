import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
	
	//JDatePicker... hi.jar from: http://jdatepicker.org/ and implementation code from:
	// http://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
	//********
	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	//*********
	
	public SessionReportFrame() {
		ReportScreen = new JPanel();
		ReportScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);

		minutesField = new JTextField(3);
		JPanel minutesFieldWrapper = new JPanel(new FlowLayout(0,0, FlowLayout.LEADING));
		minutesFieldWrapper.add(minutesField);
		supervisorField = new JTextField(3);
		notesField = new JTextArea(6,30);
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		
		int y = 0;
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		c.gridwidth = 2;
		Insets oldInsets = c.insets;
		c.insets = new Insets(10, 0, 15, 0);
		ReportScreen.add(new JLabel("Create Session Report",SwingConstants.CENTER), c);
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		ReportScreen.add(new JLabel("Tutor: " + tutor + " " +
				" Tutee: " + tutee, SwingConstants.CENTER), c);
		
		c.insets = oldInsets;
		c.gridy = y;
		y += 1;
		c.gridwidth = 1;
		ReportScreen.add(new JLabel("Date: "), c);
		
		c.gridx = 1;
		ReportScreen.add(datePicker,c);
		
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
		c.insets = new Insets(30,0,15,0);
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1,2));
		savePanel.add(saveButton);
		savePanel.add(cancelButton);
		ReportScreen.add(savePanel, c);
		
		this.add(ReportScreen);
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(saveButton)) {
			
		}
		
	}

}
