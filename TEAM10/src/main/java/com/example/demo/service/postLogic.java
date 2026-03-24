package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.ProjectImages;
import com.example.demo.mapper.ProjectImagesMapper;
import com.example.demo.mapper.ProjectPosts;
import com.example.demo.mapper.ProjectPostsMapper;
import com.example.demo.mapper.ProjectTagsMapper;


@Service

public class postLogic{
	private final ProjectPostsMapper projectPostsMapper;
	private final ProjectImagesMapper projectImagesMapper;
	private final ProjectTagsMapper projectTagsMapper;

    public postLogic(ProjectPostsMapper projectPostsMapper, ProjectImagesMapper projectImagesMapper, ProjectTagsMapper projectTagsMapper) {
        this.projectPostsMapper = projectPostsMapper;
        this.projectImagesMapper = projectImagesMapper;
        this.projectTagsMapper = projectTagsMapper;
    }
    @Transactional
    public void insertPost(ProjectPosts postDto) {
        
//    	ProjectPosts post = new ProjectPosts();
//    	
//    	//post.setPost_id(postDto.getPost_id());
//    	post.setUser_id(postDto.getUser_id());
//    	post.setTitle(postDto.getTitle());
//    	post.setContent(postDto.getContent());
    	
    	projectPostsMapper.insert(postDto);
    	
//    	ProjectImages image=new ProjectImages();
//    	image.setPost_id(postDto.getPost_id());
//    	image.setImage_path(imageDto.getImage_path());
    	
    	//post.setPost_date(postDto.getPost_date());入れたら日付がnull値になる
    }

	@Transactional
	public void insertImage(ProjectImages imageDto) {
    	
    	projectImagesMapper.insert(imageDto);
	}

	public ProjectPosts findById(long post_id) {
		
		return projectPostsMapper.findById(post_id);
	}
    

//	public ProjectImages findByPath(Long image_id) {
		
//		return projectImagesMapper.findById(image_id);
//	}
	public List<ProjectPosts> search(String keyword) {
	    return projectPostsMapper.search(keyword);
	}
	
	public List<ProjectPosts> findByTagName(String tag){
		return projectPostsMapper.findByTagName(tag);
	}
	

}
