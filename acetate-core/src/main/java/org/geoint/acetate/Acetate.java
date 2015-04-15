package org.geoint.acetate;

import java.io.InputStream;
import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.DataReader;
import org.geoint.acetate.bind.DataWriter;
import org.geoint.acetate.bind.TemplatedDataWriter;
import org.geoint.acetate.bind.template.DataTemplate;
import org.geoint.acetate.model.DataModel;

/**
 *
 *
 */
public final class Acetate {

    public static <T> T read(DataTemplate template, InputStream data,
            Class<T> type) throws DataBindException {

    }

    public static <T> T read(DataReader reader, Class<T> type)
            throws DataBindException {

    }

    public static <T> T read(DataReader reader, DataModel<T> model)
            throws DataBindException {

    }

    public static void write(DataWriter writer, Object data)
            throws DataBindException {

    }

    public static <T> void write(DataWriter writer, DataModel<T> model, T data)
            throws DataBindException {

    }

    public static void write(TemplatedDataWriter writer, Object data) {

    }
}
