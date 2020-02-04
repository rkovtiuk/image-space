package com.imagespace.source.domain.service;

import com.imagespace.source.common.exception.ImageResizeException;
import com.imagespace.source.config.SourceServiceConfig;
import com.imagespace.source.domain.entity.SourceDocument;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    String DEFAULT_FORMAT = "jpg";
    SourceServiceConfig sourceConfig;

    public SourceDocument resizeSourceData(String sourceId, byte[] sourceData) {
        return SourceDocument.builder()
            .id(sourceId)
            .postData(resizeToPost(sourceId, sourceData))
            .previewData(resizeToPreview(sourceId, sourceData))
            .smallData(resizeToSmall(sourceId, resizeToSmall(sourceId, sourceData)))
            .build();
    }

    private byte[] resizeToPost(String sourceId, byte[] source) {
        return resize(sourceId, source, sourceConfig.getSourceWidth(), sourceConfig.getSourceSmallHigh());
    }

    private byte[] resizeToPreview(String sourceId, byte[] source) {
        return resize(sourceId, source, sourceConfig.getSourcePreviewWidth(), sourceConfig.getSourcePreviewHigh());
    }

    private byte[] resizeToSmall(String sourceId, byte[] source) {
        return resize(sourceId, source, sourceConfig.getSourceSmallWidth(), sourceConfig.getSourceSmallHigh());
    }

    private byte[] resize(String sourceId, byte[] source, int width, int high) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Thumbnails.of(new ByteArrayInputStream(source))
                .size(width, high)
                .outputFormat(DEFAULT_FORMAT)
                .toOutputStream(output);
            byte[] resizedData = output.toByteArray();
            output.flush();
            return resizedData;
        } catch (IOException e) {
            log.error("Can't resize source from '{}' to {}x{}.", sourceId, width, high);
            throw new ImageResizeException("Can't resize source.", e.getCause());
        }
    }

}
