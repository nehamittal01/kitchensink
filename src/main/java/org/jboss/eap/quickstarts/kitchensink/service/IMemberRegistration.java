package org.jboss.eap.quickstarts.kitchensink.service;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.model.response.GetMemberResponse;

import java.util.List;

public interface IMemberRegistration {
    void register(CreateMemberRequest createMemberRequest) throws Exception;

    GetMemberResponse findMemberById(String id);

    List<GetMemberResponse> getAllMemberOrderedByName();
}
