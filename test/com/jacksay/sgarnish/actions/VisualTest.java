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
package com.jacksay.sgarnish.actions;

import com.jacksay.sgarnish.assets.JckIconProvider;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class VisualTest {
    
    public static void main( String[] args ){
        String labelA = "Action A";
        String labelB = "Action B";
        
        Action setA = new AbstractAction(labelA, JckIconProvider.getIcon("accept")) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("A");
            }
        };
        
        Action setB = new AbstractAction(labelB, JckIconProvider.getIcon("cancel")) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("B");
            }
        };
        
        JckToogleAction toggle = new JckToogleAction(setA, setB);
        
        JFrame f = new JFrame("Test TOGGLE ACTION");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JButton(toggle));
        f.pack();
        f.setVisible(true);
    }
}
