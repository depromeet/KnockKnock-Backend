package io.github.depromeet.knockknockbackend.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (KnockException e) {
            response.getWriter()
                    .write(objectMapper.writeValueAsString(getErrorResponse(
                            e.getErrorCode(),
                            request.getRequestURL().toString()
                    )));
        } catch (Exception e) {
            if (e.getCause() instanceof KnockException) {
                response.getWriter()
                        .write(objectMapper.writeValueAsString(getErrorResponse(
                                ((KnockException) e.getCause()).getErrorCode(),
                                request.getRequestURL().toString()
                        )));
            } else {
                e.printStackTrace();
                getErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURL().toString());
            }
        } finally {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }

    private ErrorResponse getErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getReason(), path);
    }


}
