package com.tandanji.taco_core_server.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter extends OncePerRequestFilter {

    private static final Logger accessLogger = LogManager.getLogger("ACCESS_LOG");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = responseWrapper.getStatus();

            accessLogger.info("[{}] {} {} from IP: {} - {}ms - Status: {}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    duration,
                    status
            );

            MDC.clear();
            responseWrapper.copyBodyToResponse();
        }
    }
}


