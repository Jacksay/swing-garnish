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
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckViewAbout extends JDialog {
    
    
    JPanel controlBar, container;
    JckApplicationFrame app;
    
    public JckViewAbout(JckApplicationFrame owner) {
        super(owner, JckResourceBundle.get("about"));
        app = owner;
        initializeComponent();
    }

    private void initializeComponent() {
        setSize(new Dimension(550, 400));
        setModal(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(app);
        setTitle(JckResourceBundle.get("app.name") + JckResourceBundle.get("app.version"));
        
        container = new JPanel(new BorderLayout());
        JLabel label = new JLabel(JckResourceBundle.get("title"));
        label.setFont(new Font("Arial", Font.BOLD, 48));
        container.add(label);
        controlBar = new JPanel(new FlowLayout());
        controlBar.setAlignmentX(CENTER_ALIGNMENT);
        JButton close = new JButton(JckResourceBundle.get("close"), JckIconProvider.getIcon("cancel"));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        controlBar.add(close);
        
        
        add(container, BorderLayout.CENTER);
        add(controlBar, BorderLayout.PAGE_END);
    }
    
}
