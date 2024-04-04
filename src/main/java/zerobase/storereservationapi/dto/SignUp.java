package zerobase.storereservationapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
public class SignUp {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
