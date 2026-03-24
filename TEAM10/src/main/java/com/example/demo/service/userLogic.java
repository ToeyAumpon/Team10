package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.ProjectUser;
import com.example.demo.mapper.ProjectUserMapper;

@Service

public class userLogic{
	private final ProjectUserMapper projectUserMapper;

    public userLogic(ProjectUserMapper projectUserMapper) {
        this.projectUserMapper = projectUserMapper;
    }

    public void insert(ProjectUser userDto) {
    	
        if (projectUserMapper.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("入力された情報は既に使用されているか、無効な形式です");
        }
        if (projectUserMapper.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("入力されたユーザー名は既に使用されています");
        }        
        
        ProjectUser user = new ProjectUser();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEnabled(true);
        user.setRoles("USER");
        
        projectUserMapper.insert(user);
    }

	public ProjectUser findByUsername(String email) {
		
		return projectUserMapper.findByUsername(email);
	}


    
    
    
}
