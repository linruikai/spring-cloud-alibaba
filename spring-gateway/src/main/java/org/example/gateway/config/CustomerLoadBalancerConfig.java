package org.example.gateway.config;

import com.alibaba.cloud.nacos.NacosServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * 配置自定义负载均衡，实现根据请求头和nacos group匹配动态路由
 *
 * 基线服务：nacos默认分组 -> group=DEFAULT_GROUP
 * spring.cloud.nacos.discovery.group=DEFAULT_GROUP(默认)
 *
 * 特性分支服务：nacos自定义分组 -> group=gray
 * spring.cloud.nacos.discovery.group=gray
 *
 * 基线服务[稳定环境]                   A  ->  B ->  C
 *                                     \        /
 *                                      \      /
 *            header[version:gray]       \    /
 *                                        \  /
 *                                         \/
 * 特性服务[开发环境，可以有多套]               B`
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = CustomerLoadBalancerConfig.class)
public class CustomerLoadBalancerConfig {

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomLoadBalancerConfiguration(loadBalancerClientFactory.getLazyProvider(name,
                ServiceInstanceListSupplier.class), name,nacosServiceManager);
    }
}
