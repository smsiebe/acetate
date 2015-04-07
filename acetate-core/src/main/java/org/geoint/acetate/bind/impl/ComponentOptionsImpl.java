
package org.geoint.acetate.bind.impl;

import java.util.LinkedList;
import org.geoint.acetate.bind.ComponentOptions;
import org.geoint.acetate.bind.spi.BinaryDataFormatter;
import org.geoint.acetate.bind.spi.DataConverter;
import org.geoint.acetate.bind.spi.StringDataFormatter;

/**
 *
 */
public class ComponentOptionsImpl implements ComponentOptions {

        /**
         * includes only user-provided steps...still need to do the read from
         * the source data binder
         */
        private final LinkedList<BindingStep> readSteps = new LinkedList<>();
        /**
         * includes on the user-provided steps...still need to do the write to
         * the destination data binder
         */
        private final LinkedList<BindingStep> writeSteps = new LinkedList<>();

        @Override
        public <T> ComponentOptions read(StringDataFormatter<T> formatter) {
            try {
                wl.lock();
                readSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions read(BinaryDataFormatter<T> formatter) {
            try {
                wl.lock();
                readSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <F, T> ComponentOptions read(DataConverter<F, T> converter) {
            try {
                wl.lock();
                readSteps.add(converter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions write(StringDataFormatter<T> formatter) {
            try {
                wl.lock();
                writeSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions write(BinaryDataFormatter<T> formatter) {
            try {
                wl.lock();
                writeSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <F, T> ComponentOptions write(DataConverter<F, T> converter) {
            try {
                wl.lock();
                writeSteps.add(converter);
            } finally {
                wl.unlock();
            }
            return this;
        }
    }
