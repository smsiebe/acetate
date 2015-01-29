/**
 * Data binder following "standard" POJO rules:
 * <ul>
 * <li>No-argument public constructor</li>
 * <li>All bindable fields have an accessor method that is named either
 * is{FieldName} for fields with a boolean value or get{FieldName} for any value
 * type.</li>
 * <li>All bindable fields have a mutator method that is name
 * set{FieldName}.</li>
 * </ul>
 */
package org.geoint.acetate.impl.bind.pojo;
