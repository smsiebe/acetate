package org.geoint.acetate.bind.impl;

import org.geoint.acetate.bind.ComponentOptions;
import org.geoint.acetate.data.DataConverter;
import org.geoint.acetate.data.DataFormatter;

/**
 *
 */
public class ComponentOptionsImpl implements ComponentOptions {

    private ComponentOptionsImpl() {
    }

    public static ComponentOptionsBuilder builder() {
        return new ComponentOptionsBuilderImpl();
    }

    public static class ComponentOptionsBuilderImpl
            implements ComponentOptionsBuilder {

        private ComponentOptionsBuilderImpl() {
        }

        @Override
        public ComponentOptions readString(DataFormatter<String> formatter) {

        }

        @Override
        public ComponentOptions readBytes(DataFormatter<byte[]> formatter) {

        }

        @Override
        public <F, T> ComponentOptions readConverter(DataConverter<F, T> converter) {

        }

        @Override
        public ComponentOptions writeString(DataFormatter<String> formatter) {

        }

        @Override
        public ComponentOptions writeBytes(DataFormatter<byte[]> formatter) {

        }

        @Override
        public <F, T> ComponentOptions writeConverter(DataConverter<F, T> converter) {

        }

        @Override
        public ComponentOptions build() {

        }

    }
}
