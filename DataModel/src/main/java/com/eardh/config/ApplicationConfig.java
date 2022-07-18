package com.eardh.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.TimeZone;

/**
 * application配置
 * @author eardh
 * @date 2022/6/14 16:57
 */
@Configuration
public class ApplicationConfig {

    /**
     * 配置ObjectMapper,自定义序列化，去除属性前后空格
     * @return {@link Jackson2ObjectMapperBuilderCustomizer}
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("GMT+8"));
            jacksonObjectMapperBuilder.deserializerByType(String.class, new JsonDeserializer<String>() {
                @Override
                public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    return StringUtils.trimWhitespace(jsonParser.getValueAsString());
                }
            });
        };
    }
}