package com.movilizer.mds.onlinesync.sample.enpoints;


import com.movilitas.movilizer.v12.MovilizerOnlineCallbackRequest;
import com.movilitas.movilizer.v12.MovilizerOnlineDataContainerReply;
import com.movilitas.movilizer.v12.MovilizerOnlineResponse;
import com.movilizer.mds.onlinesync.sample.services.HelloUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloMoveletEndpoint {
    public static final String NAMESPACE_URI = "http://movilitas.com/movilizer/v12";
    public static final String ONLINE_SYNC_LOCAL_NAME = "MovilizerOnlineCallbackRequest";

    @Autowired
    HelloUtilsService utils;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = ONLINE_SYNC_LOCAL_NAME)
    @ResponsePayload
    public MovilizerOnlineResponse movilizerOnlineCallback(@RequestPayload MovilizerOnlineCallbackRequest request) {
        String name = utils.getNameFromRequest(request);
        MovilizerOnlineResponse response = new MovilizerOnlineResponse();
        MovilizerOnlineDataContainerReply reply = utils.createReplyFromName(name);
        response.getOnlineContainerReply().add(reply);
        return response;
    }


}
