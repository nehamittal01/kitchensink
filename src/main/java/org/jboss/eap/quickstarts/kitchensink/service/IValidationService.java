package org.jboss.eap.quickstarts.kitchensink.service;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;

public interface IValidationService {
    boolean emailAlreadyExists(String email);

    void validateMember(CreateMemberRequest createMemberRequest);
}
