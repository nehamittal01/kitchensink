package org.jboss.eap.quickstarts.kitchensink.converter;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateMemberRequestToMember implements Converter<CreateMemberRequest, Member> {
    @Override
    public Member convert(CreateMemberRequest source) {
        return Member.builder()
                .name(source.getName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
