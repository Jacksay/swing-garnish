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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckToogleActionTest {
    
    public JckToogleActionTest() {
    }
    private String value;
    @Test
    public void testSomeMethod() {
        
        String labelA = "Action A";
        String labelB = "Action B";
        
        Action setA = new AbstractAction(labelA) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                value = "A";
            }
        };
        
        Action setB = new AbstractAction(labelB) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                value = "B";
            }
        };
        
        assertNull(value);
        
        // Test alone action
        setA.actionPerformed(null);
        assertEquals("A", value);
        assertEquals(labelA, setA.getValue(Action.NAME));
        
        // Test alone action
        setB.actionPerformed(null);
        assertEquals("B", value);
        assertEquals(labelB, setB.getValue(Action.NAME));

        // Create ToogleAction
        JckToogleAction toggle = new JckToogleAction(setA, setB);
        assertEquals(labelA, toggle.getValue(Action.NAME));
        
        toggle.actionPerformed(null);
        assertEquals("A", value);
        assertEquals(labelB, toggle.getValue(Action.NAME));
        
        
        toggle.actionPerformed(null);
        assertEquals("B", value);
        assertEquals(labelA, toggle.getValue(Action.NAME));
        
        System.out.println(toggle.getValue(Action.NAME));
        
        
        
    }
}
