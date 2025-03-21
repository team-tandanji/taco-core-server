package com.tandanji.taco_core_server.infrastructure;

import jakarta.servlet.*;
import org.apache.logging.log4j.core.config.Order;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 필터 우선순위 설정
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId); // MDC에 requestId 저장

        try {
            chain.doFilter(request, response); // 다음 필터로 요청 전달
        } finally {
            MDC.clear(); // 응답 후 MDC 정리
        }
    }
}

