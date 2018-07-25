package com.github.kleash.config;

import com.github.kleash.props.CordaProperties;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NodeRPCConnection {

    @Autowired
    private CordaProperties cordaProperties;

    private CordaRPCOps proxy;
    private CordaRPCConnection rpcConnection;

    @PostConstruct
    private void init() {
        NetworkHostAndPort networkHostAndPort = new NetworkHostAndPort(cordaProperties.getRpcHost(), cordaProperties.getRpcPort());
        CordaRPCClient cordaRPCClient = new CordaRPCClient(networkHostAndPort);
        rpcConnection = cordaRPCClient.start(cordaProperties.getRpcUser(), cordaProperties.getRpcPass());
        proxy = rpcConnection.getProxy();
    }


    @PreDestroy
    public void shutdown() {
        rpcConnection.notifyServerAndClose();
    }

    public CordaRPCOps getProxy() {
        return proxy;
    }
}
