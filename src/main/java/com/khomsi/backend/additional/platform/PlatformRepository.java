package com.khomsi.backend.additional.platform;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
