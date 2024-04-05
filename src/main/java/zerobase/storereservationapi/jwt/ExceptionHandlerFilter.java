package zerobase.storereservationapi.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import zerobase.storereservationapi.dto.ErrorResponse;
import zerobase.storereservationapi.exception.CustomException;
import zerobase.storereservationapi.type.ErrorCode;

import java.io.IOException;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    /**
     * 토큰 관련 에러 핸들링
     * JwtTokenFilter 에서 발생하는 에러를 핸들링해준다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse(response, e);
        }
    }

    /**
     * Security Chain 에서 발생하는 에러 응답 구성
     */
    private void setErrorResponse(HttpServletResponse response, CustomException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(
                ErrorResponse.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getErrorMessage())
                        .build()
                        .convertToJson()
        );

    }
}
