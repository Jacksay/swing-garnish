/*
 * The MIT License
 *
 * Copyright 2012 Stéphane Bouvry
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
package com.jacksay.sgarnish.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Provide simple custom filter for file chooser.
 *
 * <pre>
 * JFileChooser fileChooser = new JFileChooser();
 * fileChooser.setFileFilter(new CustomFileFilter("Description of my file", "xml"));
 * </pre>
 *
 * Or :
 * <pre>
 * JFileChooser fileChooser = new JFileChooser();
 * fileChooser.setFileFilter(new CustomFileFilter("Images", new String[]{"jpg","jpeg","png"}));
 * </pre>
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckCustomFileFilter extends FileFilter implements java.io.FileFilter {

    private String description;
    private String[] extensions;

    public JckCustomFileFilter(String description, String extension) {
        this(description, new String[]{extension});
    }

    public JckCustomFileFilter(String description, String[] extensions) {
        String separator = "";
        this.description = description.concat("(");
        this.extensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            this.extensions[i] = extensions[i].toLowerCase();
            this.description = this.description.concat(separator).concat("*.").concat(extensions[i]);
            separator = ", ";
        }
        this.description = this.description.concat(")");
    }

    @Override
    public boolean accept(File file) {

        if (file.isDirectory()) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0; i < extensions.length; i++) {
                String extension = '.' + extensions[i];
                if (path.endsWith(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;

    }
}
