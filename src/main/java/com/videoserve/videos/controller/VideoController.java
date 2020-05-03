package com.videoserve.videos.controller;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * VideoController
 */
@Controller
public class VideoController {
    long ChunkSize = 1000000L;
    @GetMapping("/videos/{name}")
    public ResponseEntity<UrlResource> getVideo(@PathVariable String name,
                                      @RequestHeader HttpHeaders headers
                                                ) throws IOException {
        UrlResource urlResource = new UrlResource("file:videos/"+name+".mp4");
        MediaType mediaType = MediaTypeFactory
                .getMediaType(urlResource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaTypeFactory
                .getMediaType(mediaType.toString())
                .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(urlResource);
    }
//    private ResourceRegion resourceRegion( UrlResource video, HttpHeaders headers) throws IOException {
//
//        long contentLength = video.contentLength();
//        HttpRange range = headers.getRange().get(0);
//        if (range != null) {
//            long start = range.getRangeStart(contentLength);
//            long end = range.getRangeEnd(contentLength);
//            long  rangeLength = Math.min(ChunkSize, end - start + 1);
//            return new ResourceRegion(video, start, rangeLength);
//        } else {
//            long rangeLength = Math.min(ChunkSize, contentLength);
//            return new ResourceRegion(video, 0, rangeLength);
//        }
//    }

}
