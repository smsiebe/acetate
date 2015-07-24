package org.geoint.acetate.model.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lazily resolves the class.
 *
 * @param <T>
 */
public class LazyClassLoaderResolver<T> implements Resolver<Class<T>> {

    private final String className;
    private Class<T> loaded;
    private final ClassLoader loader;

    private static final Logger logger
            = Logger.getLogger(LazyClassLoaderResolver.class.getName());

    public LazyClassLoaderResolver(String className) {
        this(className, LazyClassLoaderResolver.class.getClassLoader());
    }

    public LazyClassLoaderResolver(String className, ClassLoader loader) {
        this.className = className;
        this.loader = loader;
    }

    @Override
    public Class<T> resolve() {
        synchronized (this) {
            if (loaded == null) {
                try {
                    loaded = (Class<T>) loader.loadClass(className);
                } catch (ClassNotFoundException ex) {
                    logger.log(Level.SEVERE, String.format("Unable to lazily resolve "
                            + "class '%s$' from classloader '%s$'",
                            className, loader.toString()), ex);
                    throw new RuntimeException(String.format("Aceate metamodel was "
                            + "unable to resolve modeled class '%s$' at runtime.",
                            className), ex);
                }
            }
        }
        return loaded;
    }

}
