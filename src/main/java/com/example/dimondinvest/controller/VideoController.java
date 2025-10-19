package com.example.dimondinvest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dimondinvest.dto.Commentvideodto;
import com.example.dimondinvest.dto.Videodto;
import com.example.dimondinvest.entity.Video;
import com.example.dimondinvest.repo.Videorepo;
import com.example.dimondinvest.service.VideoServcie;
import jakarta.security.auth.message.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class VideoController {

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private VideoServcie videoServcie;
    @Autowired
    private Videorepo videorepo;

    @PostMapping("/videoupload")
    public ResponseEntity<?> videoupload(@ModelAttribute Videodto videodto){
        ResponseEntity res = videoServcie.uploadvideo(videodto);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/allvideo")
    public ResponseEntity<?> allvideo(Authentication authentication){
        ResponseEntity res = videoServcie.allfreevideo(authentication);
        return ResponseEntity.ok(res);
    }

    // video comment
    @PostMapping("/addcomment")
    public ResponseEntity<?> addcomment(@RequestBody Commentvideodto commentvideodto, Authentication authentication){
        ResponseEntity res = videoServcie.addcomment(commentvideodto,authentication);
        return ResponseEntity.ok(res);
    }

    // all get comment
    @GetMapping("/allcommentshow")
    public ResponseEntity<?> allcomment(Authentication authentication){
        ResponseEntity res = videoServcie.allcommetn(authentication);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/deletevideo")
    public ResponseEntity<?> deletevideo(@RequestBody Videodto videodto) {
        Optional<Video> videoOpt = videorepo.findById(videodto.getId());

        if(videoOpt.isPresent()) {
            Video video = videoOpt.get();

            //  Delete Video from Cloudinary
            try {
                cloudinary.uploader().destroy(video.getVideopublicId(),
                        ObjectUtils.asMap("resource_type", "video"));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete video from Cloudinary: " + e.getMessage());
            }

            // 2️ Delete Thumbnail from Cloudinary
            try {
                cloudinary.uploader().destroy(video.getThumbnailpublicId(),
                        ObjectUtils.asMap("resource_type", "image"));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete thumbnail from Cloudinary: " + e.getMessage());
            }

            //Delete record from Database
            videorepo.delete(video);

            return ResponseEntity.ok("Video and thumbnail deleted successfully from DB and Cloudinary");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Video not found in database");
        }
    }


}
