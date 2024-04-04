package zerobase.storereservationapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Login {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
