package org.jboss.eap.quickstarts.kitchensink.converter;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.response.GetMemberResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberToGetMemberResponse implements Converter<Member, GetMemberResponse> {
    @Override
    public GetMemberResponse convert(Member source) {
        return GetMemberResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
