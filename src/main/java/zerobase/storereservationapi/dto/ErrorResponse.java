package zerobase.storereservationapi.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.storereservationapi.type.ErrorCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ErrorCode errorCode;
    private String errorMessage;

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
