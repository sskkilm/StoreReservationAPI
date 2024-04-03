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
import zerobase.storereservationapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

    }

    public UserDto generalSignUp(SignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 존재하는 유저입니다.");
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
            throw new RuntimeException("이미 존재하는 유저입니다.");
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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return UserDto.toDto(userEntity);
    }
}
