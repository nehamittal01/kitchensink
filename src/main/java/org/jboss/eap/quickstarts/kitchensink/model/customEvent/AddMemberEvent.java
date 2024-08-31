package org.jboss.eap.quickstarts.kitchensink.model.customEvent;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.springframework.context.ApplicationEvent;


public class AddMemberEvent extends ApplicationEvent {

    private Member member;


    public AddMemberEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }

    public AddMemberEvent(Object source) {
        super(source);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
