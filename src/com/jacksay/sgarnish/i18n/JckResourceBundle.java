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
package com.jacksay.sgarnish.i18n;

import com.jacksay.sgarnish.parameters.JckUserParameters;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class provide a very simple feature. It merge any resource file an 
 * allow to override internationalization default file with static method 'addResourceFile'.
 * 
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckResourceBundle {
    
    private List<ResourceBundle> files;
    private static JckResourceBundle instance;
    private String missingResourceFormat = "%{0}%";

    private JckResourceBundle() {
        files = new ArrayList<>();
        files.add(ResourceBundle.getBundle("jckapplication", Locale.getDefault()));
    }
    
    private static JckResourceBundle getInstance(){
        if( instance == null )
            instance = new JckResourceBundle();
        return instance;
    }
    
    public static void addResourceFile( String baseName ){
        System.out.println(baseName);
        getInstance().files.add(ResourceBundle.getBundle(baseName, JckUserParameters.getLocale()));
    }
    
    public static void enabledMissingTag( boolean enabled ){
        getInstance().missingResourceFormat = enabled ? "%[0]%" : "{0}";
    }
    
    public static String get(String name){
        for( int i = getInstance().files.size()-1; i>=0; i-- ){
            try {
             return getInstance().files.get(i).getString(name);
            } catch( MissingResourceException e ){
                continue;
            }
        }
        return MessageFormat.format(getInstance().missingResourceFormat, name);
    }
    
    
    
}
