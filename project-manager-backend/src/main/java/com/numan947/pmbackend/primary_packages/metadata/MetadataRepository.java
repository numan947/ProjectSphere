package com.numan947.pmbackend.primary_packages.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata, String> {
    Metadata findByNameAndType(String name, MetadataType type);

    List<Metadata> findAllByType(MetadataType type);
}
