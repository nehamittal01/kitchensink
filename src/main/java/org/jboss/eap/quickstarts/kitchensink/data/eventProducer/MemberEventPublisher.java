package org.jboss.eap.quickstarts.kitchensink.data.eventProducer;

import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.model.customEvent.AddMemberEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MemberEventPublisher implements IMemberEventPublisher {

    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publish(AddMemberEvent addMemberEvent) {
        applicationEventPublisher.publishEvent(addMemberEvent);
    }
}
