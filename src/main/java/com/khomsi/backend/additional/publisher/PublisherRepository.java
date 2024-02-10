package com.khomsi.backend.additional.publisher;

import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
