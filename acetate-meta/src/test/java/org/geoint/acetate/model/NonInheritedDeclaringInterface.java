package org.geoint.acetate.model;

/**
 * Mock interface which locally declares the non-inherited model annotation used
 * for testing.
 */
@NonInheritedModelAnnotation
public interface NonInheritedDeclaringInterface {

    int getValue();
    
    @NonInheritedModelAnnotation
    void increment();
}
