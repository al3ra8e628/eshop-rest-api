package com.example.eshop.documents.eshopdocuments.repositories.jpa;

import com.example.eshop.documents.eshopdocuments.repositories.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPADocumentsRepository extends JpaRepository<DocumentEntity, Long> {
    Optional<DocumentEntity> findFirstByResourceTypeAndResourceReferenceAndDocumentReference(
            String resourceType, String resourceReference, String documentReference);

}
