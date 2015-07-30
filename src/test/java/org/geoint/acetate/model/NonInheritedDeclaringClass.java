package org.geoint.acetate.model;

/**
 * Mock class which locally declares the non-inherited model annotation used for
 * testing.
 */
@NonInheritedModelAnnotation
public class NonInheritedDeclaringClass {

    @NonInheritedModelAnnotation
    private int value;

    @NonInheritedModelAnnotation
    public synchronized void increment() {
        value++;
    }

    public synchronized int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.value;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NonInheritedDeclaringClass other = (NonInheritedDeclaringClass) obj;
        return this.value == other.value;
    }

}
