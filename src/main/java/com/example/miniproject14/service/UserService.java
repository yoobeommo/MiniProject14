package com.example.miniproject14.service;

import com.example.miniproject14.dto.*;
import com.example.miniproject14.entity.User;
import com.example.miniproject14.entity.UserRoleEnum;
import com.example.miniproject14.jwt.JwtUtil;
import com.example.miniproject14.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public GeneralResponseDto signup(SignupRequestDto signupRequestDto) {
        try {
            String username = signupRequestDto.getUsername();
            String nickname = signupRequestDto.getNickname();
            String password = passwordEncoder.encode(signupRequestDto.getPassword());

            // 회원 중복 확인
            Optional<User> found = userRepository.findByUsername(username);
            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 ID 입니다.");
            }

            // 사용자 ROLE 확인
            UserRoleEnum role = UserRoleEnum.USER;
            if (signupRequestDto.isAdmin()) {
                if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
                }
                role = UserRoleEnum.ADMIN;
            }

            User user = new User(username, nickname, password, role);
            userRepository.save(user);
            return new StatusResponseDto("회원 가입 완료!", HttpStatus.OK); // DB에 정상적으로 저장 되었을 경우 결과 리턴
        } catch (IllegalArgumentException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new StatusResponseDto("회원 가입 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public GeneralResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            // 사용자 확인

            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
            );

            // 비밀번호 확인
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));


            return new LoginResponseDto(user);
        }catch (Exception e) {
                return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
    }

}