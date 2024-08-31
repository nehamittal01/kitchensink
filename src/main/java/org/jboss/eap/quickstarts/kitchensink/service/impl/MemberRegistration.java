package org.jboss.eap.quickstarts.kitchensink.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.data.eventProducer.IMemberEventPublisher;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.customEvent.AddMemberEvent;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.model.response.GetMemberResponse;
import org.jboss.eap.quickstarts.kitchensink.repository.IMemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.IMemberRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberRegistration implements IMemberRegistration {

    @Autowired
    IMemberRepository memberRepository;

    @Autowired
    @Qualifier("kitchenSinkConversionService")
    private ConversionService conversionService;

    @Autowired
    private IMemberEventPublisher eventPublisher;

    @Autowired
    @Qualifier("memberData")
    private List<Member> membersList;

    @Override
    public void register(CreateMemberRequest createMemberRequest) throws Exception {
        log.info("Registering " + createMemberRequest.getName());
        Member member = conversionService.convert(createMemberRequest, Member.class);
        memberRepository.save(member);
        eventPublisher.publish(new AddMemberEvent(this, member));
    }

    @Override
    public GetMemberResponse findMemberById(String id) {
        log.info("Initialising findMemberById for id :{}", id);
        Member member = null;
        if (!CollectionUtils.isEmpty(membersList)) {
            member = membersList.stream()
                    .filter(m -> id.equals(m.getId()))
                    .findAny()
                    .orElse(null);
        }
        return conversionService.convert(member, GetMemberResponse.class);
    }

    @Override
    public List<GetMemberResponse> getAllMemberOrderedByName() {
        log.info("Initialising getAllMemberOrderedByName");
        List<GetMemberResponse> getMemberResponseList = null;

        if (!CollectionUtils.isEmpty(membersList)) {
            getMemberResponseList = membersList.stream()
                    .map(member -> conversionService.convert(member, GetMemberResponse.class))
                    .collect(Collectors.toList());
        }
        return getMemberResponseList;
    }
}

