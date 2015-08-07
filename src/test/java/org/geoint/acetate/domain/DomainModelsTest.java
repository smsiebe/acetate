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
package org.geoint.acetate.domain;

import com.google.testing.compile.JavaFileObjects;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import java.io.File;
import java.net.URL;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import org.geoint.metamodel.descriptor.ModelDescriptor;
import org.geoint.metamodel.processor.MetaModelAnnotationProcessor;
import org.junit.Test;
import static org.truth0.Truth.ASSERT;

/**
 *
 * @author steve_siebert
 */
public class DomainModelsTest {

    @Test
    public void testServiceLoadedProviders() throws DomainModelException, Exception {

//        ASSERT.about(javaSource())
//                .that(sourceClass(StandardBoolean.class))
//                .processedWith(new MetaModelAnnotationProcessor())
//                .compilesWithoutError()
//                .and()
//                .generatesFileNamed(StandardLocation.CLASS_OUTPUT, "",
//                        ModelDescriptor.MODEL_DESCRIPTOR_JAR_PATH);
    }

    @Test
    public void testExplicitProviderLoading() {

    }

    private JavaFileObject sourceClass(Class<?> type) throws Exception {
        return loadClass("src/main/java/", type);
    }

    private JavaFileObject testClass(Class<?> type) throws Exception {
        return loadClass("src/test/java/", type);
    }

    private JavaFileObject loadClass(String relativePath, Class<?> type) throws Exception {
        URL sourceURL = this.getClass().getClassLoader().getResource("");
        File projectBaseDir = new File(sourceURL.toURI())
                .getParentFile().getParentFile();
        File testSourceDir = new File(projectBaseDir, relativePath + toSourcePath(type));
        return JavaFileObjects.forResource(testSourceDir.toURI().toURL());
    }

    private String toSourcePath(Class<?> type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getCanonicalName().replace('.', '/'))
                .append(".java");
        return sb.toString();
    }
}
