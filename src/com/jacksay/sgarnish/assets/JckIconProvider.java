package com.jacksay.sgarnish.assets;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sun.awt.image.ToolkitImage;

/**
 * @author Jacksay<jacksay@jacksay.com>
 */
public class JckIconProvider {

    private static JckIconProvider instance;

    private static JckIconProvider getInstance() {
        if (instance == null) {
            instance = new JckIconProvider();
        }
        return instance;
    }
    private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

    private Icon getIconInternal(String name) {
        if (!icons.containsKey(name)) {
            icons.put(name, new ImageIcon(getClass().getResource("/com/jacksay/sgarnish/assets/" + name + ".png")));
        }
        return icons.get(name);
    }
    
    private Image getImageInternal(String name) {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("com/jacksay/sgarnish/assets/" +name +".png"));
    }

    public static Icon getIcon(String name) {
        return getInstance().getIconInternal(name);
    }
    
    public static Image getImage(String name) {
        return getInstance().getImageInternal(name);
    }
}
