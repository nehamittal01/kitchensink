package org.jboss.eap.quickstarts.kitchensink.service;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("qa")
public class MemberRegistrationServiceTest {

    @Mock
    IMemberRegistration memberRegistration;

}
