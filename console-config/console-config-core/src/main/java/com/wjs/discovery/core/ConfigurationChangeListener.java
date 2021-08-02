package com.wjs.discovery.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.wjs.common.thread.NamedThreadFactory;
/**
 * @author wenjs
 */
public interface ConfigurationChangeListener {

    /**
     * 默认核心线程数
     */
    int CORE_LISTENER_THREAD = 1;
    /**
     * 默认最大线程数
     */
    int MAX_LISTENER_THREAD = 1;

    /**
     * 配置变更执行线程池
     */
    ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            CORE_LISTENER_THREAD,
            MAX_LISTENER_THREAD,
            Integer.MAX_VALUE,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory(
                    "configurationChangeListener",
                    MAX_LISTENER_THREAD
            ));

    /**
     * Process.
     *
     * @param event the event
     */
    void onChangeEvent(ConfigurationChangeEvent event);

    /**
     * On process event.
     *
     * @param event the event
     */
    default void onProcessEvent(ConfigurationChangeEvent event) {
        getExecutorService().submit(() -> {
            beforeEvent();
            onChangeEvent(event);
            afterEvent();
        });
    }

    /**
     * On shut down.
     */
    default void onShutDown() {
        getExecutorService().shutdownNow();
    }

    /**
     * Gets executor service.
     *
     * @return the executor service
     */
    default ExecutorService getExecutorService() {
        return EXECUTOR_SERVICE;
    }

    /**
     * Before event.
     */
    default void beforeEvent() {

    }

    /**
     * After event.
     */
    default void afterEvent() {

    }
}
