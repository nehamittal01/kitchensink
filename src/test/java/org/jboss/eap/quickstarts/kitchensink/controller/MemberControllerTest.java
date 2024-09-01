package org.jboss.eap.quickstarts.kitchensink.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.hamcrest.Matchers;
import org.jboss.eap.quickstarts.kitchensink.controler.MemberController;
import org.jboss.eap.quickstarts.kitchensink.data.eventProducer.IMemberEventPublisher;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.model.request.CreateMemberRequest;
import org.jboss.eap.quickstarts.kitchensink.model.response.GetMemberResponse;
import org.jboss.eap.quickstarts.kitchensink.repository.IMemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.IMemberRegistration;
import org.jboss.eap.quickstarts.kitchensink.service.IValidationService;
import org.jboss.eap.quickstarts.kitchensink.service.impl.MemberRegistration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(MemberController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("qa")
public class MemberControllerTest {

    @InjectMocks
    MemberController memberController;

    @Mock
    private IMemberRegistration memberRegistration;

    @MockBean
    private IMemberRepository memberRepository;

    @Mock
    @Qualifier("kitchenSinkConversionService")
    private ConversionService conversionService;

    @Mock
    private IMemberEventPublisher eventPublisher;


    @Mock
    @Qualifier("memberData")
    private List<Member> memberList;

    @Mock
    IValidationService validationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(MemberControllerTest.class);
    }


    protected URI getHTTPEndpoint() {
        String host = getServerHost();
        if (host == null) {
            host = "http://localhost:8080/kitchensink";
        }
        try {
            return new URI(host + "/rest/members");
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getServerHost() {
        String host = System.getenv("SERVER_HOST");
        if (host == null) {
            host = System.getProperty("server.host");
        }
        return host;
    }

    @Test
    void listAllMembers_positiveResponse() throws Exception {
        List<GetMemberResponse> getMemberResponseList =new ArrayList<>();
        getMemberResponseList.add(GetMemberResponse.builder().id("11").name("member1").email("member1@gmail.com").phoneNumber("9876543210").build());
        lenient().when(memberRegistration.getAllMemberOrderedByName()).thenReturn(getMemberResponseList);
        mockMvc.perform(get("/rest/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void lookupMemberById_404Response() throws Exception {
        GetMemberResponse getMemberResponse =GetMemberResponse.builder().id("11").name("member1").email("member1@gmail.com").phoneNumber("9876543210").build();
        lenient().when(memberRegistration.findMemberById("11")).thenReturn(getMemberResponse);
        mockMvc.perform(get("/rest/members/{id}", "11")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    void createMember_positiveResponse() throws Exception {
        CreateMemberRequest createMemberRequest =CreateMemberRequest.builder().name("members").email("member1@gmail.com").phoneNumber("9876543210").build();

        lenient().doNothing().when(validationService).validateMember(createMemberRequest);
        lenient().doNothing().when(memberRegistration).register(createMemberRequest);

        mockMvc.perform(post("/rest/members")
                        .content(objectMapper.writeValueAsString(createMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    void createMember_InvalidMemberName_400Response() throws Exception {
        CreateMemberRequest createMemberRequest =CreateMemberRequest.builder().name("members1").email("member1@gmail.com").phoneNumber("9876543210").build();

        lenient().doNothing().when(validationService).validateMember(createMemberRequest);
        lenient().doNothing().when(memberRegistration).register(createMemberRequest);

        mockMvc.perform(post("/rest/members")
                        .content(objectMapper.writeValueAsString(createMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void createMember_InvalidMemberPhoneNumber_400Response() throws Exception {
        CreateMemberRequest createMemberRequest =CreateMemberRequest.builder().name("members1").email("member1@gmail.com").phoneNumber("asdc").build();

        lenient().doNothing().when(validationService).validateMember(createMemberRequest);
        lenient().doNothing().when(memberRegistration).register(createMemberRequest);

        mockMvc.perform(post("/rest/members")
                        .content(objectMapper.writeValueAsString(createMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }


}
