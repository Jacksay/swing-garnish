package com.jacksay.sgarnish.containers;

import com.jacksay.sgarnish.assets.JckIconProvider;
import com.jacksay.sgarnish.i18n.JckResourceBundle;
import com.jacksay.sgarnish.views.JckViewAbout;
import com.jacksay.sgarnish.views.JckViewParameters;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public abstract class JckApplicationFrame extends JFrame {

    public Dimension defaultSize = new Dimension(800, 600);
    
    // --- MESSAGES
    public String msgWindowClosing = "Êtes-vous sûr de vouloir quitter l'application ?";
    
    // --- ACTIONS
    public Action quitAction, parametersAction, aboutAction;
    
    // --- MENUS de BASE
    public JMenu menuFile, menuEdit, menuAbout;
    
    // --- VIEWS
    private JckViewParameters viewParameters;
    private JckViewAbout viewAbout;
   /* private JckParameters viewParameters;
    private JckParameters viewParameters;*/
    
    public JckApplicationFrame() throws HeadlessException {
        this("");
    }
    public JckApplicationFrame(String title) throws HeadlessException {
        initializeConfiguration();
        initializeResources();
        initializeActions();
        initializeComponment();
        initializeEvent();
        initializeMenu();
        setTitle(JckResourceBundle.get("title"));
    }

    private void initializeMenu() {
        setJMenuBar(getCustomMenuBar());
    }
    
    protected JMenuBar getCustomMenuBar() {
        JMenuBar menu = new JMenuBar();
        
        // Menu "File"
        menuFile = new JMenu(JckResourceBundle.get("file"));
        menuFile.add(quitAction);
        menu.add(menuFile);
        
        menuEdit = new JMenu(JckResourceBundle.get("edition"));
        menuEdit.add(parametersAction);
        menu.add(menuEdit);
        
        // Menu "About"
        menuAbout = new JMenu(JckResourceBundle.get("about"));
        menuAbout.add(aboutAction);
        menu.add(menuAbout);
        
        return menu;
    }

    private void initializeResources() {
        //JckResourceBundle.addResourceFile("jckapplication");
    }
    
    private void initializeComponment() {
         setLocationRelativeTo(null);
         setIconImage(JckIconProvider.getImage("wrench"));
        setSize(defaultSize);
    }

    private void initializeEvent() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing(e); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private void initializeActions() {
        quitAction = new AbstractAction(JckResourceBundle.get("quit"), JckIconProvider.getIcon("cancel")) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                onWindowClosing(null);
            }
        };
        
        parametersAction = new AbstractAction(JckResourceBundle.get("parameters"), JckIconProvider.getIcon("cog")) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                displayParameters();
            }

        };
        
        aboutAction = new AbstractAction(JckResourceBundle.get("about"), JckIconProvider.getIcon("information")) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                displayAbout();
            }

        };
    }
    
    protected void displayParameters() {
        if( viewParameters == null ) {
            viewParameters = new JckViewParameters(this);
        }
        viewParameters.setVisible(true);
    }
    
     protected void displayAbout() {
        if( viewAbout == null )
            viewAbout = new JckViewAbout(this);
        viewAbout.setVisible(true);
    }
     
      protected void displayHelp() {
        
    }
    
    // HANDLER -----------------------------------------------------------------
    protected void onWindowClosing(WindowEvent e) {
        if (requireConfirmationOnClosing()) {
            int response = JOptionPane.showConfirmDialog(this, msgWindowClosing, JckResourceBundle.get("quit"), JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    
    
    
    public boolean requireConfirmationOnClosing() {
        return msgWindowClosing != null;
    }
    
    
    abstract public void initializeConfiguration();
}
