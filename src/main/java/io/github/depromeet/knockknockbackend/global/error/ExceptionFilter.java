package io.github.depromeet.knockknockbackend.global.error;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (KnockException e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter()
                    .write(
                            objectMapper.writeValueAsString(
                                    getErrorResponse(
                                            e.getErrorCode(), request.getRequestURL().toString())));
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            getErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURL().toString());
        }
    }

    private ErrorResponse getErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                errorCode.getStatus(), errorCode.getCode(), errorCode.getReason(), path);
    }
}
