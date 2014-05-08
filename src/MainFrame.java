import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class MainFrame extends JPanel implements ActionListener{
	
	private JPanel mainScreen;
	private JPanel signinPanel;
	private JTextField fnameField;
	private JTextField lnameField;
	private JPasswordField pwField;
	private JButton signinButton;
	private JPanel registerPanel;
	private JButton registerButton;
	
	public MainFrame() {
		mainScreen = new JPanel(new BorderLayout(0,10));
		signinPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		registerPanel = new JPanel(new GridLayout(1,2));
		fnameField = new JTextField(10);
		lnameField = new JTextField(10);
		pwField = new JPasswordField(10);
		signinButton = new JButton("Sign In");
		registerButton = new JButton("Register Now");
		
		int y = 0;
		
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
		signinPanel.add(new JLabel("Password: "),c);
		
		c.gridx = 1;
		signinPanel.add(pwField,c);
		
		c.gridx = 0;
		c.gridy = y;
		c.gridwidth = 2;
		signinPanel.add(signinButton,c);
		
		registerPanel.add(new JLabel("Aren't registered as a tutor or tutee?    "));
		registerPanel.add(registerButton);
		
		mainScreen.add(new JLabel("Welcome to the Tutor Matcher!", 
				SwingConstants.CENTER), BorderLayout.NORTH);
		mainScreen.add(signinPanel, BorderLayout.CENTER);
		mainScreen.add(registerPanel, BorderLayout.SOUTH);
		
		this.add(mainScreen);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(registerButton)) {
			//!!!! have radio buttons for choosing whether to register or sign in as a tutor or a tutee
			
		}
		
	}

}
