package com.jacksay.sgarnish.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;

/**
 *
 * @author Stéphane Bouvry <stephane.bouvry@unicaen.fr>
 */
public class JckLineBoxFactory {
    public static Box getLabeledLine( String label, Component component ){
        return getLabeledLine(new JLabel(label), component);
    }
    public static Box getLabeledLine( JLabel label, Component component ){
        Box box = Box.createHorizontalBox();
        box.setBackground(Color.RED);
        box.add(Box.createGlue());
        box.add(label);
        box.add(component);
        box.add(Box.createGlue());
        return box;
    }
}
