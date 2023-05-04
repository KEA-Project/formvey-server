package com.kale.formvey.dto.shortResult;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.domain.ShortResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class PostShortResultReq {

    public static ShortResult toEntity(Member member, ShortForm shortForm){
        return ShortResult.builder()
                .member(member)
                .shortForm(shortForm)
                .build();
    }

}
