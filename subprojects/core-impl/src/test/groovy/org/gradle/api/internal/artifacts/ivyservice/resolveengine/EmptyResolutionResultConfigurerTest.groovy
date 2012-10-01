/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.internal.artifacts.ivyservice.resolveengine

import org.gradle.api.internal.artifacts.ArtifactDependencyResolver
import org.gradle.api.internal.artifacts.DefaultModule
import org.gradle.api.internal.artifacts.configurations.ConfigurationInternal
import spock.lang.Specification

import static org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier.newId

/**
 * by Szczepan Faber, created at: 9/10/12
 */
class EmptyResolutionResultConfigurerTest extends Specification {

    def "configures the resolution result before delegating"() {
        def delegate = Mock(ArtifactDependencyResolver)
        def configurer = new EmptyResolutionResultConfigurer(delegate)
        def conf = Mock(ConfigurationInternal)
        conf.getModule() >> new DefaultModule("foo", "bar", "1.0")

        when:
        configurer.resolve(conf)

        then:
        1 * conf.setResolutionResult({ it.root.id == newId('foo', 'bar', '1.0') && it.root.dependencies.empty && it.root.dependents.empty })

        then:
        1 * delegate.resolve(conf)
    }
}
