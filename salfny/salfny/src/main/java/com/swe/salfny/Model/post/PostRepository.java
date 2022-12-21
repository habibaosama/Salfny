package com.swe.salfny.Model.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post p ORDER BY p.date DESC LIMIT ?1, ?2", nativeQuery = true)
    public List<Post> showRecentPosts(int offset, int limit);

    @Query(value = "SELECT p.* FROM " +
                    "post p JOIN preferences pre ON p.category_id = pre.category_id " +
                    "JOIN user u ON u.id = pre.user_id WHERE u.email = ?1", nativeQuery = true)
    public List<Post> showPreferredPosts(String email);


    @Query("SELECT MAX(id) FROM Post")
    public int findMaxId();

    @Query(value = "SELECT p.* FROM post p ORDER BY p.views DESC LIMIT 10", nativeQuery = true)
    public List<Post> showTopTenViewedPosts();

    @Query(value = "SELECT * FROM post p  WHERE p.id = ?1", nativeQuery = true)
    public Post showSpecificPost(int id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET views = views+1 WHERE id = ?1", nativeQuery = true)
    public void incrementViews(int id);

}
