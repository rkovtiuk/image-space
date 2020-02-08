package com.imagespace.source.domain.repository;

import com.imagespace.source.common.projection.PostSource;
import com.imagespace.source.common.projection.PreviewSource;
import com.imagespace.source.common.projection.SmallSource;
import com.imagespace.source.domain.entity.SourceDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SourceRepository extends ReactiveMongoRepository<SourceDocument, String> {

    Mono<SourceDocument> findFirstBySourceId(String sourceId);

    Mono<PostSource> findPostSourceById(String id);

    Mono<PreviewSource> findPreviewSourceById(String id);

    Mono<SmallSource> findSmallSourceById(String id);

}
