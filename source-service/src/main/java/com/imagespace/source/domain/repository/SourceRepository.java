package com.imagespace.source.domain.repository;

import com.imagespace.source.domain.entity.SourceDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface SourceRepository extends ReactiveMongoRepository<SourceDocument, String> {

    Flux<SourceDocument> findAllBySourceIdIn(Collection<String> ids);

}
