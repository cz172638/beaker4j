package com.github.vjuranek.beaker4j.beaker;

import com.github.vjuranek.beaker4j.client.BeakerClient;
import com.github.vjuranek.beaker4j.client.BeakerClientFactory;
import com.github.vjuranek.beaker4j.xmlrpc.client.BeakerXmlRpcClientFactory;

public class BeakerServer {
    
    private final String serverURL;
    
    public BeakerServer(String serverURL) {
        this.serverURL = serverURL;
    }
    
    public String getServerURL() {
        return serverURL;
    }
    
    public BeakerClient getXmlRpcClient() {
        BeakerClientFactory bcf = new BeakerXmlRpcClientFactory(getServerURL());
        return bcf.getClient();
    }
    
    public static BeakerClient getXmlRpcClient(String beakerServerURL) {
        return BeakerXmlRpcClientFactory.getClient(beakerServerURL);
    }

}
