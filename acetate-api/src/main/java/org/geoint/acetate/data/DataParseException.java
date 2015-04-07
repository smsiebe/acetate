package org.geoint.acetate.data;

/**
 * Thrown when data could not be parsed as expected.
 */
public class DataParseException extends Exception {

    private final String dataSourceIdentity;
    private final Class<? extends DataParser> parserType;

    public DataParseException(String dataSourceIdentity,
            Class<? extends DataParser> parserType) {
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataParseException(String dataSourceIdentity,
            Class<? extends DataParser> parserType, String message) {
        super(message);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataParseException(String dataSourceIdentity,
            Class<? extends DataParser> parserType, String message,
            Throwable cause) {
        super(message, cause);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    public DataParseException(String dataSourceIdentity,
            Class<? extends DataParser> parserType, Throwable cause) {
        super(cause);
        this.dataSourceIdentity = dataSourceIdentity;
        this.parserType = parserType;
    }

    /**
     * Returns the parser type that had the problem.
     *
     * @return data parser type
     */
    public Class<? extends DataParser> getParserType() {
        return parserType;
    }

    /**
     * Returns the identity of the data source of the data being parsed.
     *
     * @return data source identity
     */
    public String getDataSourceIdentity() {
        return dataSourceIdentity;
    }

}
