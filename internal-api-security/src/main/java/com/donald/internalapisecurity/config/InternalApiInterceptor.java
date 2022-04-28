package com.donald.internalapisecurity.config;

import com.donald.internalapisecurity.common.Constant;
import com.donald.internalapisecurity.config.annotation.InternalApi;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.donald.internalapisecurity.common.Constant.INNER_REQUEST_HEADER;

/**
 * @author donald
 * @date 2022/01/12
 */
public class InternalApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        InternalApi internalApi = getAnnotation(handler);

        if (Objects.isNull(internalApi)) {

            return true;
        }

        boolean isInternalApi = Boolean.valueOf(request.getHeader(INNER_REQUEST_HEADER));

        if (!isInternalApi) {

            throw new InternalApiException(Constant.ERROR_CODE,
                    "This is the internal interface forbidding access.");
        }

        return true;
    }

    private InternalApi getAnnotation(Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        InternalApi internalApi = handlerMethod.getMethodAnnotation(InternalApi.class);

        if (Objects.isNull(internalApi)) {

            internalApi = handlerMethod.getMethod().getDeclaringClass().getAnnotation(InternalApi.class);
        }

        return internalApi;
    }
}
