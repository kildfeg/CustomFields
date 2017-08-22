package field.util;

import javax.swing.ImageIcon;

public class IconUtil {

	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(Class clazz, String path, String description) {
		java.net.URL imgURL = clazz.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
