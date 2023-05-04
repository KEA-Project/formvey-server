package com.kale.formvey.service.shortResult;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.domain.ShortResult;
import com.kale.formvey.dto.shortResult.PostShortResultReq;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.ShortFormRepository;
import com.kale.formvey.repository.ShortResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortResultService {

    private final ShortResultRepository shortResultRepository;
    private final ShortFormRepository shortFormRepository;
    private final MemberRepository memberRepository;

    /**
     * 짧폼 해금
     */
    public void responseShortResult(Long shortFormId, Long memberId){
        Member member = memberRepository.findById(memberId).get();
        ShortForm shortForm = shortFormRepository.findById(shortFormId).get();

        // 짧폼 해금
        shortResultRepository.save(PostShortResultReq.toEntity(member, shortForm));
//        ShortResult shortResult = PostShortResultReq.toEntity(member, shortForm);
//        shortResultRepository.save(shortResult);
    }

}
