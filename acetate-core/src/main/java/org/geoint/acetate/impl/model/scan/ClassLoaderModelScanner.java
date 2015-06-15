package org.geoint.acetate.impl.model.scan;

import org.geoint.acetate.model.scan.ModelComponentListener;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Scans for model components available from the provided ClassLoader.
 */
public class ClassLoaderModelScanner implements ModelScanner {

    private final ClassLoader loader;

    /**
     * Scan for model components using the
     * {@link ClassLoader#getSystemClassLoader() system class loader}.
     */
    public ClassLoaderModelScanner() {
        this(ClassLoader.getSystemClassLoader());
    }

    public ClassLoaderModelScanner(ClassLoader loader) {
        this.loader = loader;
    }

    @Override
    public void scan(ModelComponentListener listener) {
fsdf
    }

}
