package com.example.demo.mapper;

public interface ProjectUserMapper {

    ProjectUser findByUsername(String email);
    
    void insert(ProjectUser user);
    
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
