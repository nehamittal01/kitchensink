package org.jboss.eap.quickstarts.kitchensink.config.converter;

import org.jboss.eap.quickstarts.kitchensink.converter.CreateMemberRequestToMember;
import org.jboss.eap.quickstarts.kitchensink.converter.MemberToGetMemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConverterConfig {
    @Autowired
    private MemberToGetMemberResponse memberToGetMemberResponse;

    @Autowired
    CreateMemberRequestToMember createMemberRequestToMember;


    @Bean(name = "kitchenSinkConversionService")
    public ConversionService conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(addConverters());
        conversionServiceFactoryBean.afterPropertiesSet();
        return conversionServiceFactoryBean.getObject();
    }

    public Set<Converter<?, ?>> addConverters() {
        Set<Converter<?, ?>> converters = new HashSet<>();
        converters.add(memberToGetMemberResponse);
        converters.add(createMemberRequestToMember);
        return converters;
    }
}
