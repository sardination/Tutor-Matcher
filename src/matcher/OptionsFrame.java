package matcher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class OptionsFrame extends JPanel implements ActionListener {

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
	private JButton acceptRequestButton;
	private JButton cancelPairingButton;

	String[] reqinfo = new String[0];
	String reqinfoString;

	private JComboBox tuteeComboBox;
	private JComboBox cancelTuteeComboBox;

	private ArrayList<String> tuteeList = new ArrayList<String>();
	//find tutee list

	public OptionsFrame(String s, String f, String l, String e, boolean t) {
		subj = s;
		tutee = t;
		fname = f;
		lname = l;
		email = e;
		editButton = new JButton("Edit Your Information");
		editButton.addActionListener(this);


		viewTutorsButton = new JButton("View Tutors");
		viewTutorsButton.addActionListener(this);
		logButton = new JButton("View Session Log");
		logButton.addActionListener(this);
		logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(this);
		acceptRequestButton = new JButton("ACCEPT A TUTOR REQUEST");
		acceptRequestButton.addActionListener(this);
		cancelPairingButton = new JButton("Cancel a pairing");

		if (tutee) {
			optionsPanel = new JPanel(new GridLayout(4, 1));
			optionsPanel.add(new JLabel("Welcome Tutee " + fname + " " + lname,
					SwingConstants.CENTER));
			optionsPanel.add(editButton);
			optionsPanel.add(viewTutorsButton);
			optionsPanel.add(logoutButton);
		} else {
			// get tutee list
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader("tutorlist.csv"));
				String currentLine = br.readLine();
				while (currentLine != null) {
					if (currentLine.contains(fname + "," + lname + "," + email)) {
						tuteeList = new ArrayList<String>(Arrays.asList(currentLine.split(",")[7].substring(1,
								currentLine.split(",")[7].length() - 1).split(
								"~")));
					}
					break;
				}
			} catch (Exception ex) {
			}
			
			// Session Report Panel
			JPanel sessionReportPanel = new JPanel(new GridLayout(1, 2));
			reportButton = new JButton("Submit Session Report for -->");
			reportButton.addActionListener(this);
			sessionReportPanel.add(reportButton);
			tuteeComboBox = new JComboBox(tuteeList.toArray());
			sessionReportPanel.add(tuteeComboBox);
			cancelPairingButton.addActionListener(this);
			cancelTuteeComboBox = new JComboBox(tuteeList.toArray());
			
			optionsPanel = new JPanel(new GridLayout(7, 1));
			optionsPanel.add(new JLabel("Welcome Tutor " + fname + " " + lname,
					SwingConstants.CENTER));

			boolean alreadyRequested = false;
			br = null;
			try {
				br = new BufferedReader(new FileReader("requests.csv"));
				String currentLine = br.readLine();
				while (currentLine != null) {
					if (currentLine.contains(fname + "," + lname + "," + email)) {
						alreadyRequested = true;
						reqinfoString = currentLine;
						reqinfo = currentLine.split(",");
						break;
					}
					currentLine = br.readLine();
				}
			} catch (Exception ex) {
			}

			if (alreadyRequested) {
				optionsPanel.add(acceptRequestButton);
			}
			
			JPanel cancelPanel = new JPanel(new GridLayout(1,2));
			cancelPanel.add(cancelPairingButton);
			cancelPanel.add(cancelTuteeComboBox);

			optionsPanel.add(editButton);
			tuteeComboBox = new JComboBox(tuteeList.toArray());
			optionsPanel.add(sessionReportPanel);
			optionsPanel.add(logButton);
			optionsPanel.add(cancelPanel);
			optionsPanel.add(logoutButton);
		}

		// get all properties and assign to fields
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
				if (currentLine.contains(fname + "," + lname + "," + email)) {
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
		MatcherMain parent = (MatcherMain) SwingUtilities
				.getWindowAncestor(this).getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(editButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setSignupFrame(tutee);
			MatcherMain.setEditing(fname, lname, email);
			parent.setContentPane(MatcherMain.signupframe);
		} else if (event.getSource().equals(reportButton)&&
				!tuteeList.get(tuteeComboBox.getSelectedIndex()).equals("")) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setSessionReportFrame(this.fname + " " + this.lname,
					tuteeList.get(tuteeComboBox.getSelectedIndex()), email, subj);
			parent.setContentPane(MatcherMain.sessionreportframe);
		} else if (event.getSource().equals(viewTutorsButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setTutorTableFrame(subjectnum, email, fname, lname,
					subj);
			parent.setContentPane(MatcherMain.tutortableframe);
		} else if (event.getSource().equals(logButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setLogFrame(subjectnum, email, fname, lname, subj);
			parent.setContentPane(MatcherMain.logframe);
		} else if (event.getSource().equals(logoutButton)) {
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setMainFrame();
			parent.setContentPane(MatcherMain.mainframe);
		} else if (event.getSource().equals(acceptRequestButton)) {
			int result = JOptionPane.showConfirmDialog(optionsPanel, reqinfo[3]
					+ " " + reqinfo[4] + " " + "wants you to tutor them in "
					+ reqinfo[6], null, JOptionPane.YES_NO_CANCEL_OPTION);

			String messageText = "";
			if (result == JOptionPane.YES_OPTION) {
				messageText = reqinfo[0] + " " + reqinfo[1] + " has accepted "
						+ reqinfo[3] + " " + reqinfo[4]
						+ "'s request to be tutored in " + reqinfo[6];
				
				try {
					BufferedReader br = new BufferedReader(new FileReader("requests.csv"));
					String currentLine = "";
					String writeToFile = "";
					currentLine = br.readLine();
					while (currentLine != null) {
						if (!currentLine.equals(reqinfoString)) {
							writeToFile += currentLine + "\n";
						}
						currentLine = br.readLine();
					}
					FileOutputStream File = new FileOutputStream("requests.csv");
					File.write(writeToFile.getBytes());
					File.close();
					br.close();
				} catch (Exception e) {
				}

				// add tutee to list of tutees that the tutor has
				try {
					BufferedReader br = new BufferedReader(new FileReader("tutorlist.csv"));
					String currentLine = "";
					String writeToFile = "";
					currentLine = br.readLine();
					while (currentLine != null) {
						if (currentLine.contains(fname+","+lname+","+email)) {
							String replacement = "~"+reqinfo[3]+" "+reqinfo[4]+"~"+currentLine.split(",")[7].substring(1);
							currentLine = currentLine.replace(currentLine.split(",")[7],replacement);
						}
						writeToFile += currentLine + "\n";
						currentLine = br.readLine();
					}
					FileOutputStream File = new FileOutputStream("tutorlist.csv");
					File.write(writeToFile.getBytes());
					File.close();
					br.close();
				} catch (Exception ex) {
				}
			} else if (result == JOptionPane.NO_OPTION) {
				messageText = reqinfo[0] + " " + reqinfo[1]
						+ " has declined your request to be tutored in "
						+ reqinfo[6];
			}

			// Sending confirmation message
			String to = reqinfo[5];
			String from = "simplesolutionsprogrammers@gmail.com";
			final String username = "simplesolutionsprogrammers";
			final String password = "ssprogramming";
			Properties properties = System.getProperties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			Session session = Session.getInstance(properties,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});
			properties.setProperty("mail.user", username);
			properties.setProperty("mail.password", password);

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
				message.setSubject("Tutor Request Reply");
				message.setText(messageText);

				if (result == JOptionPane.YES_OPTION) {
					message.addRecipient(Message.RecipientType.CC,
							new InternetAddress("suriya.kandaswamy@gmail.com"));
				}

				Transport.send(message);

				BufferedReader br = new BufferedReader(new FileReader(
						"requests.csv"));
				String currentLine = br.readLine();
				String writeToFile = "";
				while (currentLine != null) {
					if (!currentLine.equals(reqinfoString)) {
						writeToFile += currentLine + "\n";
					}
				}
				FileOutputStream File = new FileOutputStream("requests.csv");
				File.write(writeToFile.getBytes());
				File.close();
				br.close();

			} catch (Exception e) {

			}
			
			this.setVisible(false);
			this.getParent().remove(this);
			MatcherMain.setOptionsFrame(subj, fname, lname, email, tutee);
			parent.setContentPane(MatcherMain.optionsframe);
		} else if (event.getSource().equals(cancelPairingButton)&&
				!tuteeList.get(cancelTuteeComboBox.getSelectedIndex()).equals("")) {
			int result = JOptionPane.showConfirmDialog(optionsPanel, 
					"Are you sure you want to cancel the pairing\nwith "+
					tuteeList.get(cancelTuteeComboBox.getSelectedIndex()), null, JOptionPane.YES_NO_OPTION);
			if (result==JOptionPane.YES_OPTION) {
				
				try {
					BufferedReader br = new BufferedReader(new FileReader("tutorlist.csv"));
					String currentLine = "";
					String writeToFile = "";
					currentLine = br.readLine();
					while (currentLine != null) {
						if (currentLine.contains(fname+","+lname+","+email)) {
							currentLine = currentLine.replace(tuteeList.get(cancelTuteeComboBox.getSelectedIndex())+"~",
									"");
						}
						writeToFile += currentLine + "\n";
						currentLine = br.readLine();
					}
					FileOutputStream File = new FileOutputStream("tutorlist.csv");
					File.write(writeToFile.getBytes());
					File.close();
					br.close();
				} catch (Exception ex) {
				}
				
				// Sending confirmation message
				String to = "suriya.kandaswamy@gmail.com";
				String from = "simplesolutionsprogrammers@gmail.com";
				final String username = "simplesolutionsprogrammers";
				final String password = "ssprogramming";
				Properties properties = System.getProperties();
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port", "587");
				Session session = Session.getInstance(properties,
						new Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username,
										password);
							}
						});
				properties.setProperty("mail.user", username);
				properties.setProperty("mail.password", password);

				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(to));
					message.setSubject("Tutor Request Reply");
					message.setText(fname+" "+lname+" has canceled a tutoring pair with tutee "+
					tuteeList.get(cancelTuteeComboBox.getSelectedIndex()));

					Transport.send(message);
				} catch (Exception e) {

				}
				
				this.setVisible(false);
				this.getParent().remove(this);
				MatcherMain.setOptionsFrame(subj, fname, lname, email, tutee);
				parent.setContentPane(MatcherMain.optionsframe);
			}
		}

	}

}
