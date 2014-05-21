import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class OptionsFrame extends JPanel implements ActionListener{

	private JPanel optionsPanel;
	
	private boolean tutee = false;
	private String fname = "";
	private String lname = "";
	private String email = "";
	private int subjectnum;
	private String subj = "";
	
	private JButton editButton;
	private JButton reportButton;
	private JButton viewTutorsButton;
	private JButton logButton;
	private JButton logoutButton;
	
	private JComboBox tuteeComboBox;
	
	private String[] tuteeList = {"a","b"};
	
	public OptionsFrame(String s, String f, String l, String e, boolean t) {
		subj = s;
		tutee = t;
		fname = f;
		lname = l;
		email = e;
		editButton = new JButton("Edit Your Information");
		editButton.addActionListener(this);
		
		//Session Report Panel
		JPanel sessionReportPanel = new JPanel(new GridLayout(1,2));
		reportButton = new JButton("Submit Session Report for -->");
		reportButton.addActionListener(this);
		tuteeComboBox = new JComboBox(tuteeList);
		sessionReportPanel.add(reportButton);
		sessionReportPanel.add(tuteeComboBox);
		
		viewTutorsButton = new JButton("View Tutors");
		viewTutorsButton.addActionListener(this);
		logButton = new JButton("View Session Log");
		logButton.addActionListener(this);
		logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(this);
		
		if (tutee) {
			optionsPanel = new JPanel(new GridLayout(4,1));
			optionsPanel.add(new JLabel("Welcome Tutee "+fname+" "+lname, SwingConstants.CENTER));
			optionsPanel.add(editButton);
			optionsPanel.add(viewTutorsButton);
			optionsPanel.add(logoutButton);
		} else {
			optionsPanel = new JPanel(new GridLayout(5,1));
			optionsPanel.add(new JLabel("Welcome Tutor "+fname+" "+lname, SwingConstants.CENTER));
			optionsPanel.add(editButton);
			optionsPanel.add(sessionReportPanel);
			optionsPanel.add(logButton);
			optionsPanel.add(logoutButton);
		}
		
		//get all properties and assign to fields
		BufferedReader br = null;
		try {
			
			String currentLine = "";
			
			if (tutee) {
				br = new BufferedReader(new FileReader("tuteelist.csv"));
			} else {
				br = new BufferedReader(new FileReader("tutorlist.csv"));
			}

			String infoLine[] = new String[0];
			
			while (currentLine != null) {
				currentLine = br.readLine();
				if (currentLine.contains(fname+","+lname+","+email)) {
					infoLine = currentLine.split(",");
					break;
				}
			}
			
			subjectnum = Integer.valueOf(infoLine[3]);
			
		} catch (Exception ex) {
		}
		
		this.add(optionsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		MatcherMain parent = (MatcherMain) SwingUtilities.getWindowAncestor(this).
				getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(editButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setSignupFrame(tutee);
			MatcherMain.setEditing(fname, lname, email);
			parent.setContentPane(MatcherMain.signupframe);
		} else if (event.getSource().equals(reportButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setSessionReportFrame(this.fname+" "+this.lname, tuteeList[tuteeComboBox.getSelectedIndex()]);
			parent.setContentPane(MatcherMain.sessionreportframe);
		} else if (event.getSource().equals(viewTutorsButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setTutorTableFrame(subjectnum,email,fname,lname,subj);
			parent.setContentPane(MatcherMain.tutortableframe);
		} else if (event.getSource().equals(logButton)) {
			
		} else if (event.getSource().equals(logoutButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setMainFrame();
			parent.setContentPane(MatcherMain.mainframe);
		}
		
	}
	
}
