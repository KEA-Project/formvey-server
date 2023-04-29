package com.kale.formvey.domain;

import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR"
        , sequenceName = "MEMBER_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;
    private String password;

    private int point;

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Response> responses = new ArrayList<>();

    //--------------------------------------------------------------

    public void update(PatchMemberReq dto) {
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
    }

    public void updateStatus(int i) {
        setStatus(i);
    }
}
