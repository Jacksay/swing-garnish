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
public class JckCustomFileFilter extends FileFilter {

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
