package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
    Page<ShortForm> findAll(Pageable pageable);

}
