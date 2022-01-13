package br.sales.estante.filter;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.sales.estante.config.Constants.*;

@Provider
public class LoggingFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class);

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        MDC.remove(CORRELATION_ID);
        MDC.remove(CORRELATION_TIME);

        if (request.headers().contains(HEADER_CORRELATION_ID)) {
            MDC.put(CORRELATION_ID, request.getHeader(HEADER_CORRELATION_ID));
        } else {
            MDC.put(CORRELATION_ID, UUID.randomUUID());
        }
        MDC.put(CORRELATION_TIME, LocalDateTime.now());

        LOG.info("Adding correlation and time");
    }
}
