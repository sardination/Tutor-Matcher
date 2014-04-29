import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SessionReportFrame extends JPanel implements ActionListener {

	private String tutor;
	private String tutee;
	private JPanel ReportScreen;
	
	public SessionReportFrame() {
		ReportScreen = new JPanel();
		ReportScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		int y = 0;
		
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		c.gridwidth = 2;
		Insets oldInsets = c.insets;
		c.insets = new Insets(10, 0, 24, 0);
		ReportScreen.add(new JLabel("Create Session Report",SwingConstants.CENTER));
		
		c.insets = oldInsets;
		c.gridx = 0;
		c.gridy = y;
		y += 1;
		ReportScreen.add(new JLabel("Tutor: " + tutor + "\t Tutee: " + tutee, SwingConstants.CENTER), c);
		
		
		this.add(ReportScreen);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
