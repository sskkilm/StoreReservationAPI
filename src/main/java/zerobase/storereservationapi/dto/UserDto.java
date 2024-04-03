package zerobase.storereservationapi.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.UserEntity;

@Getter
@Builder
public class UserDto {
    private String username;
    private String role;

    public static UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();
    }
}
