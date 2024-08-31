package org.jboss.eap.quickstarts.kitchensink.controler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.constants.Constants;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.model.response.GetMemberResponse;
import org.jboss.eap.quickstarts.kitchensink.service.IValidationService;
import org.jboss.eap.quickstarts.kitchensink.service.impl.MemberRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest")
@Slf4j
public class MemberController {

    @Autowired
    MemberRegistration registration;

    @Autowired
    private IValidationService validationService;

    @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetMemberResponse>> listAllMembers() {
        log.info("Starting listAllMembers execution");
        List<GetMemberResponse> getMemberResponseList = registration.getAllMemberOrderedByName();
        return ResponseEntity.ok().body(getMemberResponseList);
    }


    @GetMapping(value = "/members/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetMemberResponse> lookupMemberById(@PathVariable(name = Constants.ID) String id) {
        log.info("Starting lookupMemberById execution for id :{}", id);
        GetMemberResponse getMemberResponse = registration.findMemberById(id);
        if (null == getMemberResponse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(getMemberResponse);
    }


    @PostMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        log.info("Starting createMember execution for request :{}", createMemberRequest);
        try {
            validationService.validateMember(createMemberRequest);
            registration.register(createMemberRequest);
            return ResponseEntity.ok().build();
        } catch (ConstraintViolationException ce) {
            return ResponseEntity.badRequest().body(createViolationResponse(ce.getConstraintViolations()));
        } catch (ValidationException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put(Constants.EMAIL, "Email taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObj);
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put(Constants.ERROR, e.getMessage());
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    private Map<String, String> createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.info("Validation completed. violations found: {}", violations.size());
        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return responseObj;
    }

}
