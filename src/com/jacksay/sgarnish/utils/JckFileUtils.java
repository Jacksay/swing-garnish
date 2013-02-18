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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckFileUtils {

    public static void copy(File source, File destination) throws JckFileUtilsException {
        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(source);
        } catch (FileNotFoundException ex) {
            throw new JckFileUtilsException("Source for copy '" + source.getAbsolutePath() + "' don't exist.", ex);
        }
        try {
            out = new FileOutputStream(destination, false);
        } catch (FileNotFoundException ex) {
            throw new JckFileUtilsException("Destination for copy '" + source.getAbsolutePath() + "' don't exist.", ex);
        }

        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException ex) {
           throw new JckFileUtilsException("Write '"+source.getAbsolutePath()+"' to '" + destination.getAbsolutePath() + "' error.", ex);
        }

    }

    public static String getAppPath(Class clazz) {

        String path = "/" + clazz.getName().replace('.', '/') + ".class";
        String url = JckFileUtils.class.getResource(path).getPath();

        int index;

        // Suppression du préfix 'file:'
        if (url.startsWith("file:")) {
            url = url.substring(5);
        }

        // Supression du suffixe !***
        index = url.lastIndexOf("!");
        if (index > -1) {
            url = url.substring(0, index);
        }

        // Suppression du chemin de fichier
        index = url.lastIndexOf("/");
        url = url.substring(0, index);

        // Suppression du package de la classe
        Package pack = clazz.getPackage();
        if (null != pack) {
            String packPath = pack.getName().replace('.', '/');
            if (url.endsWith(packPath)) {
                url = url.substring(0, (url.length() - packPath.length()));
            }
        }
	
	if( url.charAt(url.length()-1) != '/' ){
	    url += '/';
	}

        return url;
    }
}
