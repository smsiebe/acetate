package org.geoint.acetate.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class which realizes the {@link NonInheritedDeclaringInterface}, used for
 * testing.
 */
public class NonInheritedRealizedClass implements NonInheritedDeclaringInterface {

    private AtomicInteger value;

    @Override
    public int getValue() {
        return value.get();
    }

    @Override
    public void increment() {
        value.incrementAndGet();
    }

}
