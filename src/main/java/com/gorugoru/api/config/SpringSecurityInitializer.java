package com.gorugoru.api.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//it will load the springSecurityFilterChain automatically.
@Order(1)
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
