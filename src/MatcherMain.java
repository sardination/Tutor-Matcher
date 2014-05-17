import javax.swing.JApplet;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class MatcherMain extends JApplet {

	public static MainFrame mainframe;
	public static SignupFrame signupframe;
	public static SessionReportFrame sessionreportframe;
	public static TutorTableFrame tutortableframe;


	public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
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
	
	public static void setSignup(boolean tutee) {
		signupframe = new SignupFrame(tutee);
	}
	
	private void createGUI() {
		setSize(600,400);
		mainframe = new MainFrame();
		mainframe.setBounds(0,0,1000,1000);
		setContentPane(mainframe);
		
		/*Tutor Signup*/
		/*
		signupframe = new SignupFrame(false);
		signupframe.setBounds(0,0,1000,1000);
		setContentPane(signupframe);
		 */
		
		/*Tutee Signup*/
		/*
		signupframe = new SignupFrame(true);
		signupframe.setBounds(0,0,1000,1000);
		setContentPane(signupframe);
		 */
		
		/*Session Report*/
		/*
		sessionreportframe = new SessionReportFrame();
		sessionreportframe.setBounds(0,0,1000,1000);
		setContentPane(sessionreportframe);
		 */
		
		/*Tutor Table*/
		/*
		tutortableframe = new TutorTableFrame(0);
		tutortableframe.setBounds(0,0,1000,1000);
		setContentPane(tutortableframe);
		 */
	}
	
}
