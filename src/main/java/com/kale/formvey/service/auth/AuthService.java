package com.kale.formvey.service.auth;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.Member;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kale.formvey.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    /**
     * 이메일 로그인 및 jwt 생성
     */
    public PostLoginRes emailLogin(PostLoginReq dto) {
        // 존재하지 않는 이메일
        if (memberRepository.findByEmail(dto.getEmail()).isEmpty())
            throw new BaseException(POST_USERS_EMPTY_EMAIL);

        // 비밀번호가 틀림
        if (!memberRepository.findByEmail(dto.getEmail()).get().getPassword().equals(dto.getPassword()))
            throw new BaseException(POST_USERS_WRONG_PASSWORD);

        //jwt 생성 후 반환
        Member member = memberRepository.findByEmail(dto.getEmail()).get();
        String jwt = jwtService.createJwt(member.getId());

        return new PostLoginRes(member.getId(),jwt);
    }
}
