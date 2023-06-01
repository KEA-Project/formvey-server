package com.kale.formvey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetResponseIndividualRes {
    private Long responseId;

    private Long memberId;

    private String nickname;

    private String responseDate;

    private int pages;
}
