package com.numan947.pmbackend.primary_packages.metadata;

import com.numan947.pmbackend.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "metadata", description = "Metadata related operations")
@RequestMapping("/metadata")
public class MetadataController {
    private final MetadataService metadataService;

    @GetMapping
    public ResponseEntity<List<Metadata>> getAllMetadata(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(metadataService.getAllMetadata(user));
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<Metadata>> getAllMetadataByType(@PathVariable String type, Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(metadataService.getAllMetadataByType(user, MetadataType.fromString(type)));
    }

    @PostMapping
    public ResponseEntity<Metadata> createMetadata(@RequestBody Metadata metadata, Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(metadataService.createMetadata(user, metadata));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMetadata(@RequestBody Metadata metadata, Authentication auth){
        User user = (User) auth.getPrincipal();
        metadataService.deleteMetadata(user, metadata);
        return ResponseEntity.ok().build();
    }

}
