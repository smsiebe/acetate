package org.geoint.acetate.impl.model;

import java.util.Objects;
import org.geoint.acetate.model.ModelContextPath;

/**
 *
 */
public abstract class ImmutableContextPath implements ModelContextPath {

    private final String domainName;
    private final long domainVersion;
    private final String path;
    private static final String PATH_SCHEME = "acetatemodel://";
    private static final String DOMAIN_VERSION_SEPARATOR = "-";

    private ImmutableContextPath(String domainName, long domainVersion,
            String baseComponent) {
        this.path = new StringBuilder(PATH_SCHEME)
                .append(domainName)
                .append(DOMAIN_VERSION_SEPARATOR)
                .append(String.valueOf(domainVersion))
                .append(PathDelimiter.OBJECT_COMPOSITE)
                .append(baseComponent).toString();
        this.domainName = domainName;
        this.domainVersion = domainVersion;
    }

    private ImmutableContextPath(ModelContextPath baseContext,
            String... components) {
        this.domainName = baseContext.getDomainName();
        this.domainVersion = baseContext.getDomainVersion();
        StringBuilder sb = new StringBuilder(baseContext.asString());
        for (String c : components) {
            sb.append(c);
        }
        this.path = sb.toString();
    }

    private ImmutableContextPath(ModelContextPath baseContext,
            PathDelimiter delimiter) {
        this(baseContext, delimiter.getSeparator());
    }

    private ImmutableContextPath(ModelContextPath baseContext,
            PathDelimiter delimiter,
            String componentName) {
        this(baseContext, delimiter.getSeparator(), componentName);
    }

    /**
     * Creates the context path for the "base" definition of a domain model
     * component.
     *
     * @param domainName domain name
     * @param domainVersion domain version
     * @param componentName name of the component
     * @return component context path for a base component definition
     */
    public static ImmutableObjectPath basePath(String domainName,
            long domainVersion, String componentName) {
        return new ImmutableObjectPath(domainName, domainVersion,
                componentName);
    }

    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public long getDomainVersion() {
        return domainVersion;
    }

    @Override
    public String asString() {
        return path;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.domainName);
        hash = 41 * hash + (int) (this.domainVersion ^ (this.domainVersion >>> 32));
        hash = 41 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableContextPath other = (ImmutableContextPath) obj;
        if (!Objects.equals(this.domainName, other.domainName)) {
            return false;
        }
        if (this.domainVersion != other.domainVersion) {
            return false;
        }
        return Objects.equals(this.path, other.path);

    }

    public static class ImmutableObjectPath extends ImmutableContextPath {

        private ImmutableObjectPath(ModelContextPath baseContext,
                PathDelimiter delimiter) {
            super(baseContext, delimiter.getSeparator());
        }

        private ImmutableObjectPath(String domainName, long domainVersion,
                String baseComponent) {
            super(domainName, domainVersion, baseComponent);
        }

        private ImmutableObjectPath(ModelContextPath baseContext, String contextName) {
            super(baseContext, PathDelimiter.OBJECT_COMPOSITE, contextName);
        }

        private ImmutableObjectPath(ModelContextPath baseContext,
                PathDelimiter delimiter, String contextName) {
            super(baseContext, delimiter, contextName);
        }

        /**
         * Creates a new context path for a composite object.
         *
         * @param compositeLocalName local composite name
         * @return component context path for a component in a composite context
         */
        public ImmutableObjectPath compositeObject(String compositeLocalName) {
            return new ImmutableObjectPath(this, compositeLocalName);
        }

        /**
         * Creates a new component path for a composite collection.
         *
         * @param compositeLocalName
         * @return
         */
        public ImmutableCollectionPath compositeCollection(
                String compositeLocalName) {
            return new ImmutableCollectionPath(this, compositeLocalName);
        }

        /**
         * Creates a new component path for a composite map.
         *
         * @param compositeLocalName
         * @return composite context path for a map
         */
        public ImmutableMapPath compositeMap(String compositeLocalName) {
            return new ImmutableMapPath(this, compositeLocalName);
        }

        /**
         * Creates a new context path for an operation of this component.
         *
         * @param operationName
         * @return context path of the component operation
         */
        public ImmutableOperationPath operation(String operationName) {
            return new ImmutableOperationPath(this, operationName);
        }
    }

    public static class ImmutableCollectionPath extends ImmutableObjectPath {

        private ImmutableCollectionPath(ModelContextPath path) {
            super(path, PathDelimiter.COLLECTION_COMPOSITE.getSeparator());
        }

        private ImmutableCollectionPath(ModelContextPath path,
                PathDelimiter delimiter) {
            super(path, delimiter, PathDelimiter.COLLECTION_COMPOSITE.getSeparator());
        }

        private ImmutableCollectionPath(ModelContextPath baseContext,
                String componentName) {
            super(baseContext, PathDelimiter.COLLECTION_COMPOSITE, componentName);
        }

        /**
         * A collection containing a collection.
         *
         * @return
         */
        public ImmutableCollectionPath collection() {
            return new ImmutableCollectionPath(this);
        }

        /**
         * A collection of maps.
         *
         * @return composite context path for a map
         */
        public ImmutableMapPath map() {
            return new ImmutableMapPath(this);
        }

    }

    public static class ImmutableOperationPath extends ImmutableContextPath {

        private ImmutableOperationPath(ModelContextPath baseContext,
                String contextName) {
            super(baseContext, PathDelimiter.OPERATION, contextName);
        }

        public ImmutableObjectPath parameter(String paramName) {
            return new ImmutableObjectPath(this,
                    PathDelimiter.OPERATION_PARAM, paramName);
        }

        public ImmutableCollectionPath parameterCollection(
                String compositeLocalName) {
            return new ImmutableCollectionPath(this, compositeLocalName);
        }

        public ImmutableMapPath parameterMap(String compositeLocalName) {
            return new ImmutableMapPath(this, compositeLocalName);
        }

        public ImmutableObjectPath returned() {
            return new ImmutableObjectPath(this, PathDelimiter.OPERATION_RETURN);
        }

        public ImmutableCollectionPath returnedCollection(
                String compositeLocalName) {
            return new ImmutableCollectionPath(this, compositeLocalName);
        }

        public ImmutableMapPath returnedMap(String compositeLocalName) {
            return new ImmutableMapPath(this, compositeLocalName);
        }
    }

    public static class ImmutableMapPath extends ImmutableContextPath {

        private ImmutableMapPath(ModelContextPath baseContext, String compositeName) {
            super(baseContext, PathDelimiter.MAP_COMPOSITE, compositeName);
        }

        private ImmutableMapPath(ModelContextPath baseContext,
                PathDelimiter delimiter) {
            super(baseContext, delimiter,
                    PathDelimiter.MAP_COMPOSITE.getSeparator());
        }

        private ImmutableMapPath(ModelContextPath baseContext) {
            super(baseContext, PathDelimiter.MAP_COMPOSITE);
        }

        public ImmutableObjectPath keyObject() {
            return new ImmutableObjectPath(this, PathDelimiter.MAP_KEY);
        }

        /**
         * A key collection.
         *
         * @param compositeLocalName
         * @return
         */
        public ImmutableCollectionPath keyCollection(
                String compositeLocalName) {
            return new ImmutableCollectionPath(this, PathDelimiter.MAP_KEY);
        }

        /**
         * A map as a key.
         *
         * @param compositeLocalName
         * @return
         */
        public ImmutableMapPath keyMap(String compositeLocalName) {
            return new ImmutableMapPath(this, PathDelimiter.MAP_KEY);
        }

        public ImmutableObjectPath valueObject() {
            return new ImmutableObjectPath(this, PathDelimiter.MAP_VALUE);
        }

        /**
         * A key collection.
         *
         * @param compositeLocalName
         * @return
         */
        public ImmutableCollectionPath valueCollection(
                String compositeLocalName) {
            return new ImmutableCollectionPath(this, PathDelimiter.MAP_VALUE);
        }

        /**
         * A map as a key.
         *
         * @param compositeLocalName
         * @return
         */
        public ImmutableMapPath valueMap(String compositeLocalName) {
            return new ImmutableMapPath(this, PathDelimiter.MAP_VALUE);
        }
    }

    private static enum PathDelimiter {

        OBJECT_COMPOSITE("/"),
        COLLECTION_COMPOSITE("@"),
        MAP_COMPOSITE("#"),
        OPERATION("!"),
        OPERATION_PARAM("?"),
        OPERATION_RETURN(">"),
        MAP_KEY("*"),
        MAP_VALUE("&");

        private final String separator;

        private PathDelimiter(String separator) {
            this.separator = separator;
        }

        public String getSeparator() {
            return separator;
        }

    }
}
