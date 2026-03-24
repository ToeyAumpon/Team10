package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Post;

@Mapper
public interface PostMapper {

	void insert(Post post);
	
	List<Post> findAll();
}
