package org.geoint.acetate.impl.model.scan;

import java.io.File;
import org.geoint.acetate.model.scan.ModelComponentListener;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Scans a java .class file for domain model component(s).
 */
public class ClassModelScanner implements ModelScanner {

    private final File classFile;
    private final ClassLoader classloader; //may be null

    /**
     *
     * @param classFile
     * @param classloader optional classloader, uses classloader used to load
     * this class if null
     */
    public ClassModelScanner(File classFile, ClassLoader classloader) {
        this.classFile = classFile;
        this.classloader = classloader;
    }

    @Override
    public void scan(ModelComponentListener... listeners)
            throws ModelScanException {
        throw new UnsupportedOperationException();
    }

}
