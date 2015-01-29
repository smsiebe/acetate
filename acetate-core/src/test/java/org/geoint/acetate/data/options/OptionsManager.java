package org.geoint.acetate.data.options;

import java.util.Collection;

/**
 * The acetate OptionsManager is a centralized store for optional values that
 * may be used by a data item.
 *
 * The OptionsManager is used to create and manage an enumerative set of values
 * for an associated "option name", which are defined at runtime.
 */
public interface OptionsManager {

    /**
     * Returns the registered option names.
     *
     * @return unmodifiable collection of option names supported by the manager
     */
    Collection<String> getOptionNames();

    /**
     * Return the options for the requested option name.
     *
     * @param optionName option name for which to get the available values
     * @return unmodifiable option values associated with the option name
     */
    Collection<?> getOptions(String optionName);

    /**
     * Returns typed options for a requested option name.
     * 
     * @param <T> option type
     * @param optionName name of he option
     * @param optionType option value type
     * @return unmodifiable collection of values associated with the option name
     */
    <T> Collection<T> getOptions(String optionName, Class<T> optionType);

    /**
     * Adds an option for the requested option name.
     * 
     * @param <T>
     * @param optionName
     * @param value
     * @return 
     */
    <T> T addOption(String optionName, T value);
}
