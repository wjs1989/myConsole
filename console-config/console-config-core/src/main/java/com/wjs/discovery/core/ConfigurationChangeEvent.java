package com.wjs.discovery.core;

/**
 * @author wenjs
 */
public class ConfigurationChangeEvent {
    private String dataId;
    private String oldValue;
    private String newValue;
    private String namespace;
    private ConfigurationChangeType changeType;
    private static final String DEFAULT_NAMESPACE = "DEFAULT";


    public ConfigurationChangeEvent(){

    }

    public ConfigurationChangeEvent(String dataId, String newValue) {
        this(dataId, DEFAULT_NAMESPACE, null, newValue, ConfigurationChangeType.MODIFY);
    }

    public ConfigurationChangeEvent(String dataId, String namespace, String oldValue, String newValue,
                                    ConfigurationChangeType type) {
        this.dataId = dataId;
        this.namespace = namespace;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeType = type;
    }

    public String getDataId() {
        return dataId;
    }

    public ConfigurationChangeEvent setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public ConfigurationChangeEvent setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public ConfigurationChangeEvent setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public ConfigurationChangeType getChangeType() {
        return changeType;
    }

    public ConfigurationChangeEvent setChangeType(ConfigurationChangeType changeType) {
        this.changeType = changeType;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public ConfigurationChangeEvent setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
}
