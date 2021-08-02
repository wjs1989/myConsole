package com.wjs.discovery.register;

import com.wjs.common.constant.ConfigurationKeys;
import com.wjs.common.exception.NotSupportYetException;
import com.wjs.common.loader.EnhancedServiceLoader;
import com.wjs.discovery.core.ConfigurationFactory;

import java.util.Objects;

/**
 * @author wenjs
 */
public class RegistryFactory {

    private static volatile RegistryService instance = null;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RegistryService getInstance() {
        if (instance == null) {
            synchronized (RegistryFactory.class) {
                if (instance == null) {
                    instance = buildRegistryService();
                }
            }
        }
        return instance;
    }

    private static RegistryService buildRegistryService() {
        RegistryType registryType;
        String registryTypeName = ConfigurationFactory.CURRENT_FILE_INSTANCE.getConfig(
                ConfigurationKeys.FILE_ROOT_REGISTRY + ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR
                        + ConfigurationKeys.FILE_ROOT_TYPE);
        try {
            registryType = RegistryType.getType(registryTypeName);
        } catch (Exception exx) {
            throw new NotSupportYetException("not support registry type: " + registryTypeName);
        }
        // if (RegistryType.File == registryType) {
        //     return FileRegistryServiceImpl.getInstance();
        // } else {
        return EnhancedServiceLoader.load(RegistryProvider.class, Objects.requireNonNull(registryType).name()).provide();
        //  }
    }
}
