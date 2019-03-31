package com.aztu68.spring.greenwich.genesis.configuration;

import com.aztu68.spring.greenwich.genesis.model.Reader;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class ReaderHandlerMethodArgumentResolver
        implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().isAssignableFrom(Reader.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        return Optional.ofNullable((Authentication) webRequest.getUserPrincipal())
                .map((auth) -> auth.getPrincipal())
                .filter((principal) -> principal instanceof Reader)
                .orElse(null);
    }

}
