package com.khomsi.backend.additional.developer;

import com.khomsi.backend.additional.developer.model.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
