import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
	
	public TutorTableFrame(int num) {
		subjectNum = num;
		TableScreen = new JPanel();
		TableScreen.setLayout(new BorderLayout(0,10));
		TableScreen.add(new JLabel("Select a row to view more about the tutor",
				SwingConstants.CENTER),BorderLayout.NORTH);
		infoScreen = new JTextArea(7,9);
		//infoScreen.setPreferredSize(new Dimension(7,9));
		//infoScreen.setMaximumSize(new Dimension(7,9));
		infoScreen.setEditable(false);
		infoScreen.setLineWrap(true);
		infoScreen.setWrapStyleWord(true);
		JScrollPane infoscrollPane = new JScrollPane(infoScreen);

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
			}
		});
		JScrollPane tablescrollPane = new JScrollPane(table);
		tablescrollPane.setPreferredSize(new Dimension(400,200));
		table.setFillsViewportHeight(true);
		
		TableScreen.add(infoscrollPane,BorderLayout.SOUTH);
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
		// TODO Auto-generated method stub

	}

}
