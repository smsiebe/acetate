/*
 * Copyright 2015 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.std.v1_0;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.geoint.acetate.annotation.Converts;
import org.geoint.acetate.annotation.Domain;
import org.geoint.acetate.annotation.DomainName;

/**
 * Immutable basic domain model for a {@link String}.
 *
 * @author steve_siebert
 */
@DomainName("string")
@Domain(name = "org.geoint.stddomain", version = "1.0")
public class StandardString extends BasicType<StandardString> {

    private final String value;

    @Converts
    public StandardString(String value) {
        this.value = value;
    }

    @Override
    public void write(OutputStream out, Charset charset) throws IOException {
        out.write(value.getBytes(charset));
    }

    @Converts
    public static StandardString valueOf(byte[] bytes, Charset charset) {
        return new StandardString(new String(bytes, charset));
    }
}
