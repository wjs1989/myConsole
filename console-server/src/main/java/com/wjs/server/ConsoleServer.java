package com.wjs.server;

import com.wjs.common.constant.Constants;
import com.wjs.common.util.NetUtil;
import com.wjs.common.util.UUIDGenerator;
import com.wjs.discovery.core.config.NettyBaseConfig;
import com.wjs.discovery.core.system.ShutdownHook;
import com.wjs.discovery.register.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author wenjs
 * 服务启动类
 */
public class ConsoleServer {

    public static void main(String[] args) throws Exception {
        ParameterParser parameterParser = new ParameterParser(args);

        String host = null;
        int port = parameterParser.getPort();
        System.setProperty(Constants.SERVER_PORT, Integer.toString(port));

        final Logger logger = LoggerFactory.getLogger(ConsoleServer.class);
        logger.info("服务开始启动……");

        if (NetUtil.isValidIp(parameterParser.getHost(), false)) {
            host = parameterParser.getHost();
        } else {
            host = NetUtil.getLocalIp();
        }

        System.out.println(NettyBaseConfig.CONFIG.getConfig("registry.type"));

        logger.info("host : " + host + ":" + port);

        String seataConfigName = System.getProperty("ev");
        if (seataConfigName == null) {
            seataConfigName = System.getenv("ev");
        }
        logger.info("seataConfigName : " + seataConfigName );

        UUIDGenerator.init(parameterParser.getServerNode());

        //销毁服务
        ShutdownHook.getShutdownHook();

        RegistryFactory.getInstance().register(new InetSocketAddress(host, port));

        logger.info("服务启动成功……");
        try {
            System.in.read();
        } catch (IOException e) {
        }

        //退出
        System.exit(0);
    }

}
