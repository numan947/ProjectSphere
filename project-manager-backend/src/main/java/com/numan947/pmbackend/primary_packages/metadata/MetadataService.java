package com.numan947.pmbackend.primary_packages.metadata;

import com.numan947.pmbackend.user.User;

import java.util.List;

public interface MetadataService {
    List<Metadata>getAllMetadata(User user);
    Metadata createMetadata(User user, Metadata metadata);
    void deleteMetadata(User user, Metadata metadata);
    List<Metadata>getAllMetadataByType(User user, MetadataType type);
}
