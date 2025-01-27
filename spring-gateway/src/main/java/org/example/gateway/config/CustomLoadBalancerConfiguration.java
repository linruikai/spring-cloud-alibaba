package org.example.gateway.config;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery.hostToServiceInstance;

public class CustomLoadBalancerConfiguration implements ReactorServiceInstanceLoadBalancer {

    public static final String X_GRAY_VERSION ="X-GRAY-VERSION";
    final String serviceId;
    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    final NacosServiceManager nacosServiceManager;

    public CustomLoadBalancerConfiguration(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                           String serviceId, NacosServiceManager nacosServiceManager) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.nacosServiceManager = nacosServiceManager;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances -> getInstanceResponse(request));
    }
    private Response<ServiceInstance> getInstanceResponse(Request request) {
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        String header = headers.getFirst(X_GRAY_VERSION);
        try {
            Instance instance;
            try {
                // 先获取指定header分支特性环境实例，如果header为null，则获取基线环境group=DEFAULT_GROUP的实例
                instance = nacosServiceManager.getNamingService().selectOneHealthyInstance(serviceId, Objects.isNull(header) ? Constants.DEFAULT_GROUP : header);
            }catch (IllegalStateException illegalStateException) {
                // 如果特性分支group没有，则获取基线环境group=DEFAULT_GROUP的实例
                instance = nacosServiceManager.getNamingService().selectOneHealthyInstance(serviceId, Constants.DEFAULT_GROUP);
            }
            ServiceInstance serviceInstance = hostToServiceInstance(instance, serviceId);
            return new DefaultResponse(serviceInstance);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}

