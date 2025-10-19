package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Videorepo extends JpaRepository<Video,Long> {

}
