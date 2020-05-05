package com.videoserve.videos.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.videoserve.videos.model.UserContent;
import com.videoserve.videos.model.UserContentDto;
import com.videoserve.videos.repo.UserContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * VideoController
 */
@Controller
public class VideoController {

    @Value("${cloudinary.cloudname}")
    private String cloudName;

    @Value("${cloudinary.key}")
    private String apiKey;

    @Value("${cloudinary.secret}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @Autowired
    UserContentRepo contentRepo;

    @PostConstruct
    public void init() throws Exception {
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
    }

    @GetMapping("/videos/{name}")
    public ResponseEntity<?> getVideo(@PathVariable String name,
                                      @RequestHeader HttpHeaders headers
                                                ) throws IOException {
        List<UserContent> userContent = contentRepo.findByFileName(name);
        if(userContent.size()>0) {
            UrlResource urlResource = new UrlResource(userContent.get(0).getCloudUrl());
            MediaType mediaType = MediaTypeFactory
                    .getMediaType(urlResource)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaTypeFactory
                            .getMediaType(mediaType.toString())
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(urlResource);
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body("error");
        }

    }

    @RequestMapping(value="/videos/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try{
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String originalFilename = file.getOriginalFilename();
            String username = principal.getUsername();
            Map params = ObjectUtils.asMap(
                    "public_id", "my_folder/"+originalFilename,
                    "overwrite", true,
                    "resource_type", "video"
            );
            Map uploadResult = cloudinary
                    .uploader()
                    .upload(file.getBytes(), params);
            ObjectMapper mapper = new ObjectMapper();

            UserContent userContent = new UserContent()
                    .setUserName(username)
                    .setFileName(originalFilename)
                    .setCloudUrl(uploadResult.get("secure_url").toString())
                    .setContentType("video");
            contentRepo.save(userContent);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("saved");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/videos")
    public ResponseEntity<?> getVideos() throws IOException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        List<UserContent> userContents = contentRepo.findByUserName(username);
        List<UserContentDto> collect = userContents.stream()
                .map(uc -> {
                    return new UserContentDto()
                            .setFileName(uc.getFileName())
                            .setId(uc.getId());
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(collect);

    }
}
