package com.kale.formvey.dto.answer;

import com.kale.formvey.domain.Answer;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAnswerRes {
    private Long questionId;
    private String answerContent;

}
