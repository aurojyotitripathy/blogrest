package com.nextcloudapps.blogapi.repository;

import java.util.List;

import com.nextcloudapps.blogapi.model.post.Post;
import com.nextcloudapps.blogapi.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCreatedBy(Long userId, Pageable pageable);
    Page<Post> findByCategory(Long categoryId, Pageable pageable);
    Page<Post> findByTags(List<Tag> tags, Pageable pageable);
    Long countByCreatedBy(Long userId);
}
