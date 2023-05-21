package com.kale.formvey.domain;

import com.kale.formvey.dto.response.PostResponseReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Response extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private LocalDateTime responseDate;

    @OneToMany(mappedBy = "response", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public void updateStatus(int i) {
        setStatus(i);
    }

    public void update(PostResponseReq dto, Member member, Survey survey){
        this.member=member;
        this.survey=survey;
        this.responseDate=dto.getResponseDate();
    }
}
