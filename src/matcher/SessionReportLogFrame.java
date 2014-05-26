package matcher;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class SessionReportLogFrame extends JPanel implements ActionListener {

	JPanel LogScreen;
	JTextArea infoScreen;
	JButton backToOptionsButton;
	String[] columnNames = {"Tutee", "Date", "Minutes", "Supervisor", "Additional Notes"};
	String[][] sessions = new String[0][5];
	int totalMinutes = 0;
	JTable table;
	JButton clearSessionReportButton;
	
	//info from optionspane
	int num;
	String email;
	String fname;
	String lname;
	String subject;
	
	public SessionReportLogFrame(int num, String email, String fname, String lname,
			String subject) {
		this.num = num;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.subject = subject;
		
		LogScreen = new JPanel();
		LogScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(15, 0, 0, 15);
		
		int y = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		y++;
		LogScreen.add(new JLabel("Session Reports Log - Select a row to view more about that session",
				SwingConstants.CENTER), c);
		
		infoScreen = new JTextArea(7, 9);
		infoScreen.setEditable(false);
		infoScreen.setLineWrap(true);
		infoScreen.setWrapStyleWord(true);
		JScrollPane infoscrollPane = new JScrollPane(infoScreen);
		backToOptionsButton = new JButton("Back To Options Menu");
		backToOptionsButton.addActionListener(this);
		clearSessionReportButton = new JButton("Clear Session Reports");
		clearSessionReportButton.addActionListener(this);
		
		ArrayList<String> fileInfo = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fname+" "+lname+".csv"));
			String currentLine = br.readLine();
			while (currentLine != null) {
				fileInfo.add(currentLine);
				currentLine = br.readLine();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(LogScreen,
					"You have not logged any sessions.");
		}
		
		sessions = new String[fileInfo.size()][5];
		for (int i=0; i<fileInfo.size(); i++) {
			sessions[i] = fileInfo.get(i).split(",");
			totalMinutes += Integer.parseInt(sessions[i][2]);
		}
		
		c.gridy = y;
		y++;
		LogScreen.add(new JLabel("Minutes Logged: "+totalMinutes, SwingConstants.CENTER), c);
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel(sessions, columnNames) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(model);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int row = table.getSelectedRow();
						infoScreen.setText("Tutee: "
								+ sessions[row][0] + "\n\n"
								+"Date: " + sessions[row][1] + " "
								+ "\n\n" + "Minutes: "
								+ sessions[row][2] + "\n\n"
								+ "Supervisor: " + sessions[row][3]
								+ "\n\n" + "Additional Notes: "
								+ sessions[row][4]
								+ "\n\n");
					}
				});
		JScrollPane tablescrollPane = new JScrollPane(table);
		tablescrollPane.setPreferredSize(new Dimension(400, 100));
		table.setFillsViewportHeight(true);
		
		c.gridy = y;
		y++;
		LogScreen.add(tablescrollPane,c);
		
		c.gridy = y;
		y++;
		LogScreen.add(infoscrollPane, c);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y;
		y++;
		LogScreen.add(clearSessionReportButton, c);
		
		c.gridx = 1;
		LogScreen.add(backToOptionsButton, c);
		
		this.add(LogScreen);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		MatcherMain parent = (MatcherMain) SwingUtilities
				.getWindowAncestor(this).getComponent(0).getComponentAt(0, 0);
		if (event.getSource().equals(backToOptionsButton)) {
			MatcherMain.setOptionsFrame(subject, fname, lname, email, false);
			this.setVisible(false);
			parent.remove(this);
			parent.setContentPane(MatcherMain.optionsframe);
		} else if (event.getSource().equals(clearSessionReportButton)) {
			int result = JOptionPane.showConfirmDialog(LogScreen, "Are you sure you want to clear this log?\n"
					+ "An email will be sent with your log once\nyou clear the log", null, JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				String to = email;
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
					Message message  = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("Session Report Log");
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText("Your session report is attached.");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					
					messageBodyPart = new MimeBodyPart();
					String filename = fname+" "+lname+".csv";
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);
					
					message.setContent(multipart);
					
					Transport.send(message);
					
					FileOutputStream File = new FileOutputStream(fname+" "+lname+".csv");
					File.write(new String("").getBytes());
					File.close();
					
					MatcherMain.setLogFrame(num, email, fname, lname, subject);
					this.setVisible(false);
					parent.remove(this);
					parent.setContentPane(MatcherMain.logframe);
				} catch (Exception e) {
				}
			}
		}
		
	}

}
