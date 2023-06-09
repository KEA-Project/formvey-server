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

@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    private String password;

    private int point;

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Response> responses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ShortAnswer> shortAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ShortResult> shortResults = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<UserReward> userRewards = new ArrayList<>();

    public void update(PatchMemberReq dto) {
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
    }

    public void updateStatus(int i) {
        setStatus(i);
    }

    public void modifySurveyPoint(int i) {
        this.point += i;
    }
}
