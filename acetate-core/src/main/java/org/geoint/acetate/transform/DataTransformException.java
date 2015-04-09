package org.geoint.acetate.transform;

/**
 *
 */
public class DataTransformException extends Exception {

    private final String dataSourceIdentity;
    private final Class<? extends DataTransformer> parserType;

    public DataTransformException(String dataSourceIdentity,
            Class<? extends DataTransformer> parserType) {
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataTransformException(String dataSourceIdentity,
            Class<? extends DataTransformer> parserType, String message) {
        super(message);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataTransformException(String dataSourceIdentity,
            Class<? extends DataTransformer> parserType, String message,
            Throwable cause) {
        super(message, cause);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataTransformException(String dataSourceIdentity,
            Class<? extends DataTransformer> parserType, Throwable cause) {
        super(cause);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    /**
     * Returns the parser type that had the problem.
     *
     * @return data parser type
     */
    public String getDataSourceIdentity() {
        return dataSourceIdentity;
    }

    /**
     * Returns the identity of the data source of the data being parsed.
     *
     * @return data source identity
     */
    public Class<? extends DataTransformer> getParserType() {
        return parserType;
    }

}
