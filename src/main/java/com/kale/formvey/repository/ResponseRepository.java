package com.kale.formvey.repository;

import com.kale.formvey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Survey, Long> {
}
