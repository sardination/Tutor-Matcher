import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class TutorTableFrame extends JPanel implements ActionListener {

	private int subjectNum;
	private String[] columnNames = {"First Name", "Last Name", "Email", "Times Available", 
			"Additional Notes"};
	private String[][] tutorInfo;
	
	private JPanel TableScreen;
	private JTable table;
	private JTextArea infoScreen;
	
	private JButton selectTutorButton;
	
	private String tuteeEmail;
	private String fname;
	private String lname;
	private String subject;
	
	public TutorTableFrame(int num, String email, String fname, String lname, String subject) {
		subjectNum = num;
		tuteeEmail = email;
		this.fname = fname;
		this.lname = lname;
		this.subject = subject;
		TableScreen = new JPanel();
		TableScreen.setLayout(new BorderLayout(0,10));
		TableScreen.add(new JLabel("Select a row to view more about the tutor",
				SwingConstants.CENTER),BorderLayout.NORTH);
		infoScreen = new JTextArea(7,9);
		infoScreen.setEditable(false);
		infoScreen.setLineWrap(true);
		infoScreen.setWrapStyleWord(true);
		JScrollPane infoscrollPane = new JScrollPane(infoScreen);
		selectTutorButton = new JButton("I want this tutor - an email will be sent to the tutor.");
		selectTutorButton.setEnabled(false);
		selectTutorButton.addActionListener(this);

		findTutors();

		table = new JTable();
		DefaultTableModel model = new DefaultTableModel(tutorInfo,columnNames) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(model);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				int row = table.getSelectedRow();
				infoScreen.setText("Name: "+tutorInfo[row][0]+" "+tutorInfo[row][1]+"\n\n"+
				"Email: "+tutorInfo[row][2]+"\n\n"+"Times Available: "+tutorInfo[row][3]+
				"\n\n"+"Additional Notes: "+tutorInfo[row][4]);
				selectTutorButton.setEnabled(true);
			}
		});
		JScrollPane tablescrollPane = new JScrollPane(table);
		tablescrollPane.setPreferredSize(new Dimension(400,200));
		table.setFillsViewportHeight(true);
		
		JPanel southPanel = new JPanel(new GridLayout(2,1,0,0));
		southPanel.add(infoscrollPane);
		southPanel.add(selectTutorButton);
		
		TableScreen.add(southPanel,BorderLayout.SOUTH);
		TableScreen.add(tablescrollPane,BorderLayout.CENTER);
		
		this.add(TableScreen);
	}
	
	public void findTutors() {
		ArrayList<String> fileInfo = new ArrayList<String>();
		BufferedReader br = null;
		try {
			String currentLine = "";
			br = new BufferedReader(new FileReader("tutorlist.csv"));
			currentLine = br.readLine();
			while (currentLine != null) {
				if (Integer.parseInt(currentLine.split(",")[3])<=subjectNum) {
					fileInfo.add(currentLine);
				}
				currentLine = br.readLine();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TableScreen, "Sorry, please come back later. There are"
					+ " currently no tutors registered.");
		}
		
		tutorInfo = new String[fileInfo.size()][5];
		
		int index = 0;
		for (String i:fileInfo) {
			String[] tempInfo = i.split(",");
			if (tempInfo.length == 6) {
				String[] temptempInfo = new String[7];
				for (int j=0; j<6; j++) {
					temptempInfo[j] = tempInfo[j];
				}
				temptempInfo[6] = "";
				tempInfo = new String[7];
				for (int j=0; j<7; j++) {
					tempInfo[j] = temptempInfo[j];
				}
			} else if (tempInfo.length == 5) {
				String[] temptempInfo = new String[7];
				for (int j=0; j<5; j++) {
					temptempInfo[j] = tempInfo[j];
				}
				temptempInfo[5] = "";
				temptempInfo[6] = "";
				tempInfo = new String[7];
				for (int j=0; j<7; j++) {
					tempInfo[j] = temptempInfo[j];
				}
			}

			
			tutorInfo[index][0] = tempInfo[0];
			tutorInfo[index][1] = tempInfo[1];
			tutorInfo[index][2] = tempInfo[2];
			tutorInfo[index][3] = tempInfo[5];
			tutorInfo[index][4] = tempInfo[6];
			index++;
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(selectTutorButton)) {
			String to = tutorInfo[table.getSelectedRow()][2];
			String from = "simplesolutionsprogrammers@gmail.com";
			final String username = "simplesolutionsprogrammers";
			final String password = "ssprogramming";
			Properties properties = System.getProperties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			Session session = Session.getInstance(properties,new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});
			properties.setProperty("mail.user", username);
			properties.setProperty("mail.password", password);
			
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Tutor Request");
				message.setText("Hello, \n\nMy name is "+fname+" "+lname+". I would like you to tutor me in "+subject
						+". My email is: "+tuteeEmail+". Go to <INSERT LINK HERE> to confirm this pairing. \n\n"
						+"Please email me so we can discuss good times to have sessions. \n\n"
						+ "Thank you, \n"+fname+" "+lname);
				Transport.send(message);
				JOptionPane.showMessageDialog(TableScreen,"An email has been sent to your chosen tutor.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(TableScreen,"Sorry, something did not work. Please contact your chosen tutor using the email"
						+ " shown in the table.");
			}
			
		}

	}

}
