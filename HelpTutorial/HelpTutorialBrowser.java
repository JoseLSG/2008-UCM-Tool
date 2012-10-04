
package HelpTutorial;

import java.io.IOException;
import javax.swing.JOptionPane;

public class HelpTutorialBrowser {
	// Used to identify the windows platform.
	private static final String WIN_ID = "Windows";

	// The default system browser under windows.
	private static final String WIN_PATH = "rundll32";

	// The flag to display a url.
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

	// The default browser under unix.
	private static final String UNIX_PATH = "firefox";

	// The flag to display a url.
	private static final String UNIX_FLAG = "-remote";

	private HelpTutorialBrowser() {}

	/**
	* Display a file in the system browser.  If you want to display a
	* file, you must include the absolute path name.
	*
	* @param url the file's url (the url must start with either "http://"
	or
	* "file://").
	*/
	public static void displayURL(String url) {
		boolean windows = isWindowsPlatform();
		boolean mac = isMacintoshPlatform();
		String cmd = null;
		try {
			if (windows) {
				// cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
				cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
				Process p = Runtime.getRuntime().exec(cmd);
			}   
			else {
				// Under Unix, Firefox has to be running for the "-remote"
				// command to work.  So, we try sending the command and
				// check for an exit value.  If the exit command is 0,
				// it worked, otherwise we need to start the browser.
				// cmd = 'firefox -remote http://www.javaworld.com'
				cmd = UNIX_PATH + " " + UNIX_FLAG + " \"" + url + "\"";
				Process p = Runtime.getRuntime().exec(cmd);
				try {
					// wait for exit code -- if it's 0, command worked,
					// otherwise we need to start the browser up.
					int exitCode = p.waitFor();
					if (exitCode != 0) {
						// Command failed, start up the browser
						// cmd = 'firefox http://www.javaworld.com'
						cmd = UNIX_PATH + " " + url;
						p = Runtime.getRuntime().exec(cmd);

						exitCode = p.waitFor();
	
						if (exitCode != 0) {
							// try Mozilla
							cmd = "mozilla -remote \"" + url + "\"";
							p = Runtime.getRuntime().exec(cmd);
							exitCode = p.waitFor();

							if (exitCode != 0) {
								cmd = "mozilla \"" + url + "\"";
								p = Runtime.getRuntime().exec(cmd);
								exitCode = p.waitFor();

								if (exitCode != 0) {
									cmd = "konqueror \"" + url + "\"";
									p = Runtime.getRuntime().exec(cmd);
									exitCode = p.waitFor();

									if (exitCode != 0) {
										JOptionPane.showMessageDialog(null, "Unable to instantiate browser.  This feature may not be supported on your platform.", "Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
								}
							}
						}
					}
				} 
				catch (InterruptedException x) {
					JOptionPane.showMessageDialog(null, "Unable to instantiate browser.  This feature may not be supported on your platform.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} 
	
		catch (IOException x) {
			JOptionPane.showMessageDialog(null, "Unable to instantiate browser.  This feature may not be supported on your platform.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

    public static boolean isWindowsPlatform() {
    	String os = System.getProperty("os.name");
    	
    	if (os != null && os.startsWith(WIN_ID))
    		return true;
    	else
    		return false;
	}

	public static boolean isMacintoshPlatform() {
		return System.getProperty("os.name").startsWith("Mac");
	}
	
}
	