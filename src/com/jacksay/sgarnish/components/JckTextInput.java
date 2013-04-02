/*
 * The MIT License
 *
 * Copyright 2013 Stéphane Bouvry<stephane.bouvry@unicaen.fr>.
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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTextField;


/**
 *
 * @author Stéphane Bouvry <stephane.bouvry@unicaen.fr>
 */
public class JckTextInput extends JTextField implements FocusListener {
    
    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Color DEFAULT_COLOR = Color.BLACK;
    
    // Fonts
    public Font fontPlaceHolder;
    
    // Colors
    public Color colorPlaceHolder;
    
    // Placeholder text
    private String textPlaceHolder = "";
    
    // Icon
    private Icon icon;
    
    
    public JckTextInput(String text, String placeHolder ) {
	this(text, placeHolder, null);
    }
    
    public JckTextInput(String text, String placeHolder, Icon icon ) {
	super(text);
	textPlaceHolder		= placeHolder;
	
	
	if( icon != null ){
	    this.icon = icon;
	    setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(0, 18, 0, 0)));
	}
	addFocusListener(this);
    }

    public void setIcon(Icon icon) {
        if( icon != this.icon ){
            this.icon = icon;
            repaint();
        }
    }

    public void setTextPlaceHolder(String textPlaceHolder) {
         if( textPlaceHolder != this.textPlaceHolder ){
             this.textPlaceHolder = textPlaceHolder;
            repaint();
        }
       
    }
    
    
    
    @Override
    public void setFont(Font f) {
	super.setFont(f);
	fontPlaceHolder		= getFont().deriveFont(Font.ITALIC, getFont().getSize()-1);
    }
    
    @Override
    public void setForeground(Color fg) {
	super.setForeground(fg);
	colorPlaceHolder	= new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 125);
    }
    /*
    public static void main( String[] args ){
	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(800, 600);
	f.setLayout(new BorderLayout());
	
	Box c = Box.createHorizontalBox();
	JckTextInput t;
	c.add(t = new JckTextInput("", "Votre nom...", JckIconProvider.getIcon("cancel")), BorderLayout.PAGE_START);
	t.setMinimumSize(new Dimension(150,32));
	c.add(new JckTextInput("", "Votre prénom...", JckIconProvider.getIcon("cancel")));
	f.add(c, BorderLayout.PAGE_START);
	f.add(t = new JckTextInput("", "Year...", JckIconProvider.getIcon("cancel")));
	t.setForeground(Color.BLUE);
	t.setFont(new Font("Times", Font.BOLD, 16));
	
	f.setVisible(true);
    }/****/
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	int paddingX = 2;
	if( icon != null ){
	    int y = (getHeight() - icon.getIconHeight())/2;
	    icon.paintIcon(this, g, paddingX, y);
	    paddingX += icon.getIconWidth();
	}
	if( getText().equals("") && !hasFocus() ){
		Graphics2D g2 = (Graphics2D)g;
		FontMetrics metric = g2.getFontMetrics(fontPlaceHolder);
		int x = paddingX;
		int y = metric.getHeight()+(getHeight()-metric.getHeight())/2-2;
		
		g2.setFont(fontPlaceHolder);
		g2.setColor(colorPlaceHolder);
		//g2.fillRect(2, 2, 150, 18);
		
		g2.drawString(textPlaceHolder, x, y);
	}
    }
    
    
    
    @Override
    public void focusGained(FocusEvent fe) {
	repaint(0,0,getWidth(), getHeight());
    }

    @Override
    public void focusLost(FocusEvent fe) {
	repaint(0,0,getWidth(), getHeight());
    }
    
}
