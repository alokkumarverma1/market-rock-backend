package com.example.dimondinvest.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dimondinvest.dto.Commentvideodto;
import com.example.dimondinvest.dto.SendVideodto;
import com.example.dimondinvest.dto.Videodto;
import com.example.dimondinvest.entity.Commentvideo;
import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.entity.Video;
import com.example.dimondinvest.repo.CommentVideorepo;
import com.example.dimondinvest.repo.Registerrepo;
import com.example.dimondinvest.repo.Videorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class VideoServcie {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private Videorepo videorepo;
    @Autowired
    private CommentVideorepo commentVideorepo;
    @Autowired
    private Registerrepo registerrepo;

    public ResponseEntity<?> uploadvideo(Videodto videodto) {
        try {
            MultipartFile videoFile = videodto.getVideo();
            var videoUploadResult = cloudinary.uploader().upload(
                    videoFile.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video",
                            "upload_preset", "myfiles",
                            "unsigned", true
                    )
            );
            String videoUrl = videoUploadResult.get("secure_url").toString();
            String videoPublicId = videoUploadResult.get("public_id").toString();

            //  Upload the thumbnail image ===
            MultipartFile thumbnailFile = videodto.getThumbnail();
            var imageUploadResult = cloudinary.uploader().upload(
                    thumbnailFile.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "upload_preset", "myfiles",
                            "unsigned", true
                    )
            );
            String thumbnailUrl = imageUploadResult.get("secure_url").toString();
            String thumbnailpublicid = imageUploadResult.get("public_id").toString();

            Video video = new Video();
            video.setTitle(videodto.getTitle());
            video.setVideoUrl(videoUrl);
            video.setVideopublicId(videoPublicId);
            video.setThumbnailpublicId(thumbnailpublicid);
            video.setThumbnail(thumbnailUrl);
            video.setDate(LocalDate.now().toString());
            videorepo.save(video);
            return ResponseEntity.ok(video);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Video upload failed");
        }
    }

    // my all free video
    public ResponseEntity<?> allfreevideo(Authentication authentication){
      List<Video> video = videorepo.findAll();
      if(video == null){
          return ResponseEntity.badRequest().body("video not found");
      }
       List<SendVideodto> videodto = video.stream().map(d->{
           SendVideodto videodto1= new SendVideodto();
            videodto1.setVideoUrl(d.getVideoUrl());
            videodto1.setThumbnail(d.getThumbnail());
            videodto1.setId(d.getId());
            videodto1.setTitle(d.getTitle());
            videodto1.setDate(d.getDate());
           return videodto1;
       }).toList();
        return ResponseEntity.ok(videodto);
    }

    // commnet video
    public ResponseEntity<?> addcomment(Commentvideodto commentvideodto,Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        Commentvideo commentvideo = new Commentvideo();
        commentvideo.setComment(commentvideodto.getComment());
        commentvideo.setName(register.getName());
       commentVideorepo.save(commentvideo);
        return ResponseEntity.ok("comment add sucess");
    }

    // all comment
    public ResponseEntity<?> allcommetn(Authentication authentication){
        List<Commentvideo> commentvideos = commentVideorepo.findAll();
        List<Commentvideodto> commentvideodtos = commentvideos.stream().map(d->{
            Commentvideodto commentvideodto = new Commentvideodto();
            commentvideodto.setComment(d.getComment());
            commentvideodto.setName(d.getName());
            return commentvideodto;
        }).toList();
        return ResponseEntity.ok(commentvideodtos);
    }





}
