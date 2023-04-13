package com.kale.formvey.service.member;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.Member;
import com.kale.formvey.dto.member.GetMemberRes;
import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kale.formvey.config.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;
import static com.kale.formvey.config.BaseResponseStatus.USERS_EMPTY_USER_ID;
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public PostMemberRes emailSignup(PostMemberReq dto) {
        // 이메일 중복일 때
        if (memberRepository.findByEmail(dto.getEmail()).isPresent())
            throw new BaseException(POST_USERS_EXISTS_EMAIL);

        Member member = PostMemberReq.toEntity(dto);
        member = memberRepository.save(member);

        return new PostMemberRes(member.getId());
    }

    public GetMemberRes getMemberInfo(Long memberId) {
        // 해당 유저 id가 존재하지 않을 때
        if (memberRepository.findById(memberId).isEmpty())
            throw new BaseException(USERS_EMPTY_USER_ID);

        Member member = memberRepository.findById(memberId).get();

        return new GetMemberRes(member.getId(), member.getEmail(),
                member.getNickname(), member.getPoint(), member.getPhone());
    }

    public void editProfile(Long memberId, PatchMemberReq dto) {
        // 해당 유저 id가 존재하지 않을 때
        if (memberRepository.findById(memberId).isEmpty())
            throw new BaseException(USERS_EMPTY_USER_ID);

        Member member = memberRepository.findById(memberId).get();
        member.update(dto);
        memberRepository.save(member);
    }
}
