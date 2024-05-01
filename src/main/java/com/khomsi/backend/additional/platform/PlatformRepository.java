package com.khomsi.backend.additional.platform;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Optional<Platform> findByNameIgnoreCase(String name);
}
