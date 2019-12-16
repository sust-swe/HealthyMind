package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;

@Repository("commentRepository")
public interface CommentRepository extends CrudRepository<Comment, Long> {

@Query("select t from Comment t where post_id = :id")	
List<Comment> getCommentByPostId(int id);
}
