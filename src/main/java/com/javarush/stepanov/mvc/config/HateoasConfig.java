package com.javarush.stepanov.mvc.config;

import com.javarush.stepanov.mvc.model.creator.Creator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

@Configuration
public class HateoasConfig {

    @Bean
    public PagedResourcesAssembler<Creator.Out> creatorOutPagedResourcesAssembler() {
        return new PagedResourcesAssembler<>(null, null);
    }
}
