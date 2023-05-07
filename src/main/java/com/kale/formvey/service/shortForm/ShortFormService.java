package com.kale.formvey.service.shortForm;


import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.*;
import com.kale.formvey.dto.shortForm.*;
import com.kale.formvey.dto.shortOption.GetShortOptionRes;
import com.kale.formvey.dto.shortForm.PostShortFormReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
import com.kale.formvey.dto.shortOption.PostShortOptionReq;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;
import static com.kale.formvey.config.BaseResponseStatus.SHORTFORMS_EMPTY_SHORTFORM_ID;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortFormService {
    private final ShortFormRepository shortFormRepository;
    private final ShortResultRepository shortResultRepository;

    /**
     * 짧폼 리스트 조회
     */
    public List<GetShortFormListRes> getShortFormList (int page, int size, Long memberId){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ShortForm> boardShortForms = shortFormRepository.findAll(pageRequest);
        List<GetShortFormListRes> shortForms = new ArrayList<>();

        int totalPages = shortFormRepository.findAll().size();

        // 해금 여부 확인
        List<Long> resultList = shortResultRepository.findSurveyIdByMember(memberId);
        int resultStatus;

        for(ShortForm shortForm : boardShortForms){
            if (resultList.contains(shortForm.getId()))
                resultStatus = 1;
            else
                resultStatus = 0;

            GetShortFormListRes dto  = new GetShortFormListRes(shortForm.getSurvey().getId(), shortForm.getSurvey().getSurveyTitle(), shortForm.getId(), shortForm.getShortQuestion(), shortForm.getShortType(), shortForm.getShortResponse(), totalPages, resultStatus);

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

        return new GetShortFormRes(shortForm.getSurvey().getId(), shortForm.getSurvey().getSurveyTitle(), shortForm.getShortQuestion(), shortForm.getShortType(), options);
    }

    /**
     * 짧폼 메인 조회
     */
    public GetShortFormMainRes getShortFormMain(Long memberId) {

        ShortForm shortForm = shortFormRepository.findRandom(memberId).get();

        List<GetShortOptionRes> options = shortForm.getShortOptions().stream()
                .map(shortOption -> new GetShortOptionRes(shortOption.getId(), shortOption.getShortIndex(), shortOption.getShortContent()))
                .collect(Collectors.toList());


        return new GetShortFormMainRes(shortForm.getSurvey().getId(), shortForm.getSurvey().getSurveyTitle(), shortForm.getId(), shortForm.getShortQuestion(), shortForm.getShortType(), options);
    }
}

