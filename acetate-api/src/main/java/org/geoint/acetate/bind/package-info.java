/**
 * BoundData is typically a transient object which is used by acetate to convert
 * data from the java object graph to a template-defined data stream and back.
 *
 * Bound Data can be useful to application as well, which is why it is exposed
 * publicly. One common usage of an application directly interfacing with
 * BoundData is to access field data present in a data source which may not be
 * defined in the template.
 */
package org.geoint.acetate.bind;
