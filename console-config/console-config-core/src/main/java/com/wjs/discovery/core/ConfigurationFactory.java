package com.wjs.discovery.core;


import com.wjs.common.constant.ConfigurationKeys;
import com.wjs.common.exception.NotSupportYetException;
import com.wjs.common.loader.EnhancedServiceNotFoundException;
import com.wjs.common.util.StringUtils;
import com.wjs.discovery.core.file.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenjs
 */
public class ConfigurationFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);

    //默认的配置文件名称
    private static final String REGISTRY_CONF_DEFAULT = "registry";
    private static final String SYSTEM_PROPERTY_SEATA_CONFIG_NAME = "seata.config.name";
    private static final String ENV_SEATA_CONFIG_NAME = "SEATA_CONFIG_NAME";

    private static final String ENV_SYSTEM_KEY = "SEATA_ENV";
    public static final String ENV_PROPERTY_KEY = "seataEnv";

    private static final String NAME_KEY = "name";
    private static final String FILE_TYPE = "file";

    //项目启动时，加载本地配置文件
    public static Configuration CURRENT_FILE_INSTANCE;

    static {
        load();
    }

    private static void load() {
        String seataConfigName = null;
        if ((seataConfigName = System.getProperty(SYSTEM_PROPERTY_SEATA_CONFIG_NAME)) == null
                || (seataConfigName = System.getenv(ENV_SEATA_CONFIG_NAME)) == null) {
            seataConfigName = REGISTRY_CONF_DEFAULT;
            System.setProperty(SYSTEM_PROPERTY_SEATA_CONFIG_NAME,seataConfigName);
        }

        String envValue = null;
        if ((envValue = System.getProperty(ENV_SYSTEM_KEY)) == null
                || (envValue = System.getenv(ENV_PROPERTY_KEY)) == null) {
        }


        CURRENT_FILE_INSTANCE = (envValue == null) ? new FileConfiguration(seataConfigName,
                false) : new FileConfiguration(seataConfigName + "-" + envValue, false);
    }

    private static Configuration instance = null;

    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = buildConfiguration();
                }
            }
        }
        return instance;
    }

    /**
     * 创建 Configuration
     *
     * @return
     */
    private static Configuration buildConfiguration() {

        String configTypeName = CURRENT_FILE_INSTANCE.getConfig(
                ConfigurationKeys.FILE_ROOT_CONFIG + ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR
                        + ConfigurationKeys.FILE_ROOT_TYPE);

        if (StringUtils.isBlank(configTypeName)) {
            throw new NotSupportYetException("config type can not be null");
        }
        ConfigType configType = ConfigType.getType(configTypeName);

        Configuration configuration = null;
        if (ConfigType.File == configType) {
            String pathDataId = String.join(ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR,
                    ConfigurationKeys.FILE_ROOT_CONFIG, FILE_TYPE, NAME_KEY);
            String name = CURRENT_FILE_INSTANCE.getConfig(pathDataId);
            configuration = new FileConfiguration(name);
        }

        try {
            configuration = ConfigurationCache.getInstance().proxy(configuration);

        } catch (EnhancedServiceNotFoundException ignore) {
        } catch (Exception e) {
            logger.error("failed to load configurationCacheProvider:{}", e.getMessage(), e);
        }
        return configuration;
    }

    protected static void reload() {
        ConfigurationCache.getInstance().clear();
        load();
        instance = null;
        getInstance();
    }
}
