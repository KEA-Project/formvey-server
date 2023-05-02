package com.kale.formvey.service.shortForm;


import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.domain.ShortOption;
import com.kale.formvey.domain.Survey;
<<<<<<< HEAD
import com.kale.formvey.dto.shortForm.*;
import com.kale.formvey.dto.shortOption.GetShortOptionRes;
=======
import com.kale.formvey.dto.shortForm.PostShortFormReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
>>>>>>> 2e373c2363d42a93649e5937c992a213a5b3a14d
import com.kale.formvey.dto.shortOption.PostShortOptionReq;
import com.kale.formvey.repository.ShortFormRepository;
import com.kale.formvey.repository.ShortOptionRepository;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
=======
>>>>>>> 2e373c2363d42a93649e5937c992a213a5b3a14d
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;
import static com.kale.formvey.config.BaseResponseStatus.SHORTFORMS_EMPTY_SHORTFORM_ID;
=======
import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;
>>>>>>> 2e373c2363d42a93649e5937c992a213a5b3a14d

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
    public PostShortFormRes createShortForm(Long surveyId, PostShortFormReq dto) throws BaseException {
        try {
            //짧폼 생성
            Survey survey = surveyRepository.findById(surveyId).get();
            ShortForm shortForm = PostShortFormReq.toEntity(survey, dto);

            shortForm = shortFormRepository.save(shortForm);

            //짧폼 옵션 생성
            //주관식이 아닌 객관식, 찬부식인 경우
            if(!dto.getShortOptions().isEmpty()) {
                for (PostShortOptionReq postShortOptionReq : dto.getShortOptions()) {
                    ShortOption shortOption = PostShortOptionReq.toEntity(shortForm, postShortOptionReq);
                    shortOptionRepository.save(shortOption);
                }
            }
            return new PostShortFormRes(shortForm.getId());

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
<<<<<<< HEAD

    /**
     * 짧폼 리스트 조회
     */
    public List<GetShortFormListRes> getShortFormList (int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ShortForm> boardShortForms = shortFormRepository.findAll(pageRequest);
        List<GetShortFormListRes> shortForms = new ArrayList<>();

        for(ShortForm shortForm : boardShortForms){
            GetShortFormListRes dto  = new GetShortFormListRes(shortForm.getSurvey().getId(), shortForm.getSurvey().getSurveyTitle(), shortForm.getId(), shortForm.getShortQuestion(), shortForm.getShortType(), shortForm.getShortResponse());

            shortForms.add(dto);
        }

        return shortForms;
    }

    /**
     * 짧폼 내용 조회
     */
    public GetShortFormRes getShortForm(Long shortFormID){

        if(shortFormRepository.findById(shortFormID).isEmpty())
            throw new BaseException(SHORTFORMS_EMPTY_SHORTFORM_ID);

        ShortForm shortForm = shortFormRepository.findById(shortFormID).get();

        List<GetShortOptionRes> options = shortForm.getShortOptions().stream()
                .map(shortOption -> new GetShortOptionRes(shortOption.getId(), shortOption.getShortIndex(), shortOption.getShortContent()))
                .collect(Collectors.toList());

        return new GetShortFormRes(shortForm.getId(), shortForm.getShortQuestion(), shortForm.getShortType(), options);
    }

    /**
     * 짧폼 메인 조회
     */
//    public List<GetShortFormMainRes> getShortFormMain(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
//        Page<ShortForm> mainShortForms = shortFormRepository.findAll(pageRequest);
//        List<GetShortFormMainRes> shortForms = new ArrayList<>();
//
//        for (ShortForm shortForm : mainShortForms) {
//            GetShortFormMainRes dto = new GetShortFormMainRes(shortForm.getSurvey().getId(), shortForm.getSurvey().getSurveyTitle(),shortForm.getId(), shortForm.getShortQuestion(), shortForm.getShortType());
//
//            shortForms.add(dto);
//        }
//
//        return shortForms;
//    }
}

=======
}
>>>>>>> 2e373c2363d42a93649e5937c992a213a5b3a14d
