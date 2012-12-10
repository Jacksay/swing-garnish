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

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckToogleAction extends AbstractAction 
{
    Action actionA, actionB, currentAction;
    
    public JckToogleAction(Action actionA, Action actionB) {
        this(actionA, actionB, actionA);
    }
    
    public JckToogleAction(Action actionA, Action actionB, Action currentAction) {
        this.actionA = actionA;
        this.actionB = actionB;
        this.currentAction = currentAction;
    }

    @Override
    public Object getValue(String string) {
        return currentAction.getValue(string);
    }

    @Override
    public void putValue(String string, Object o) {
        actionA.putValue(string, o);
        actionB.putValue(string, o);
    }

    @Override
    public void setEnabled(boolean bln) {
        actionA.setEnabled(bln);
        actionB.setEnabled(bln);
    }

    @Override
    public boolean isEnabled() {
        return currentAction.isEnabled();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        currentAction.actionPerformed(ae);
        switchAction();
    }

    private void switchAction() {
        Action oldAction = currentAction;
        
        if( currentAction == actionB ){
            currentAction = actionA;
        }
        else
        {
            currentAction = actionB;
        }
        firePropertyChange(
                Action.NAME, 
                oldAction.getValue(Action.NAME), 
                currentAction.getValue(Action.NAME));
        firePropertyChange(
                Action.SMALL_ICON, 
                oldAction.getValue(Action.SMALL_ICON), 
                currentAction.getValue(Action.SMALL_ICON));
    }
    
}
