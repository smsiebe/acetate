package org.geoint.acetate.transform;

/**
 * Thrown when data formatting could not be completed.
 */
public class DataFormatException extends DataTransformException {

    private final Class<? extends StringConverter> formatterType;
    private final String dataSourceIdentity;

    public DataFormatException(Class<? extends StringConverter> formatterType,
            String dataSourceIdentity) {
        this.formatterType = formatterType;
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataFormatException(Class<? extends StringConverter> formatterType,
            String dataSourceIdentity, String message) {
        super(message);
        this.formatterType = formatterType;
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataFormatException(Class<? extends StringConverter> formatterType,
            String dataSourceIdentity, String message, Throwable cause) {
        super(message, cause);
        this.formatterType = formatterType;
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataFormatException(Class<? extends StringConverter> formatterType,
            String dataSourceIdentity, Throwable cause) {
        super(cause);
        this.formatterType = formatterType;
        this.dataSourceIdentity = dataSourceIdentity;
    }

    /**
     * Returns the identity of the data source of the data being parsed.
     *
     * @return data source identity
     */
    public String getDataSourceIdentity() {
        return dataSourceIdentity;
    }

    /**
     * Data formatter type.
     *
     * @return formatter type
     */
    public Class<? extends StringConverter> getFormatter() {
        return formatterType;
    }

}
