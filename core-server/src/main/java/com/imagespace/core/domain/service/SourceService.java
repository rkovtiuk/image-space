package com.imagespace.core.domain.service;

import com.imagespace.core.domain.repositories.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceService {

    private final SourceRepository sourceRepository;

}
