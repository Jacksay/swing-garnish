package com.jacksay.sgarnish.containers;

import com.jacksay.sgarnish.assets.JckIconProvider;
import com.jacksay.sgarnish.exceptions.UserFriendlyException;
import com.jacksay.sgarnish.i18n.JckResourceBundle;
import com.jacksay.sgarnish.parameters.JckUserParameters;
import com.jacksay.sgarnish.views.JckStatusApplication;
import com.jacksay.sgarnish.views.JckViewAbout;
import com.jacksay.sgarnish.views.JckViewParameters;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;


/**
 *
 * @author Stéphane Bouvry<stephane.bouvry@unicaen.fr>
 */
public abstract class JckApplicationFrame extends JFrame implements Observer {

    public Dimension defaultSize = new Dimension(800, 600);

    // --- MESSAGES
    public boolean msgClosing = true;

    // --- ACTIONS
    public Action quitAction, parametersAction, aboutAction;
    
    protected SortedMap<String, JMenu> menus = new TreeMap<>();

    // --- MENUS de BASE
    public JMenu menuFile, menuEdit, menuAbout;
    protected JMenuBar mainMenu;

    // --- VIEWS
    protected JckViewParameters viewParameters;
    protected JckViewAbout viewAbout;
    protected JPanel mainFrame;
    protected JckStatusApplication statusBar;
    /* private JckParameters viewParameters;
     private JckParameters viewParameters;*/
    
    private List<Locale> locales = new ArrayList<>();

    public JckApplicationFrame() throws HeadlessException {
	this("");
    }
    
    public List<Locale> getLocales(){
	return locales;
    }

    public JckApplicationFrame(String title) throws HeadlessException {
        try {
            JckUserParameters.initialize(this.getClass());
            JckUserParameters.getInstance().addObserver(this);
            locales.add(Locale.ENGLISH);
            locales.add(Locale.FRANCE);
            //setLocale(JckUserParameters.getLocale());
            initializeConfiguration();
            initializeResources();
            initializeActions();
            initializeComponment();
            initializeEvent();
            initializeMenu();
            setTitle(JckResourceBundle.get("app.name") + " - version " + JckResourceBundle.get("app.version"));
            onOpen();
        } catch ( Exception ex ){
            getStatus().addError(ex);
        }
    }

    private void initializeMenu() {
	setJMenuBar(getCustomMenuBar());
    }
    
    
    // --- Main menus
    protected JMenu getMenu( String menuTag ){
        if( !menus.containsKey(menuTag) ){
            menus.put(menuTag, new JMenu(JckResourceBundle.get(menuTag)));
        }
        return menus.get(menuTag);
    }
    
    protected void populateMenu( JMenu menu, Action action, int key ){
        JMenuItem item;
        menu.add(item = new JMenuItem(action));
        item.setAccelerator(KeyStroke.getKeyStroke(key, KeyEvent.CTRL_MASK));
    }
    
    protected JMenuBar getCustomMenuBar() {
	mainMenu = new JMenuBar();
	JMenuItem item;
	// Menu "File"
	menuFile = new JMenu(JckResourceBundle.get("file"));
        populateFile( menuFile );
	menuFile.add(item = new JMenuItem(quitAction));
	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
	mainMenu.add(menuFile);

	menuEdit = new JMenu(JckResourceBundle.get("edit"));
	menuEdit.add(parametersAction);
	mainMenu.add(menuEdit);
        
        hookAddMenu(mainMenu);

	// Menu "About"
	menuAbout = new JMenu(JckResourceBundle.get("about"));
	item = new JMenuItem(aboutAction);
	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));
	menuAbout.add(item);
	mainMenu.add(menuAbout);

	return mainMenu;
    }
    
    public JckStatusApplication getStatus(){
        return statusBar;
    }

    private void initializeResources() {
	//JckResourceBundle.addResourceFile("jckapplication");
    }

    private void initializeComponment() {
	setIconImage(JckIconProvider.getImage("wrench"));
	setSize(defaultSize);
        setLayout(new BorderLayout());
        mainFrame = new JPanel();
        statusBar = new JckStatusApplication(this);
        add(mainFrame, BorderLayout.CENTER);
        add(statusBar, BorderLayout.PAGE_END);
        viewParameters = new JckViewParameters(this);
    }
    
    protected Component getMainFrame(){
        return mainFrame;
    }

    private void initializeEvent() {
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		onClose(e); //To change body of generated methods, choose Tools | Templates.
	    }

	});
    }
    
    /**
     * Initialize default actions.
     */
    private void initializeActions() {
	quitAction = new AbstractAction(JckResourceBundle.get("quit"), JckIconProvider.getIcon("cancel")) {
	    @Override
	    public void actionPerformed(ActionEvent ae) {
		onClose(null);
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
                try {
                    displayAbout();
                } catch (UserFriendlyException ex) {
                    getStatus().addError(ex);
                }
	    }

	};
    }

    protected void displayParameters() {
        viewParameters.setLocationRelativeTo(this);
	viewParameters.setVisible(true);
    }

    protected void displayAbout() throws UserFriendlyException {
	if (viewAbout == null) {
	    viewAbout = new JckViewAbout(this);
	}
	viewAbout.setVisible(true);
        viewAbout.setLocationRelativeTo(this);
    }

    protected void displayHelp() {
    }

    // HANDLER -----------------------------------------------------------------
    protected void onClose(WindowEvent e) {
	int response = JOptionPane.YES_OPTION;

	if (requireConfirmationOnClosing()) {
	    response = JOptionPane.showConfirmDialog(this,
		    JckResourceBundle.get("message.confirmclose"),
		    JckResourceBundle.get("quit"),
		    JOptionPane.YES_NO_OPTION);
	}

	if (response == JOptionPane.YES_OPTION) {
	    System.exit(0);
	}
    }
    
    protected void onOpen() throws UserFriendlyException{
        setVisible(true);
        setLocationRelativeTo(null);
        if( JckUserParameters.getShowAboutOnOpen() ){
            displayAbout();
        }
    }

    public boolean requireConfirmationOnClosing() {
	return msgClosing;
    }

    abstract public void initializeConfiguration();
    
    @Override
    public void update(Observable o, Object o1) {
	if( o == JckUserParameters.getInstance() && o1.equals("l18n") ){
	    System.out.println("totaly reload");
	    invalidate();
	    
	}
	
    }

    protected void populateFile(JMenu menuFile) {
        
    }

    public void hookAddParameters(JTabbedPane pane) {
        // Override
    }

    public void hookAddMenu(JMenuBar menu) {
        
    }
    
    
    

}
