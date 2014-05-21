import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class MainFrame extends JPanel implements ActionListener{
	
	private JPanel mainScreen;
	private JPanel signinPanel;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField emailField;
	private JButton signinButton;
	private JPanel registerPanel;
	private JButton registerButton;
	private JRadioButton tutorChoice;
	private JRadioButton tuteeChoice;
	
	public MainFrame() {
		mainScreen = new JPanel(new BorderLayout(0,10));
		signinPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		registerPanel = new JPanel(new GridLayout(1,2));
		fnameField = new JTextField(10);
		lnameField = new JTextField(10);
		emailField = new JTextField(10);
		signinButton = new JButton("Sign In");
		signinButton.addActionListener(this);
		registerButton = new JButton("Register Now");
		registerButton.addActionListener(this);
		
		tutorChoice = new JRadioButton("Tutor");
		tutorChoice.setSelected(true);
		tuteeChoice = new JRadioButton("Tutee");

		ButtonGroup bg = new ButtonGroup();
		bg.add(tutorChoice);
		bg.add(tuteeChoice);
		
		int y = 0;
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		signinPanel.add(new JLabel("Are you a tutor or a tutee?: "), c);
		
		JPanel radioPanel = new JPanel(new GridLayout(1,2));
		radioPanel.add(tutorChoice);
		radioPanel.add(tuteeChoice);
		
		c.gridx = 1;
		c.gridheight = 1;
		signinPanel.add(radioPanel, c);

		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		signinPanel.add(new JLabel("First Name: "),c);
		
		c.gridx = 1;
		signinPanel.add(fnameField,c);
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		signinPanel.add(new JLabel("Last Name: "),c);
		
		c.gridx = 1;
		signinPanel.add(lnameField, c);
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		signinPanel.add(new JLabel("Email Address: "),c);
		
		c.gridx = 1;
		signinPanel.add(emailField,c);
		
		c.gridx = 0;
		c.gridy = y;
		c.gridwidth = 2;
		signinPanel.add(signinButton,c);
		
		registerPanel.add(new JLabel("<html>Aren't registered as a tutor or tutee?<br/>Pick your role above and click<br/>the"
				+ " Register button.     </html>    "));
		registerPanel.add(registerButton);

		
		mainScreen.add(new JLabel("Welcome to the Tutor Matcher! Please log in.    ", 
				SwingConstants.CENTER), BorderLayout.NORTH);
		mainScreen.add(signinPanel, BorderLayout.CENTER);
		mainScreen.add(registerPanel, BorderLayout.SOUTH);
		
		this.add(mainScreen);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		MatcherMain parent = (MatcherMain) SwingUtilities.getWindowAncestor(this).
				getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(registerButton)) {
			if (tutorChoice.isSelected()) {
				MatcherMain.setSignupFrame(false);
			} else if (tuteeChoice.isSelected()) {
				MatcherMain.setSignupFrame(true);
			}
			this.setVisible(false);
			parent.remove(this);
			parent.setContentPane(MatcherMain.signupframe);
		} else if (event.getSource().equals(signinButton)) {
			BufferedReader br = null;
			try {
				
				String currentLine = "";
				
				if (tuteeChoice.isSelected()) {
					br = new BufferedReader(new FileReader("tuteelist.csv"));
				} else {
					br = new BufferedReader(new FileReader("tutorlist.csv"));
				}
				
				boolean found = false;
				
				String subject = "";
				while (currentLine != null) {
					currentLine = br.readLine();
					if (currentLine.contains(fnameField.getText()+","+lnameField.getText()+","+emailField.getText())) {
						found = true;
						subject = currentLine.split(",")[4];
					}
					
					if (found) break;
					currentLine = br.readLine();
				}

				
				if (found) {
					MatcherMain.setOptionsFrame(subject, fnameField.getText(), lnameField.getText()
							, emailField.getText(), tuteeChoice.isSelected());
					this.setVisible(false);
					parent.remove(this);
					parent.setContentPane(MatcherMain.optionsframe);
				} else {
					System.out.println("hi");
					JOptionPane.showMessageDialog(mainScreen,"There is no user that matches these credentials.");
				}

				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(mainScreen,"There is no user that matches these credentials.");
			}

		}
		
	}

}
