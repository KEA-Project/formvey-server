package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
}
