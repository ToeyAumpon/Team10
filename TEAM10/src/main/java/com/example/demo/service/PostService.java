package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.mapper.PostMapper;

@Service
public class PostService {
	
	
	@Autowired
	private PostMapper postMapper;
	

	public void save(Post post) {
		
		postMapper.insert(post);
//	System.out.println("★★ save() 呼ばれた ★★");
//
//	Post post = new Post();
//	post.setTitle(title);
//	post.setContent(content);
//	post.setTag(tag);
//
//	post.setImage_path(null);
////	post.setImage2(null);
//
//	System.out.println("★★ insert 実行前 ★★");
//	postMapper.insert(post);
//	System.out.println("★★ insert 実行後 ★★");
	
	}

	
	public List<Post> findAll(){
		return postMapper.findAll();
	}



}
