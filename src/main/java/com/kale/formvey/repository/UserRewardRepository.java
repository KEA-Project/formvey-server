package com.kale.formvey.repository;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Response;
import com.kale.formvey.domain.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    @Query("SELECT r FROM UserReward r where r.member.id=:id")
    List<UserReward> findByMemberId(Long id);
}
