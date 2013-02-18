/*
 * The MIT License
 *
 * Copyright 2013 Stéphane Bouvry <stephane.bouvry@unicaen.fr>.
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
package com.jacksay.sgarnish.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 *
 * @author Stéphane Bouvry <stephane.bouvry@unicaen.fr>
 */
public class ViewEventDispatcherImpl implements IViewEventDispatcher {
    
    private static final Logger logger = Logger.getLogger(ViewEventDispatcherImpl.class.getName());
    private List<IViewEventListener> listeners = new ArrayList<>();
    private IViewEventDispatcher master;
    
    
    public ViewEventDispatcherImpl(IViewEventDispatcher master) {
	this.master = master;
    }
    
    
    
    @Override
    public void addViewEventListener(IViewEventListener listener) {
	listeners.add(listener);
    }

    @Override
    public void removeViewEventListener(IViewEventListener listener) {
	listeners.remove(listener);
    }
    
    public void fireChange(){
	for( IViewEventListener l : listeners ){
	    l.onViewChange(master);
	}
    }
    public void fireClose(){
	for( IViewEventListener l : listeners ){
	    l.onViewClose(master);
	}
    }
    public void fireApear(){
	for( IViewEventListener l : listeners ){
	    l.onViewAppear(master);
	}
    }
    
}
