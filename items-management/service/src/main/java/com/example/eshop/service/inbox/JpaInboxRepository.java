package com.example.eshop.service.inbox;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaInboxRepository extends JpaRepository<InboxEntity, Long>,
        PagingAndSortingRepository<InboxEntity, Long> {

    @Query("select i from inbox i where i.status IN ('PENDING', 'FAILED') AND i.retries < :maxRetries")
    List<InboxEntity> findAllReadyForHandling(@Param("maxRetries") Integer maxRetries, Pageable pageable);


}
