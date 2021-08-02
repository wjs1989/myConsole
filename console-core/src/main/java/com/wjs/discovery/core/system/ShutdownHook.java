package com.wjs.discovery.core.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenjs
 */
public class ShutdownHook extends Thread {
    final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    static class ShutdownHookSingle {
        public static ShutdownHook shutdownHook = new ShutdownHook();
    }

    public static ShutdownHook getShutdownHook() {
        return ShutdownHookSingle.shutdownHook;
    }


    @Override
    public void run() {
        logger.info("Shutdown……");
    }


    static {
        Runtime.getRuntime().addShutdownHook(getShutdownHook());
    }
}
