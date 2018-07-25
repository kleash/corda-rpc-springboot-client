package com.github.kleash.props;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class CordaProperties {

    @Value("${corda.node.rpc.host}")
    private String rpcHost;
    @Value("${corda.node.rpc.user}")
    private String rpcUser;
    @Value("${corda.node.rpc.pass}")
    private String rpcPass;
    @Value("${corda.node.rpc.port}")
    private int rpcPort;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getRpcHost() {
        return rpcHost;
    }

    public void setRpcHost(String rpcHost) {
        this.rpcHost = rpcHost;
    }

    public String getRpcUser() {
        return rpcUser;
    }

    public void setRpcUser(String rpcUser) {
        this.rpcUser = rpcUser;
    }

    public String getRpcPass() {
        return rpcPass;
    }

    public void setRpcPass(String rpcPass) {
        this.rpcPass = rpcPass;
    }

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    @Override
    public String toString() {
        return "CordaProperties{" +
                "rpcHost='" + rpcHost + '\'' +
                ", rpcUser='" + rpcUser + '\'' +
                ", rpcPass='" + rpcPass + '\'' +
                ", rpcPort='" + rpcPort + '\'' +
                '}';
    }
}
