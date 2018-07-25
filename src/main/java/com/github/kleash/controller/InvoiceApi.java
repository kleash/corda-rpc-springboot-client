package com.github.kleash.controller;

import com.github.kleash.config.NodeRPCConnection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.NodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

// This API is accessible from /api/example. All paths specified below are relative to it.
@RestController
public class InvoiceApi {

    private final List<String> serviceNames = ImmutableList.of("Notary", "Network Map Service");
    @Autowired
    private NodeRPCConnection nodeRPCConnection;

    private CordaRPCOps rpcOps;
    private CordaX500Name myLegalName;

    @PostConstruct
    public void init() {
        this.rpcOps = nodeRPCConnection.getProxy();
        this.myLegalName = rpcOps.nodeInfo().getLegalIdentities().get(0).getName();
    }

    /**
     * Returns the node's name.
     */


    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json")
    public Map<String, CordaX500Name> whoami() {
        return ImmutableMap.of("me", myLegalName);
    }

    /**
     * Returns all parties registered with the [NetworkMapService]. These names can be used to look up identities
     * using the [IdentityService].
     */

    @RequestMapping(value = "/peers", method = RequestMethod.GET, produces = "application/json")
    public Map<String, List<CordaX500Name>> getPeers() {
        List<NodeInfo> nodeInfoSnapshot = rpcOps.networkMapSnapshot();
        return ImmutableMap.of("peers", nodeInfoSnapshot
                .stream()
                .map(node -> node.getLegalIdentities().get(0).getName())
                .filter(name -> !name.equals(myLegalName) && !serviceNames.contains(name.getOrganisation()))
                .collect(toList()));
    }

}