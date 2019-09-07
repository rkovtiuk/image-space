package com.imagespace.source.domain.service;

import com.imagespace.source.dto.ImageDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    public boolean saveImage(ImageDto imageDto) {
        // TODO: 07.09.19 implement saving an image
        return true;
    }

}
