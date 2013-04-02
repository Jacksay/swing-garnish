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

import com.jacksay.sgarnish.assets.JckIconProvider;
import com.jacksay.sgarnish.events.IJckTabRendererListenner;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public class JckTabRenderer extends JPanel implements MouseListener, FocusListener, ChangeListener {

    private final JTabbedPane pane;
    private long lastPress = -1;
    private JLabel label;
    private JckTextInput newLabel;
    private List<IJckTabRendererListenner> listenners = new ArrayList<IJckTabRendererListenner>();

    private JckTabRenderer(final JTabbedPane pane, boolean editable, boolean closable) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.pane = pane;
        setOpaque(false);
        pane.addChangeListener(this);

        // --- Label
        label = new JLabel() {
            @Override
            public String getText() {
                return getLabel();
            }
        };
        label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 2));
        label.addMouseListener(this);
        add(label);

        // --- Input
        newLabel = new JckTextInput(label.getText(), "Tip tab name...");
        newLabel.setVisible(false);
        newLabel.setMinimumSize(new Dimension(100, 16));
        newLabel.addFocusListener(this);
        newLabel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    updateLabelFromInput();
                } else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    cancelLabelFromInput();
                }
            }
        });
        add(newLabel);

        // --- Button
        JButton buttonClose = new JButton(JckIconProvider.getIcon("cancel"));
        buttonClose.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 0));
        buttonClose.setUI(new BasicButtonUI());
        buttonClose.setOpaque(false);
        buttonClose.setToolTipText("Close this tab.");
        buttonClose.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTab();
            }
        });
        add(buttonClose);
    }

    public void removeTab() {
        int indexTab = pane.indexOfTabComponent(this);
        pane.remove(indexTab);
        fireRemove();
        //removeTabRendererListenners();
        
    }
    // -------------------------------------------------------------------------
    // PUBLIC ACCESS

    /**
     * Create a rendrerer for all Tab.
     *
     * @param pane Target JTabbedPane
     * @param closable true if closable
     * @param editable true if editable
     */
    public static void createRendererForAll(JTabbedPane pane, boolean closable, boolean editable) {
        for (int i = 0; i < pane.getTabCount(); i++) {
            createRenderer(i, pane, closable, editable);
        }
    }

    public static void createRendererForAll(JTabbedPane pane, boolean closable) {
        createRendererForAll(pane, closable, true);
    }

    public static void createRendererForAll(JTabbedPane pane) {
        createRendererForAll(pane, true, true);
    }

    // -------------------------------------------------------------------------
    public static JckTabRenderer createRenderer(int index, JTabbedPane pane, boolean closable, boolean editable) {
        JckTabRenderer renderer = new JckTabRenderer(pane, closable, editable);
        pane.setTabComponentAt(index, renderer);
        return renderer;
    }

    public static JckTabRenderer createRenderer(int index, JTabbedPane pane, boolean closable) {
        return createRenderer(index, pane, closable, true);
    }

    public static JckTabRenderer createRenderer(int index, JTabbedPane pane) {
        return createRenderer(index, pane, true, true);
    }

    // -------------------------------------------------------------------------
    public static JckTabRenderer createRenderForLastAddedTab(JTabbedPane pane, boolean closable, boolean editable) {
        int lastIndex = pane.getTabCount() - 1;
        return createRenderer(lastIndex, pane, closable, editable);
    }

    public static JckTabRenderer createRenderForLastAddedTab(JTabbedPane pane, boolean closable) {
        return createRenderForLastAddedTab(pane, closable, true);
    }

    public static JckTabRenderer createRenderForLastAddedTab(JTabbedPane pane ) {
        return createRenderForLastAddedTab(pane, true, true);
    }
    
    // -------------------------------------------------------------------------
    // PUBLIC INSTANCE ACCESS
    
    // --- Listenner
    public void addTabRendererListenner( IJckTabRendererListenner l ){
        listenners.add(l);
    }
    public void removeTabRendererListenner( IJckTabRendererListenner l ){
        listenners.remove(l);
    }
    public void removeTabRendererListenners(){
        for( IJckTabRendererListenner l : listenners )
            removeTabRendererListenner(l);
    }
    protected void fireLabelChange(){
        for( IJckTabRendererListenner l : listenners )
            l.onTabRendererLabelChanged(this);
    }
    protected void fireRemove(){
        for( IJckTabRendererListenner l : listenners ){
            l.onTabRendererRemoved(this);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // Detect double-clivk
        long now = new Date().getTime();
        if ((now - lastPress) < 500) {
            label.setVisible(false);
            newLabel.setText(label.getText());
            newLabel.setVisible(true);
            newLabel.requestFocus();
            invalidate();
            validate();

        } else {
            dispatchEvent(e);
        }
        lastPress = now;
    }

    protected void dispatchEvent(MouseEvent e) {
        pane.dispatchEvent(SwingUtilities.convertMouseEvent(label, e, pane));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dispatchEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dispatchEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        dispatchEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        dispatchEvent(e);
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == newLabel) {
            updateLabelFromInput();
        } else if (e.getSource() == this) {
            System.out.println("test");
        }
    }

    public void setLabel(String text) {
        pane.setTitleAt(pane.indexOfTabComponent(JckTabRenderer.this), text);
    }

    public String getLabel() {
        int i = pane.indexOfTabComponent(JckTabRenderer.this);
        if (i != -1) {
            return pane.getTitleAt(i);
        }
        return null;
    }

    protected void updateLabelFromInput() {
        if( !newLabel.getText().equals(label.getText()) ){
            setLabel(newLabel.getText());
            fireLabelChange();
        }
        label.setVisible(true);
        newLabel.setVisible(false);
        invalidate();
        validate();
    }

    protected void cancelLabelFromInput() {
        newLabel.setText(label.getText());
        label.setVisible(true);
        newLabel.setVisible(false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (newLabel.isVisible()) {
            updateLabelFromInput();
        }
    }
}
