/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension.dependency.springkafka;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.start.site.container.DockerServiceResolver;
import io.spring.start.site.container.ServiceConnections.ServiceConnection;
import io.spring.start.site.container.ServiceConnectionsCustomizer;

import org.springframework.context.annotation.Bean;

/**
 * Configuration for generation of projects that depend on Spring Kafka.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("kafka")
class SpringKafkaProjectGenerationConfiguration {

	private static final String TESTCONTAINERS_CLASS_NAME = "org.testcontainers.containers.KafkaContainer";

	@Bean
	SpringKafkaBuildCustomizer springKafkaBuildCustomizer() {
		return new SpringKafkaBuildCustomizer();
	}

	@Bean
	@ConditionalOnRequestedDependency("testcontainers")
	ServiceConnectionsCustomizer kafkaServiceConnectionsCustomizer(DockerServiceResolver serviceResolver) {
		return (serviceConnections) -> serviceResolver.doWith("kafka", (service) -> serviceConnections
			.addServiceConnection(ServiceConnection.ofContainer("kafka", service, TESTCONTAINERS_CLASS_NAME, false)));
	}

}
