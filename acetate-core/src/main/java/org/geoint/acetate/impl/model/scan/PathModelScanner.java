package org.geoint.acetate.impl.model.scan;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.scan.ModelComponentListener;
import org.geoint.acetate.model.scan.ModelScanException;
import org.geoint.acetate.model.scan.ModelScanner;

/**
 * Scans a path (optionally recursively) for <i>.class</i> or </i>.jar</i> files
 * containing model component classes.
 */
public class PathModelScanner implements ModelScanner {

    private final Path rootPath;
    private final boolean recursive;
    private final ClassLoader loader; //may be null
    private final ExecutorService exec;
    private final Map<File, Future<?>> scans = new ConcurrentHashMap<>();

    private static final String CLASS_EXTENSION = ".class";
    private static final String JAR_EXTENSION = ".jar";

    private static final Logger logger = Logger.getLogger(PathModelScanner.class.getName());

    public PathModelScanner(ExecutorService exec,
            Path rootPath,
            boolean recursive,
            ClassLoader loader) {
        this.exec = exec;
        this.rootPath = rootPath;
        this.recursive = recursive;
        this.loader = loader;
    }

    public PathModelScanner(ExecutorService exec,
            Path rootPath,
            boolean recursive) {
        this.exec = exec;
        this.rootPath = rootPath;
        this.recursive = recursive;
        this.loader = null;
    }

    @Override
    public void scan(ModelComponentListener... listeners)
            throws ModelScanException {
        if (recursive) {
            logger.log(Level.FINE, "Recursively scanning ''{0}'' for model "
                    + "components.", rootPath.toString());

            try {
                Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file,
                            BasicFileAttributes attrs) throws IOException {
                        scanFile(file.toFile(), listeners);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Unable to complete scanning path '"
                        + rootPath.toString() + "' for model components", ex);
            }
        } else {
            logger.log(Level.FINE, "Scanning directory ''{0}'' for model "
                    + "components.", rootPath.toString());

            for (File f : rootPath.toFile().listFiles()) {
                scanFile(f, listeners);
            }
        }

        //wait until all file scans have been completed
        while (!scans.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                if (Thread.interrupted()) {
                    logger.log(Level.WARNING, "Path model scanner scanning path '"
                            + rootPath.toString() + "' is shutting down", ex);
                    scans.values().stream().forEach((f) -> f.cancel(true));
                }
            }
        }
    }

    private void scanFile(File f, ModelComponentListener... listeners) {
        ModelScanner scanner = getScannerForFile(f);

        if (scanner == null) {
            logger.log(Level.FINEST, "Path model scanner determined file ''{0}'' "
                    + "cannot be scanned.", f.getAbsolutePath());
            return;
        }

        scans.put(f, exec.submit(() -> {
            try {
                scanner.scan(listeners); //synchronous call
            } finally {
                scans.remove(f);//self-unregistger from scans when complete
            }
            return null;
        }));
    }

    private ModelScanner getScannerForFile(File f) {
        final String fileName = f.getName();
        switch (fileName.substring(fileName.lastIndexOf('.'))) {
            case CLASS_EXTENSION:
//                return new ClassModelScanner(f, loader);
            case JAR_EXTENSION:
//                return new JarModelScanner(f, loader);
            default:
                return null;
        }
    }

}
