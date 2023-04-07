package com.kale.formvey.service;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.Member;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public PostMemberRes emailSignup(PostMemberReq dto) throws BaseException {
        try {
            Member member = new Member();

            member.setEmail(dto.getEmail());
            member.setPoint(0);
            member.setPassword(dto.getPassword());
            member.setNickName(dto.getNickName());
            member.setPhone(dto.getPhone());

            Member newMember = memberRepository.save(member);

            return new PostMemberRes(newMember.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
