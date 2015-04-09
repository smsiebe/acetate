/**
 * Data that has been "bound" to a data model.
 * <p>
 * Bound objects may be requested on demand but are not actually created during
 * a typical binding operation (object memory overhead reasons). If a bound
 * representation of the data isn't necessary, don't use it. As always,
 * stream-based operations are the way to go if you can.
 */
package org.geoint.acetate.bound;
