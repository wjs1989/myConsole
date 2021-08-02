package com.wjs.discovery.register;

import com.wjs.discovery.core.ConfigurationCache;
import com.wjs.discovery.core.ConfigurationFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wenjs
 */
public interface RegistryService<T> {

    Set<String> SERVICE_GROUP_NAME = new HashSet<>();

    void register(InetSocketAddress address) throws Exception;

    void unregister(InetSocketAddress address) throws Exception;

    void subscribe(String cluster, T listener) throws Exception;

    void unsubscribe(String cluster, T listener) throws Exception;

    List<InetSocketAddress> lookup(String key) throws Exception;

    void close() throws Exception;

    default String getServiceGroup(String key) {
        //key = PREFIX_SERVICE_ROOT + CONFIG_SPLIT_CHAR + PREFIX_SERVICE_MAPPING + key;
        if (!SERVICE_GROUP_NAME.contains(key)) {
            ConfigurationCache.addConfigListener(key);
            SERVICE_GROUP_NAME.add(key);
        }
        return ConfigurationFactory.getInstance().getConfig(key);
    }
}
