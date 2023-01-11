package io.github.depromeet.knockknockbackend.global.error;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (KnockException e) {
            writeErrorResponse(response, e.getErrorCode(), request.getRequestURL().toString());
        } catch (Exception e) {
            if (e.getCause() instanceof KnockException) {
                writeErrorResponse(
                        response,
                        ((KnockException) e.getCause()).getErrorCode(),
                        request.getRequestURL().toString());
            } else {
                e.printStackTrace();
                writeErrorResponse(
                        response,
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        request.getRequestURL().toString());
            }
        }
    }

    private void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode, String path)
            throws IOException {
        ErrorResponse errorResponse =
                new ErrorResponse(
                        errorCode.getStatus(), errorCode.getCode(), errorCode.getReason(), path);

        response.setStatus(errorCode.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
