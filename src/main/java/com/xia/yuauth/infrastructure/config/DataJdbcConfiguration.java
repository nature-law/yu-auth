package com.xia.yuauth.infrastructure.config;

import com.xia.yuauth.common.enums.converter.ReadingConverterFactory;
import com.xia.yuauth.common.enums.converter.WritingConverterFactory;
import com.xia.yuauth.domain.generation.IdGeneration;
import com.xia.yuauth.domain.model.entity.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * description:
 *
 * @author Administrator
 * @date 2021/11/29 13:45
 */
@Configuration
@EnableJdbcAuditing

public class DataJdbcConfiguration extends AbstractJdbcConfiguration {


    @Override
    public JdbcCustomConversions jdbcCustomConversions() {

        return new JdbcCustomConversions(Arrays.asList(new ReadingConverterFactory(), new WritingConverterFactory()));
    }

    @Bean
    public BeforeSaveCallback<BaseEntity> beforeSaveCallback() {
        return (entity, aggregateChange) -> {
            if (entity.getId() == null) {
                entity.setId(IdGeneration.getId());
            }
            entity.setUpdateTime(LocalDateTime.now());

            if (entity.getCreateTime() == null) {
                entity.setCreateTime(LocalDateTime.now());
            }

            return entity;
        };
    }
}