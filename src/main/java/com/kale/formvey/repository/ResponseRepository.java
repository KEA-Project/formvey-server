package com.kale.formvey.repository;

import com.kale.formvey.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
