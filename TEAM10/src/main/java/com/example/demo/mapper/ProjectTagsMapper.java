package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProjectTagsMapper {
	

	List<ProjectTags> findAll();

	ProjectTags findByTagName(String tag_name);
	
	void insert(ProjectTags tag);
	
	void insertPostTag(@Param("post_id") long post_id, @Param("tag_id") long tag_id);
}