package com.kale.formvey.service.member;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.domain.Member;
import com.kale.formvey.dto.member.GetMemberRes;
import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kale.formvey.config.BaseResponseStatus.*;
import static com.kale.formvey.utils.ValidationRegex.isRegexEmail;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 이메일 회원가입
     */
    public PostMemberRes emailSignup(PostMemberReq dto) throws BaseException {
        try {
            // 이메일 중복일 때
            if (memberRepository.findByEmail(dto.getEmail()).isPresent())
                throw new BaseException(POST_USERS_EXISTS_EMAIL);

            //이메일 정규 표현 검증
            if (!isRegexEmail(dto.getEmail()))
                throw new BaseException(POST_USERS_INVALID_EMAIL);

            //새 유저 생성
            Member member = PostMemberReq.toEntity(dto);
            member.updateStatus(0);
            member = memberRepository.save(member);

            return new PostMemberRes(member.getId());

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 회원 정보 조회
     */
    public GetMemberRes getMemberInfo(Long memberId) throws BaseException{
        try {
            // 해당 유저 id가 존재하지 않을 때
            if (memberRepository.findById(memberId).isEmpty())
                throw new BaseException(USERS_EMPTY_USER_ID);

            Member member = memberRepository.findById(memberId).get();

            return new GetMemberRes(member.getId(), member.getEmail(),
                    member.getNickname(), member.getPoint());

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 회원 정보 수정
     */
    public void editProfile(Long memberId, PatchMemberReq dto) throws BaseException{
        try {
            // 해당 유저 id가 존재하지 않을 때
            if (memberRepository.findById(memberId).isEmpty())
                throw new BaseException(USERS_EMPTY_USER_ID);

            Member member = memberRepository.findById(memberId).get();
            member.update(dto);
            memberRepository.save(member);

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
