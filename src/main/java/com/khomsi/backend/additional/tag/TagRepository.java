package com.khomsi.backend.additional.tag;

import com.khomsi.backend.additional.tag.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
