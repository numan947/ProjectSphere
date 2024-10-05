package com.numan947.pmbackend.primary_packages.metadata;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MetadataServiceImpl implements MetadataService{
    private final MetadataRepository metadataRepository;
    @Override
    public List<Metadata> getAllMetadata(User user) {
        return metadataRepository.findAll();
    }
    @Override
    public List<Metadata> getAllMetadataByType(User user, MetadataType type) {
        return metadataRepository.findAllByType(type);
    }

    @Override
    public Metadata createMetadata(User user, Metadata metadata) {
        // Check if metadata with the same name already exists
        Metadata existingMetadata = metadataRepository.findByNameAndType(metadata.getName(), metadata.getType());
        if(existingMetadata != null){
            throw new OperationNotPermittedException("Metadata with the same name already exists");
        }
        return metadataRepository.save(metadata);
    }


    @Override
    public void deleteMetadata(User user, Metadata metadata) {
        // Check if metadata with the same name already exists
        Metadata existingMetadata = metadataRepository.findByNameAndType(metadata.getName(), metadata.getType());
        if(existingMetadata == null){
            throw new OperationNotPermittedException("Metadata with the same name does not exist");
        }
        metadataRepository.delete(metadata);
    }

}
