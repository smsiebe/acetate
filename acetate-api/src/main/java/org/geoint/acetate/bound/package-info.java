/**
 * Bound data is data that has been "stitched" to a data model.
 * <p>
 * BoundData is an optional intermediary object which can be retrieved to
 * introspect, inspect, manipulate, etc the binding operation programmatically.
 * Tough BoundData instances may be created during acetate operations
 * internally, it isn't guaranteed, especially for stream-based operations.
 * However, if requested, BoundData can/will be available. Given this, BoundData
 * should be requested by applications only if they actually need it, as
 * unnecessary calls my result in unnecessary memory usage.
 */
package org.geoint.acetate.bound;
