package org.jboss.eap.quickstarts.kitchensink.service.impl;

import jakarta.validation.ValidationException;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.repository.IMemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements IValidationService {

    @Autowired
    IMemberRepository memberRepository;

    @Override
    public boolean emailAlreadyExists(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null;
    }

    @Override
    public void validateMember(CreateMemberRequest createMemberRequest) {
        if (emailAlreadyExists(createMemberRequest.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }
}
