/*
 * The MIT License
 *
 * Copyright 2012 Expression project.organization is undefined on line 6, column 57 in Templates/Licenses/license-mit.txt..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jacksay.sgarnish.views;

import com.jacksay.sgarnish.assets.JckIconProvider;
import com.jacksay.sgarnish.containers.JckApplicationFrame;
import com.jacksay.sgarnish.i18n.JckResourceBundle;
import com.jacksay.sgarnish.parameters.JckUserParameters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckViewAbout extends JDialog {

    JPanel controlBar, container;

    JckApplicationFrame app;

    JTabbedPane tab;
    
    JCheckBox opener;

    public JckViewAbout(JckApplicationFrame owner) {
	super(owner, JckResourceBundle.get("about"));
	app = owner;
	tab = new JTabbedPane();
	initializeComponent();
    }

    private void initializeComponent() {
	setSize(new Dimension(620, 500));
	setMinimumSize(getSize());
	setModal(false);
	setLayout(new BorderLayout(10, 10));
	setLocationRelativeTo(app);
	setTitle(JckResourceBundle.get("app.name") + JckResourceBundle.get("app.version"));

	container = new JPanel(new GridLayout(2, 1));
	tab.add(JckResourceBundle.get("informations"), container);
	tab.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	container.setBackground(Color.white);
	container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	container.setAlignmentX(CENTER_ALIGNMENT);
	JLabel label = new JLabel(
		"<html>"
		+ "<table width=570>"
		+ "<tr><td>"
		+ "<h1>" + JckResourceBundle.get("app.name") + "</h1>"
		+ "<p>"
		+ JckResourceBundle.get("version") + ": " + JckResourceBundle.get("app.version") + "<br>"
		+ JckResourceBundle.get("vendor") + ": " + JckResourceBundle.get("app.vendor") + "<br>"
		+ JckResourceBundle.get("site") + ": " + JckResourceBundle.get("app.site") + "<br>"
		+ JckResourceBundle.get("contact") + ": " + JckResourceBundle.get("app.contact") + "<br>"
		+ "</p>"
		+ "</td>"
		+ "<td align=right>"
		+ "<img src=\"" + getClass().getResource("/LOGO.png") + "\" alt=\"LOGO\"/>"
		+ "</td>"
		+ "</tr><tr>"
		+ "</table>"
		+ "</html>");

	label.setFont(new Font("Arial", Font.PLAIN, 14));
	container.add(label);

	JTextPane description = new JTextPane();
	description.setContentType("text/html");
	description.setText("<html>"+JckResourceBundle.get("app.description")+"</html>");
	description.setBorder(BorderFactory.createTitledBorder("Description : "));
	description.setEditable(false);
	container.add(description);

	// LICENSE
	try {
	    JEditorPane license = new JEditorPane(app.getClass().getResource(JckResourceBundle.get("file.license")));
	    license.setEditable(false);
	    tab.add(JckResourceBundle.get("license"), new JScrollPane(license));
	} catch (IOException ex) {
	    Logger.getLogger(JckViewAbout.class.getName()).log(Level.SEVERE, null, ex);
	}

	// LICENSE
	JEditorPane credits;
	StringBuilder contentCredits = new StringBuilder("<html>");
	try {
	    String line = null;
	    StringBuilder out = new StringBuilder();
	    BufferedReader reader = new BufferedReader(new FileReader(app.getClass().getResource(JckResourceBundle.get("file.credits")).getFile()));
	    while ((line = reader.readLine()) != null) {
		out.append(line);
	    }
	    contentCredits.append(out);
	} catch (Exception ex) {
	    Logger.getLogger(JckViewAbout.class.getName()).log(Level.SEVERE, null, ex);
	}
	credits = new JEditorPane();
	credits.setContentType("text/html");
	credits.setText(contentCredits + "<h1>Toolkit</h1>\n"
		+ "    <p>That software use several free tools : </p>\n"
		+ "    <ul>\n"
		+ "        <li><strong>Swing Garnish Library (by Jacksay)</strong> : A little framework which simplify developement of Swing desktop application. <a href=\"http://code.google.com/p/swing-garnish/\">http://code.google.com/p/swing-garnish/</a></li>\n"
		+ "        <li><strong>Fatcow Icon Library (by Fatcow)</strong> : 3000 Free \"Farm-Fresh Web Icons\". <a href=\"http://www.fatcow.com/free-icons\">http://www.fatcow.com/free-icons</a></li>\n"
		+ "    </ul></html>");
	credits.setFont(new Font("Arial", Font.ROMAN_BASELINE, 14));
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

	tab.add(JckResourceBundle.get("credits"), new JScrollPane(credits));

	controlBar = new JPanel(new FlowLayout());
	controlBar.setAlignmentX(CENTER_ALIGNMENT);
	JButton close = new JButton(JckResourceBundle.get("close"), JckIconProvider.getIcon("door_open"));
	close.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent ae) {
		dispose();
	    }

	});
        
        opener = new JCheckBox(JckResourceBundle.get("message.opener"), JckUserParameters.getShowAboutOnOpen());
        opener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JckUserParameters.setShowAboutOnOpen(opener.isSelected());
            }
        });
        controlBar.add(opener);
	controlBar.add(close);


	add(tab, BorderLayout.CENTER);
	add(controlBar, BorderLayout.PAGE_END);
    }

}
