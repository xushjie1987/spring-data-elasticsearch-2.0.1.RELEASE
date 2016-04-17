/*
 * Copyright 2013-2014 the original author or authors.
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
package org.springframework.data.elasticsearch.repository.support;

import java.io.Serializable;

import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.util.Assert;

/**
 * ElasticsearchEntityInformationCreatorImpl
 *
 * @author Rizwan Idrees
 * @author Mohsin Husen
 * @author Oliver Gierke
 */
public class ElasticsearchEntityInformationCreatorImpl implements ElasticsearchEntityInformationCreator {

	private final MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext;

	public ElasticsearchEntityInformationCreatorImpl(
			MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
		Assert.notNull(mappingContext);
		this.mappingContext = mappingContext;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, ID extends Serializable> ElasticsearchEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {

		ElasticsearchPersistentEntity<T> persistentEntity = (ElasticsearchPersistentEntity<T>) mappingContext
				.getPersistentEntity(domainClass);

		Assert.notNull(persistentEntity, String.format("Unable to obtain mapping metadata for %s!", domainClass));
		Assert.notNull(persistentEntity.getIdProperty(), String.format("No id property found for %s!", domainClass));

		return new MappingElasticsearchEntityInformation<T, ID>(persistentEntity);
	}
}
