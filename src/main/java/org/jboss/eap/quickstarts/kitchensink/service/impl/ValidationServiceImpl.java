package org.jboss.eap.quickstarts.kitchensink.service.impl;

import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.repository.IMemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class ValidationServiceImpl implements IValidationService {

    @Autowired
    IMemberRepository memberRepository;

    @Autowired
    private Validator validator;

    @Override
    public boolean emailAlreadyExists(String email) {
        try{
            Member member=memberRepository.findByEmail(email);
        }catch (NoResultException exception){
            log.info("Exception occurred inside emailAlreadyExists",exception);
        }

        return member != null;
    }

    @Override
    public void validateMember(CreateMemberRequest createMemberRequest) {
        Set<ConstraintViolation<CreateMemberRequest>> violations = validator.validate(createMemberRequest);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }

        if (emailAlreadyExists(createMemberRequest.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }
}
