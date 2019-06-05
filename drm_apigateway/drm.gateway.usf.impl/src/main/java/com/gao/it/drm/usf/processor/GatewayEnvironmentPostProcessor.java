package com.gao.it.drm.usf.processor;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;


public class GatewayEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String CONFIGURE_UNKNOWN = "UNKNOWN";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {

        Map<String, Object> propertyMap = new HashMap<>();

        String appId = env.getProperty("app.appId", CONFIGURE_UNKNOWN);
        String subAppId = env.getProperty("app.subAppId", CONFIGURE_UNKNOWN);

        if (StringUtils.equals(appId, CONFIGURE_UNKNOWN) && StringUtils.equals(subAppId, CONFIGURE_UNKNOWN)) {
            return;
        } else {
            propertyMap.put("eureka.instance.appname", appId + ":" + subAppId);
            propertyMap.put("eureka.instance.metadataMap.group", env.getProperty("app.usf.environment"));

            env.getPropertySources().addFirst(new MapPropertySource("gateway-property", propertyMap));
        }
    }
}
