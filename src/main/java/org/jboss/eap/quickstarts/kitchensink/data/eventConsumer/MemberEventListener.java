package org.jboss.eap.quickstarts.kitchensink.data.eventConsumer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.customEvent.AddMemberEvent;
import org.jboss.eap.quickstarts.kitchensink.repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.logging.Logger;

@Configuration
@Slf4j
public class MemberEventListener implements ApplicationListener<AddMemberEvent> {

    @Autowired
    private IMemberRepository memberRepository;

    private List<Member> members;

    @Bean("memberData")
    public List<Member> getMembers() {
        return members;
    }

    @Override
    public void onApplicationEvent(AddMemberEvent addMemberEvent) {
    }

    @EventListener({AddMemberEvent.class,})
    void onMemberListChanged(AddMemberEvent event) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        members = memberRepository.findAllByOrderByNameAsc();
    }
}

