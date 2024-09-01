package org.jboss.eap.quickstarts.kitchensink.repository;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("qa")
public class MemberRepositoryTest {

    @MockBean
    private IMemberRepository memberRepository;

    @Test
    void testFindByEmail_positiveResponse(){
        Member expectedMember =Member.builder().id("11").name("member1").email("member1@gmail.com").phoneNumber("9876543210").build();
        when(memberRepository.findByEmail(expectedMember.getEmail())).thenReturn(expectedMember);
        Member foundMember= memberRepository.findByEmail(expectedMember.getEmail());
        assertNotNull(foundMember);
        assertEquals(expectedMember.getName(), foundMember.getName());
        assertEquals(expectedMember.getEmail(), foundMember.getEmail());
    }

    @Test
    void testFindByEmail_nullResponse(){
        Member expectedMember =Member.builder().id("11").name("member1").email("member1@gmail.com").phoneNumber("9876543210").build();
        when(memberRepository.findByEmail(expectedMember.getEmail())).thenReturn(null);
        Member foundMember= memberRepository.findByEmail("member2@gmail.com");
        assertNull(foundMember);
    }

    @Test
    void testFindAllByOrderByNameAsc_positiveResponse(){
        Member expectedMember1 =Member.builder().id("11").name("member1").email("member1@gmail.com").phoneNumber("9876543210").build();
        Member expectedMember2 =Member.builder().id("12").name("member2").email("member2@gmail.com").phoneNumber("9876543211").build();
        List<Member> memberList = List.of(expectedMember1, expectedMember2);

        when(memberRepository.findAllByOrderByNameAsc()).thenReturn(memberList);
        List<Member> foundMemberList=memberRepository.findAllByOrderByNameAsc();
        assertNotNull(foundMemberList);
        assertEquals(foundMemberList.size(),memberList.size());
    }

    @Test
    void testFindAllByOrderByNameAsc_nullResponse(){
        when(memberRepository.findAllByOrderByNameAsc()).thenReturn(null);
        List<Member> foundMemberList=memberRepository.findAllByOrderByNameAsc();
        assertNull(foundMemberList);
    }
}
