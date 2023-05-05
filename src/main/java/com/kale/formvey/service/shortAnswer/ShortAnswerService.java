package com.kale.formvey.service.shortAnswer;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.shortAnswer.PostShortAnswerReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
import com.kale.formvey.dto.shortOption.PostShortOptionReq;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.ShortAnswerRepository;
import com.kale.formvey.repository.ShortFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ShortAnswerService {

    private final ShortAnswerRepository shortAnswerRepository;

    private final MemberRepository memberRepository;

    private final ShortFormRepository shortFormRepository;


    /**
     * 짧폼 답변
     */
    public void responseShortAnswer(PostShortAnswerReq dto, Long shortFormId, Long memberId) throws BaseException {
        try {
            Member member = memberRepository.findById(memberId).get(); // 짧폼 답변자
            ShortForm shortForm = shortFormRepository.findById(shortFormId).get(); // 답변하고자 하는 짧폼

            // 숏폼 답변 등록
            List<ShortAnswer> shortAnswer = new ArrayList<>();
            shortAnswer.add(PostShortAnswerReq.toEntity(member, shortForm, dto));
            shortAnswerRepository.saveAll(shortAnswer);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

}
