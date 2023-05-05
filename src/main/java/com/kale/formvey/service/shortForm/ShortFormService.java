package com.kale.formvey.service.shortForm;


import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.domain.ShortOption;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.shortForm.PostShortFormReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
import com.kale.formvey.dto.shortOption.PostShortOptionReq;
import com.kale.formvey.repository.ShortFormRepository;
import com.kale.formvey.repository.ShortOptionRepository;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortFormService {

    private final SurveyRepository surveyRepository;
    private final ShortFormRepository shortFormRepository;
    private final ShortOptionRepository shortOptionRepository;

    /**
     * 짧폼 생성
     */
//    public PostShortFormRes createShortForm(Long surveyId, PostShortFormReq dto) throws BaseException {
//        try {
//            //짧폼 생성
//            Survey survey = surveyRepository.findById(surveyId).get();
//            ShortForm shortForm = PostShortFormReq.toEntity(survey, dto);
//
//            shortForm = shortFormRepository.save(shortForm);
//
//            //짧폼 옵션 생성
//            //주관식이 아닌 객관식, 찬부식인 경우
//            if(!dto.getShortOptions().isEmpty()) {
//                for (PostShortOptionReq postShortOptionReq : dto.getShortOptions()) {
//                    ShortOption shortOption = PostShortOptionReq.toEntity(shortForm, postShortOptionReq);
//                    shortOptionRepository.save(shortOption);
//                }
//            }
//            return new PostShortFormRes(shortForm.getId());
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//
//    }
}
