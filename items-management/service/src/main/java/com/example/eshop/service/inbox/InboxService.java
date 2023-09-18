package com.example.eshop.service.inbox;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InboxService {
    private final JpaInboxRepository jpaInboxRepository;

    public InboxEntity save(InboxEntity entity) {
        return jpaInboxRepository.save(entity);
    }

    public List<InboxEntity> findAllReadyForHandling(Integer maxAllowedRetries,
                                                     Integer maxFetchSize) {
        return jpaInboxRepository.findAllReadyForHandling(maxAllowedRetries,
                Pageable.ofSize(maxFetchSize));
    }

}
