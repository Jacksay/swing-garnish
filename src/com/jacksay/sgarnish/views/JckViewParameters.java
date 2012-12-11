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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckViewParameters extends JDialog {
    
    
    private JPanel controlBar, container;
    private JckApplicationFrame app;
    JComboBox<Locale> languagesCombo;
    
    public JckViewParameters(JckApplicationFrame owner, String title) {
        super(owner, title);
        app = owner;
        initializeComponent();
    }
    
    public JckViewParameters(JckApplicationFrame owner) {
        this(owner, JckResourceBundle.get("parameters"));
    }

    private void initializeComponent() {
        
        setLayout(new BorderLayout());
        
        // Container
        container = new JPanel(new BorderLayout());
	
        // Générale
        JPanel general = new JPanel();
        general.setLayout(new FlowLayout());
        
	Box languages = Box.createHorizontalBox();
        languages.add(new JLabel(JckResourceBundle.get("chose_language") +" : "));
	languagesCombo = new JComboBox<>(new Locale[]{Locale.ENGLISH, Locale.FRENCH});
	languagesCombo.setSelectedItem(JckUserParameters.getLocale());
	languagesCombo.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent ae) {
		JckUserParameters.setLocale((Locale)languagesCombo.getSelectedItem());
	    }
	});
        languages.add(languagesCombo);
	general.add(languages);
        
        JTabbedPane pane = new JTabbedPane();
	pane.add(JckResourceBundle.get("general"), general);
        
        app.hookAddParameters(pane);
        
        pane.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
	
        add(pane, BorderLayout.CENTER);
        
        // ControlBar
        controlBar = new JPanel();
        
        JButton close, save;
        
        close = new JButton(JckResourceBundle.get("close"), JckIconProvider.getIcon("door_open"));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        controlBar.add(close);
        add(controlBar, BorderLayout.PAGE_END);
        
        
        setModal(true);
        setLocationRelativeTo(getOwner());
        setSize(new Dimension(750, 500));
        setMaximumSize(getSize());
        
        setIconImage(JckIconProvider.getImage("cog"));
        
        
    }
    
}
