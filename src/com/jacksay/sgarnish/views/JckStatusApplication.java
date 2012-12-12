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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckStatusApplication extends JPanel {
    private JckApplicationFrame app;
    private Box box;
    private JLabel status = new JLabel(JckIconProvider.getIcon("information"));
    private Icon iconWarning, iconError, iconInfo;

    public JckStatusApplication(JckApplicationFrame app) {
        this.app = app;
        iconWarning = JckIconProvider.getIcon("caution_high_voltage");
        iconError = JckIconProvider.getIcon("exclamation");
        iconInfo = JckIconProvider.getIcon("information");
        
        initializeComponent();
    }
    
    public void addError( Exception ex ){
        System.err.println(ex.getCause());
        error(ex.getMessage());
    }

    private void initializeComponent() {
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(Color.WHITE);
        
        box = Box.createHorizontalBox();
        box.add(new Box.Filler(new Dimension(10, 10), new Dimension(500, 10), new Dimension(Integer.MAX_VALUE, 10)));
        box.add(status);
        box.add(Box.createHorizontalGlue());
        add(box);
        
        // 
        status.setFont(new Font("Arial", Font.PLAIN, 10));
        info(app.getTitle());
    }
    
    protected void changeStatus(String message, Icon icon ){
        status.setIcon(icon);
        status.setText(message);
    }
    
    public void info(String message ){
        changeStatus(message, iconInfo);
    }
    
    public void warning(String message ){
        changeStatus(message, iconWarning);
    }
    
    public void error(String message ){
        changeStatus(message, iconError);
    }
    
}
