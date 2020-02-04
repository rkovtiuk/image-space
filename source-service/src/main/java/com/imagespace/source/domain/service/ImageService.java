package com.imagespace.source.domain.service;

import com.imagespace.source.config.SourceServiceConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    String DEFAULT_FORMAT = "jpg";
    SourceServiceConfig sourceServiceConfig;

    public byte[] resizeToPost(byte[] source) throws IOException {
        return resize(source, sourceServiceConfig.getSourceWidth(), sourceServiceConfig.getSourceSmallHigh());
    }

    public byte[] resizeToPreview(byte[] source) throws IOException {
        return resize(source, sourceServiceConfig.getSourcePreviewWidth(), sourceServiceConfig.getSourcePreviewHigh());
    }

    public byte[] resizeToSmall(byte[] source) throws IOException {
        return resize(source, sourceServiceConfig.getSourceSmallWidth(), sourceServiceConfig.getSourceSmallHigh());
    }

    private byte[] resize(byte[] source, int width, int high) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Thumbnails.of(new ByteArrayInputStream(source))
                .size(width, high)
                .outputFormat(DEFAULT_FORMAT)
                .toOutputStream(output);
            byte[] resizedData = output.toByteArray();
            output.flush();
            return resizedData;
        }
    }

}
