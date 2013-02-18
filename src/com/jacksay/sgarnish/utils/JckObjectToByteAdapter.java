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

import java.io.*;

/**
 * Static methods for convert bytes to object (and this reverse)
 * @author jacksay
 */
public class JckObjectToByteAdapter {
    
    /**
     * Convert object to an ByteArray.
     * 
     * @param object
     * @return
     * @throws IOException 
     */
    public static byte[] objectToBytes( Object object ) throws IOException{
        byte[] output = null;
        if( object != null ){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(object);
            output = stream.toByteArray();
        }
        return output;
    }
    
    /**
     * Convert Bytes to an object.
     * 
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Object bytesToObject( byte[] bytes ) throws IOException, ClassNotFoundException
    {
        if( bytes == null ){
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
}
