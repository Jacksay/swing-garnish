/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacksay.sgarnish.views;

import com.jacksay.sgarnish.exceptions.UserFriendlyException;
import com.jacksay.sgarnish.i18n.JckResourceBundle;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author Stéphane Bouvry <stephane.bouvry@unicaen.fr>
 */
public class JckHtmlPanel extends JPanel {
    
    private URL htmlPath;
    
    public JckHtmlPanel( URL htmlFilePath) {
        super();
        setLayout(new BorderLayout());
        htmlPath = htmlFilePath;
        initComponent();
        
    }

    private void initComponent() {
      JEditorPane credits;
        try {
            credits = new JEditorPane(htmlPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            credits = new JEditorPane();
            credits.setText("Bad path");
        }
	credits.setContentType("text/html");
	credits.setFont(new Font("Arial", Font.ROMAN_BASELINE, 14));
        System.out.println(credits.getText());
	credits.setEditable(false);
	credits.addHyperlinkListener(new HyperlinkListener() {
	    @Override
	    public void hyperlinkUpdate(HyperlinkEvent he) {
		if( he.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED) ){
		    try {
			if( Desktop.isDesktopSupported() ){
			    try {
				Desktop.getDesktop().browse(he.getURL().toURI());
			    } catch (IOException ex) {
				Logger.getLogger(JckViewAbout.class.getName()).log(Level.SEVERE, null, ex);
			    }
			}
			System.out.println(he.getURL().toURI());
		    } catch (URISyntaxException ex) {
			Logger.getLogger(JckViewAbout.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		
	    }
	});
        add(new JScrollPane(credits), BorderLayout.CENTER);
    }
    
}
