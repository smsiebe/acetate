package org.geoint.acetate.bind;

/**
 *
 */
public interface DataBinder<R> {


    DataReader reader(R source);

    DataWriter writer();

}
