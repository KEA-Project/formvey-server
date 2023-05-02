package com.kale.formvey.dto.response;

import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponseReq {
    private List<PostAnswerReq> answers = new ArrayList<>();
    private LocalDate responseDate;

    public static Response toEntity(Member member, Survey survey, PostResponseReq dto){
        return Response.builder()
                .member(member)
                .survey(survey)
                .responseDate(dto.responseDate)
                .build();
    }
}
