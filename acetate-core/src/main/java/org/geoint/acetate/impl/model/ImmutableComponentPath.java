package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ComponentPath;

/**
 *
 */
public class ImmutableComponentPath implements ComponentPath {

    private enum ContextRelationship {

        COMPOSITE("#"),
        OPERATION("!"),
        OPERATION_PARAM(">"),
        OPERATION_RETURN("<");
        private final String separator;

        private ContextRelationship(String separator) {
            this.separator = separator;
        }

        public String getSeparator() {
            return separator;
        }

    }

    private final String domainName;
    private final long domainVersion;
    private final String path;
    private static final String PATH_SCHEME = "acetatemodel://";
    private static final String DOMAIN_VERSION_SEPARATOR = "-";

    private ImmutableComponentPath(String domainName, long domainVersion,
            String... pathComponents) {
        final StringBuilder sb = new StringBuilder(PATH_SCHEME)
                .append(domainName)
                .append(DOMAIN_VERSION_SEPARATOR)
                .append(String.valueOf(domainVersion));
        for (String pathComponent : pathComponents) {
            sb.append(pathComponent);
        }
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.path = sb.toString();
    }

    private ImmutableComponentPath(ComponentPath baseContext,
            ContextRelationship relationship,
            String contextName) {
        this.domainName = baseContext.getDomainName();
        this.domainVersion = baseContext.getDomainVersion();
        this.path = new StringBuilder(baseContext.asString())
                .append(relationship.getSeparator())
                .append(contextName).toString();
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
    public static ImmutableComponentPath basePath(String domainName,
            long domainVersion, String componentName) {
        return new ImmutableComponentPath(domainName, domainVersion,
                componentName);
    }

    /**
     * Creates a new context path for a composite of this component.
     *
     * @param compositeLocalName local composite name
     * @return component context path for a component in a composite context
     */
    public ImmutableComponentPath compositePath(String compositeLocalName) {
        return new ImmutableComponentPath(this, ContextRelationship.COMPOSITE,
                compositeLocalName);
    }

    /**
     * Creates a new context path for an operation of this component.
     *
     * @param operationName
     * @return context path of the component operation
     */
    public OperationPath operation(String operationName) {
        return new ImmutableOperationPath(this, operationName);
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

    private class ImmutableOperationPath extends ImmutableComponentPath
            implements OperationPath {

        public ImmutableOperationPath(ComponentPath baseContext,
                String contextName) {
            super(baseContext, ContextRelationship.OPERATION, contextName);
        }

        @Override
        public ImmutableComponentPath parameter(String paramName) {
            return new ImmutableComponentPath(this,
                    ContextRelationship.OPERATION_PARAM,
                    paramName);
        }

        @Override
        public ImmutableComponentPath returned() {
            return new ImmutableComponentPath(this,
                    ContextRelationship.OPERATION_RETURN,
                    "");
        }

    }
}
