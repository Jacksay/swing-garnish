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
package com.jacksay.sgarnish.components;

import com.jacksay.sgarnish.utils.JckCustomFileFilter;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * Simple class for create custom JFileChooser.
 * 
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckFileChooserFactory {
    public static JFileChooser createJFileChooser( JckCustomFileFilter filter, File defaultPath ){
        JFileChooser fc = new JFileChooser(defaultPath);
        fc.setFileFilter(filter);
        return fc;
    }
}
