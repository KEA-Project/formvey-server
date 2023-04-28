package com.kale.formvey.controller.member;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.member.GetMemberRes;
import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.service.member.MemberService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    private final JwtService jwtService;

    /**
     * 이메일 회원가입
     * [POST] /members/signup
     * @return BaseResponse<PostMemberRes>
     */
    @ResponseBody
    @PostMapping("/signup")
    @ApiOperation(value = "이메일 회원가입")
    @ApiResponses({
            @ApiResponse(code=2016, message="이메일 형식을 확인해주세요."),
            @ApiResponse(code=2017, message="중복된 이메일 입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<PostMemberRes> emailSignup(@RequestBody PostMemberReq dto) {
        try {
            PostMemberRes postMemberRes = memberService.emailSignup(dto);

            return new BaseResponse<>(postMemberRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 정보 조회
     * [GET] /members/info/{memberId}
     * @return BaseResponse<GetMemberRes>
     */
    @ResponseBody
    @GetMapping("/info/{memberId}")
    @ApiOperation(value = "회원 정보 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name="memberId", value="조회할 유저 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2010, message="유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<GetMemberRes> getMemberInfo(@PathVariable Long memberId) {
        try{
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(memberId!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetMemberRes getMemberRes = memberService.getMemberInfo(memberId);

            return new BaseResponse<>(getMemberRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 정보 수정
     * [PATCH] /members/edit/{memberId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/edit/{memberId}")
    @ApiOperation(value = "회원 정보 수정", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name="memberId", value="수정할 유저 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2010, message="유저 아이디 값을 확인해주세요."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> editProfile(@RequestBody PatchMemberReq dto, @PathVariable Long memberId) {
        try{
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(memberId!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            memberService.editProfile(memberId, dto);
            String result = "회원 정보 수정이 완료되었습니다";

            return new BaseResponse<>(result);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
