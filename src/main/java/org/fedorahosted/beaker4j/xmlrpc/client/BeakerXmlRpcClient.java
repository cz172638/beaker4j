package org.fedorahosted.beaker4j.xmlrpc.client;

import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.fedorahosted.beaker4j.client.BeakerClient;
import org.fedorahosted.beaker4j.remote_model.BeakerJob;


public class BeakerXmlRpcClient implements BeakerClient {
    
    private final XmlRpcClient client;

    public BeakerXmlRpcClient(XmlRpcClient client) {
        this.client = client;
    }

    public boolean authenticate(String login, String password) {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, ?> o = (HashMap<String, ?>) execute(XmlRpcApi.AUTH_LOGIN_PASSWORD, new Object[] {login, password});
        } catch(XmlRpcException e){
            return false;
        }
        return true;
    }
    
    @Override
    public BeakerJob scheduleJob(String jobXml) throws XmlRpcException {
        Object[] params = new Object[] { jobXml };
        BeakerJob job = null;
        try {
            String jobId = (String)execute(XmlRpcApi.JOBS_UPLOAD, params);
            job = new BeakerJob(jobId, this);
        } catch(XmlRpcException e) {
            //TODO log exception properly
            e.printStackTrace();
        }
        return job;
    }
    
    @Override
    public Object execute(String cmd, Object[] params) throws XmlRpcException {
        return doRPCall(cmd, params);
    }
    
    @Override
    public Object execute(XmlRpcApi rpcApi, Object[] params) throws XmlRpcException {
        return doRPCall(rpcApi, params);
    }

    private Object doRPCall(XmlRpcApi rpcApi, Object[] params) throws XmlRpcException {
        return doRPCall(rpcApi.getRpc(), params);
    }
    
    private Object doRPCall(String rpc, Object[] params) throws XmlRpcException {
        Object result = null;
        //try {
            synchronized (client) { // only one thread and/or method is using this connection manager at a time
                result = client.execute(rpc, params);
            }
        /*} catch (XmlRpcException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }*/
        return result;
    }
}
