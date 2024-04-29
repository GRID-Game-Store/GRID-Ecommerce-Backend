package com.khomsi.backend.additional.media;

import com.khomsi.backend.additional.media.model.entity.GameMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameMediaRepository extends JpaRepository<GameMedia, Long> {
}
