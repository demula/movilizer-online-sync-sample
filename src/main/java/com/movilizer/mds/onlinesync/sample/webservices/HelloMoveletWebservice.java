package com.movilizer.mds.onlinesync.sample.webservices;


import com.movilitas.movilizer.v12.MovilizerOnlineCallbackRequest;
import com.movilitas.movilizer.v12.MovilizerOnlineDataContainerReply;
import com.movilitas.movilizer.v12.MovilizerOnlineResponse;
import com.movilitas.movilizer.v12.MovilizerOnlineWebServiceV12;
import com.movilizer.mds.onlinesync.sample.services.HelloUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface="com.movilitas.movilizer.v12.MovilizerOnlineWebServiceV12")
@Service
public class HelloMoveletWebservice implements MovilizerOnlineWebServiceV12 {

    @Autowired
    HelloUtilsService utils;

    @Override
    @WebMethod(operationName="MovilizerOnlineCallback")
    public MovilizerOnlineResponse movilizerOnlineCallback(MovilizerOnlineCallbackRequest movilizerOnlineCallbackRequest) {
        String name = utils.getNameFromRequest(movilizerOnlineCallbackRequest);
        MovilizerOnlineResponse response = new MovilizerOnlineResponse();
        MovilizerOnlineDataContainerReply reply = utils.createReplyFromName(name);
        response.getOnlineContainerReply().add(reply);
        return response;
    }

//    @WebMethod(exclude=true)
//    public void setUtils(HelloUtilsService utils) {
//        this.utils = utils;
//    }
}
