/**
 * Load and manage acetate plugins.
 * 
 * When no external plugins are found, acetate uses the default implementations 
 * (annotated with {@link DefaultPlugin}) provided by core.  If there are 
 * external plugins on the classpath, acetate will first defer to these and use 
 * the acetate plugins as a last resort.
 */
package org.geoint.acetate.impl.plugin;
