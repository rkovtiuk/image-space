package com.imagespace.source.domain.repository;

import com.imagespace.source.domain.entity.ImageDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface ImageRepository extends ReactiveMongoRepository<ImageDocument, String> {

    Flux<ImageDocument> findAllByIdIn(Collection<String> ids);

}
