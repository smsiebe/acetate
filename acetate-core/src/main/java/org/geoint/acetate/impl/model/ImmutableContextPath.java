package org.geoint.acetate.impl.model;

import java.util.Objects;
import org.geoint.acetate.model.ModelContextPath;

/**
 *
 */
public abstract class ImmutableContextPath implements ModelContextPath {

    private final String domainName;
    private final long domainVersion;
    private final String componentName;
    private final String path;
    private static final String PATH_SCHEME = "acetatemodel://";
    private static final String DOMAIN_VERSION_SEPARATOR = "-";

    private ImmutableContextPath(String domainName, long domainVersion,
            String componentName) {
        this.path = new StringBuilder(PATH_SCHEME)
                .append(domainName)
                .append(DOMAIN_VERSION_SEPARATOR)
                .append(String.valueOf(domainVersion))
                .append(PathDelimiter.OBJECT_COMPOSITE)
                .append(componentName)
                .toString();
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.componentName = componentName;
    }

    private ImmutableContextPath(ImmutableContextPath baseContext,
            PathDelimiter delimiter,
            String componentName) {
        this.domainName = baseContext.getDomainName();
        this.domainVersion = baseContext.getDomainVersion();
        this.componentName = componentName;
        String sb = baseContext.asString()
                + delimiter.getSeparator()
                + componentName;
        this.path = sb;
    }

    private ImmutableContextPath(ImmutableContextPath baseContext,
            PathDelimiter delimiter) {
        this.domainName = baseContext.getDomainName();
        this.domainVersion = baseContext.getDomainVersion();
        this.componentName = PathDelimiter.OPERATION_RETURN.getSeparator()
                + baseContext.getComponentName();
        String sb = baseContext.asString()
                + delimiter.getSeparator()
                + componentName;
        this.path = sb;
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

    public String getComponentName() {
        return componentName;
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

        private ImmutableObjectPath(ImmutableContextPath baseContext,
                PathDelimiter delimiter) {
            super(baseContext, delimiter);
        }

        private ImmutableObjectPath(String domainName, long domainVersion,
                String objectName) {
            super(domainName, domainVersion, objectName);
        }

        private ImmutableObjectPath(ImmutableContextPath baseContext, String contextName) {
            super(baseContext, PathDelimiter.OBJECT_COMPOSITE, contextName);
        }

        private ImmutableObjectPath(ImmutableContextPath baseContext,
                PathDelimiter delimiter, String contextName) {
            super(baseContext, delimiter, contextName);
        }

        /**
         * Creates a new context path for a composite object.
         *
         * @param compositeLocalName local composite name
         * @return component context path for a component in a composite context
         */
        public ImmutableObjectPath composite(String compositeLocalName) {
            return new ImmutableObjectPath(this, compositeLocalName);
        }

        /**
         * Creates a new context path for an aggregate object.
         *
         * @param aggregateLocalName local composite name
         * @return component context path for a component in a composite context
         */
        public ImmutableObjectPath aggregate(String aggregateLocalName) {
            return new ImmutableObjectPath(this, aggregateLocalName);
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

    public static class ImmutableOperationPath extends ImmutableContextPath {

        private ImmutableOperationPath(ImmutableContextPath baseContext,
                String contextName) {
            super(baseContext, PathDelimiter.OPERATION, contextName);
        }

        public ImmutableObjectPath parameter(String paramName) {
            return new ImmutableObjectPath(this,
                    PathDelimiter.OPERATION_PARAM, paramName);
        }

        public ImmutableObjectPath returned() {
            return new ImmutableObjectPath(this, PathDelimiter.OPERATION_RETURN);
        }

    }

    private static enum PathDelimiter {

        OBJECT_COMPOSITE("/"),
        MAP_COMPOSITE("#"),
        OPERATION("!"),
        OPERATION_PARAM("?"),
        OPERATION_RETURN(">");

        private final String separator;

        private PathDelimiter(String separator) {
            this.separator = separator;
        }

        public String getSeparator() {
            return separator;
        }

    }
}
