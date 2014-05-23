import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
		
		c.gridy = y;
		y++;
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
		}
		
	}

}
