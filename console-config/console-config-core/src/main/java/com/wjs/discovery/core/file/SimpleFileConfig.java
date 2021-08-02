package com.wjs.discovery.core.file;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.wjs.common.loader.LoadLevel;
import com.wjs.common.loader.Scope;

import java.io.File;

/**
 * @author wenjs
 */
@LoadLevel(name = FileConfigFactory.DEFAULT_TYPE,scope = Scope.PROTOTYPE)
public class SimpleFileConfig implements FileConfig {

    private Config fileConfig;

    public SimpleFileConfig() {
        fileConfig = ConfigFactory.load();
    }

    public SimpleFileConfig(File file, String name) {
        if (name.startsWith(FileConfiguration.SYS_FILE_RESOURCE_PREFIX)) {
            Config appConfig = ConfigFactory.parseFileAnySyntax(file);
            fileConfig = ConfigFactory.load(appConfig);
        } else {
            fileConfig = ConfigFactory.load(file.getName());
        }
    }

    @Override
    public String getString(String path) {
        return fileConfig.getString(path);
    }

}
