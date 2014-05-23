import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MatcherMain extends JApplet {

	public static MainFrame mainframe;
	public static SignupFrame signupframe;
	public static SessionReportFrame sessionreportframe;
	public static TutorTableFrame tutortableframe;
	public static OptionsFrame optionsframe;
	public static SessionReportLogFrame logframe;

	public void init() {
		// Implementing the "seaglass" look and feel (from
		// https://code.google.com/p/seaglass/)
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Execute a job on the event-dispatching thread; creating this applet's
		// GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}

	}

	public static void setSignupFrame(boolean tutee) {
		signupframe = new SignupFrame(tutee);
	}

	public static void setEditing(String f, String l, String e) {
		signupframe.setEditing(f, l, e);
	}

	public static void setMainFrame() {
		mainframe = new MainFrame();
	}

	public static void setOptionsFrame(String s, String f, String l, String e,
			boolean tutee) {
		optionsframe = new OptionsFrame(s, f, l, e, tutee);
	}

	public static void setTutorTableFrame(int subj, String email, String fname,
			String lname, String subject) {
		tutortableframe = new TutorTableFrame(subj, email, fname, lname,
				subject);
	}

	public static void setSessionReportFrame(String tuto, String tute, String email, String subject) {
		sessionreportframe = new SessionReportFrame(tuto, tute, email, subject);
	}
	
	public static void setLogFrame(int num, String email, String fname, String lname,
			String subject) {
		logframe = new SessionReportLogFrame(num, email, fname, lname, subject);
	}

	private void createGUI() {
		setSize(600, 450);
		mainframe = new MainFrame();
		mainframe.setBounds(0, 0, 1000, 1000);
		setContentPane(mainframe);

		/* Tutor Signup */
		/*
		 * signupframe = new SignupFrame(false);
		 * signupframe.setBounds(0,0,1000,1000); setContentPane(signupframe);
		 */

		/* Tutee Signup */
		/*
		 * signupframe = new SignupFrame(true);
		 * signupframe.setBounds(0,0,1000,1000); setContentPane(signupframe);
		 */

		/* Session Report */
		/*
		 * sessionreportframe = new SessionReportFrame();
		 * sessionreportframe.setBounds(0,0,1000,1000);
		 * setContentPane(sessionreportframe);
		 */

		/* Tutor Table */
		/*
		 * tutortableframe = new TutorTableFrame(0);
		 * tutortableframe.setBounds(0,0,1000,1000);
		 * setContentPane(tutortableframe);
		 */
	}

}
