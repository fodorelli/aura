/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.impl.factory;

import java.util.Map;

import javax.inject.Inject;

import org.auraframework.annotations.Annotations.ServiceComponent;
import org.auraframework.def.DefDescriptor;
import org.auraframework.def.LibraryDef;
import org.auraframework.service.CompilerService;
import org.auraframework.system.BundleSource;
import org.auraframework.system.DefinitionFactory;
import org.auraframework.system.Source;
import org.auraframework.throwable.quickfix.QuickFixException;

@ServiceComponent
public class BundleLibraryDefFactory implements DefinitionFactory<BundleSource<LibraryDef>,LibraryDef> {

    @Inject
    private CompilerService compilerService;

    @Override
    public Class<?> getSourceInterface() {
        return BundleSource.class;
    }

    @Override
    public Class<LibraryDef> getDefinitionClass() {
        return LibraryDef.class;
    }

    @Override
    public String getMimeType() {
        return "";
    }

    @Override
    public LibraryDef getDefinition(DefDescriptor<LibraryDef> descriptor,
            BundleSource<LibraryDef> source) throws QuickFixException {
        Map<DefDescriptor<?>, Source<?>> sourceMap = source.getBundledParts();
        @SuppressWarnings("unchecked")
        Source<LibraryDef> componentSource = (Source<LibraryDef>)sourceMap.get(descriptor);
        LibraryDef def = compilerService.compile(descriptor, componentSource);
        return def;
    }
}