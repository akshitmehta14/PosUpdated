package com.akshit.sale.config;

import com.akshit.sale.spring.SpringConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(//
		basePackages = { "com.akshit.sale" }, //
		excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringConfig.class })//
)
@PropertySources({ //
		@PropertySource(value = "file:./test.properties", ignoreResourceNotFound = true) //
})
public class QaConfig {

}
