package zerobase.storereservationapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.storereservationapi.domain.UserEntity;
import zerobase.storereservationapi.dto.Login;
import zerobase.storereservationapi.dto.SignUp;
import zerobase.storereservationapi.dto.UserDto;
import zerobase.storereservationapi.exception.CustomException;
import zerobase.storereservationapi.repository.UserRepository;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    }

    public UserDto generalSignUp(SignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(AlREADY_EXIST_USER);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CUSTOMER")
                .build();

        return UserDto.toDto(userRepository.save(userEntity));
    }

    public UserDto partnerSignUp(SignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(AlREADY_EXIST_USER);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_MANAGER")
                .build();

        return UserDto.toDto(userRepository.save(userEntity));
    }

    public UserDto authenticate(Login request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new CustomException(PASSWORD_UN_MATCH);
        }

        return UserDto.toDto(userEntity);
    }
}
