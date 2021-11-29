package com.xia.yuauth.config;

import com.xia.yuauth.domain.generation.IdGeneration;
import com.xia.yuauth.domain.model.entity.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

import java.time.LocalDateTime;

/**
 * description:
 *
 * @author Administrator
 * @date 2021/11/29 13:45
 */
@Configuration
public class DataJdbcConfiguration extends AbstractJdbcConfiguration {

	@Bean
	public BeforeSaveCallback<BaseEntity> beforeSaveCallback() {
		return (entity, aggregateChange) -> {
			if (entity.getId() == null) {
				entity.setId(IdGeneration.getId());
			}
			entity.setUpdateTime(LocalDateTime.now());

			if (entity.getCreateTime() == null) {
				entity.setUpdateTime(LocalDateTime.now());
			}
			return entity;
		};
	}
}