package com.wjs.server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import static com.wjs.common.constant.DefaultValues.SERVER_DEFAULT_PORT;
import static com.wjs.common.constant.DefaultValues.SERVER_NODE;
/**
 * 参数解析
 *
 * @author wenjs
 */
public class ParameterParser {
    private static final String PROGRAM_NAME
            = "sh console-server.sh(for linux and mac) or cmd console-server.bat(for windows)";
    @Parameter(names = "--help", help = true)
    private boolean help;
    @Parameter(names = {"--host", "-h"}, description = "The ip to register to registry center.", order = 1)
    private String host;
    @Parameter(names = {"--port", "-p"}, description = "The port to listen.", order = 2)
    private int port = SERVER_DEFAULT_PORT;

    // @Parameter(names = {"--storeMode", "-m"}, description = "log store mode : file, db", order = 3)
    // private String storeMode;
    @Parameter(names = {"--serverNode", "-n"}, description = "server node id, such as 1, 2, 3.it will be generated according to the snowflake by default", order = 4)
    private Long serverNode = SERVER_NODE;
    // @Parameter(names = {"--seataEnv", "-e"}, description = "The name used for multi-configuration isolation.",
    //         order = 5)
    // private String seataEnv;

    /**
     * Instantiates a new Parameter parser.
     *
     * @param args the args
     */
    public ParameterParser(String[] args) {
        this.init(args);
    }

    private void init(String[] args) {
        try {

            JCommander jCommander = JCommander.newBuilder().addObject(this).build();
            jCommander.parse(args);
            if (help) {
                jCommander.setProgramName(PROGRAM_NAME);
                jCommander.usage();
                System.exit(0);
            }

            // if ( com.wjs.core.util.StringUtils.isNotBlank(seataEnv)) {
            //     System.setProperty(ENV_PROPERTY_KEY, seataEnv);
            // }
            // if ( com.wjs.core.util.StringUtils.isBlank(storeMode)) {
            //     storeMode = ConfigurationFactory.getInstance().getConfig(ConfigurationKeys.STORE_MODE,
            //             SERVER_DEFAULT_STORE_MODE);
            // }
        } catch (ParameterException e) {
            printError(e);
        }

    }

    private void printError(ParameterException e) {
        System.err.println("Option error " + e.getMessage());
        e.getJCommander().setProgramName(PROGRAM_NAME);
        e.usage();
        System.exit(0);
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets store mode.
     *
     * @return the store mode
     */
    // public String getStoreMode() {
    //     return storeMode;
    // }

    /**
     * Is help boolean.
     *
     * @return the boolean
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Gets server node.
     *
     * @return the server node
     */
    public Long getServerNode() {
        return serverNode;
    }

    /**
     * Gets seata env
     *
     * @return the name used for multi-configuration isolation.
     */
    // public String getSeataEnv() {
    //     return seataEnv;
    // }

}
