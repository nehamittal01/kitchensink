package org.jboss.eap.quickstarts.kitchensink.data.eventProducer;

import org.jboss.eap.quickstarts.kitchensink.model.customEvent.AddMemberEvent;
import org.springframework.context.ApplicationEventPublisherAware;

public interface IMemberEventPublisher extends ApplicationEventPublisherAware {

    void publish(AddMemberEvent addMemberEvent);
}
