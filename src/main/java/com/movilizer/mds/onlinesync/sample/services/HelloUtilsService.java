package com.movilizer.mds.onlinesync.sample.services;

import com.movilitas.movilizer.v12.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloUtilsService {

    public static final String helloContainerKey = "helloKey";
    public static final String helloContainerNameVarName = "username";
    public static final String helloContainerTextVarName = "text";
    public static final String helloContainerTextVarValue = "Hello %s from the server!!";

    public String getNameFromRequest(MovilizerOnlineCallbackRequest request) {
        List<MovilizerOnlineDataContainer> inboundContainers = request.getOnlineContainer();
        String name = "";
        if (!inboundContainers.isEmpty()) {
            List<MovilizerGenericDataContainerEntry> dataContainerEntries = inboundContainers.get(0).getContainer().getData().getEntry();
            if (!dataContainerEntries.isEmpty()) {
                MovilizerGenericDataContainerEntry data = dataContainerEntries.get(0);
                if (helloContainerNameVarName.equals(data.getName())) {
                    name = data.getValstr();
                }
            }
        }
        return name;
    }

    public MovilizerOnlineDataContainerReply createReplyFromName(String name) {
        MovilizerOnlineDataContainerReply reply = new MovilizerOnlineDataContainerReply();
        MovilizerGenericDataContainer dataContainer = new MovilizerGenericDataContainer();
        MovilizerGenericDataContainerEntry data = new MovilizerGenericDataContainerEntry();
        data.setName(helloContainerTextVarName);
        data.setValstr(String.format(helloContainerTextVarValue, name));
        dataContainer.getEntry().add(data);
        reply.setKey(helloContainerKey);
        reply.setData(dataContainer);
        return reply;
    }
}
