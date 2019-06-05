package com.gao.it.drm.usf.route;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import reactor.core.publisher.Flux;

public class DiscoveryClientRouteDefinitionLocator  implements RouteDefinitionLocator {

    private final DiscoveryClient discoveryClient;

    private final String prefix;

    public DiscoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;

        this.prefix = this.getClass().getSimpleName() + "_";
    }


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        SimpleEvaluationContext evalCtxt = SimpleEvaluationContext.forReadOnlyDataBinding()
                .withInstanceMethods()
                .build();

        return Flux.fromIterable(discoveryClient.getServices())
                .map(discoveryClient::getInstances)
                .filter(instances -> !instances.isEmpty())
                .map(instances -> instances.get(0))
                .filter();
    }
}
