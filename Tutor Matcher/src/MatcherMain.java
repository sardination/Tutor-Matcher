import javax.swing.JApplet;
import javax.swing.SwingUtilities;


public class MatcherMain extends JApplet {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

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
	
	private void createGUI() {
		setSize(1000,400);
		SignupFrame signupFrame = new SignupFrame(true);
		signupFrame.setBounds(0,0,1000,1000);
		setContentPane(signupFrame);
	}
	
}
