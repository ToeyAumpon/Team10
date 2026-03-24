package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectPostsMapper {

	
	 ProjectPosts findById(long post_id);
	
	//投稿保存
	void insert(ProjectPosts post);
	
//	//投稿IDで1件取得
//	ProjectPosts findByID(Long post_id);
//	
//	//全投稿を取得
	List<ProjectPosts> findAll();
//	
//	//ユーザーIDで投稿一覧を取得
//	List<ProjectPosts> findByUserId(Long user_id);


    // IDで取得
    ProjectPosts findById(@Param("post_id") Long post_id);


	List<ProjectPosts> findByUsername(String name);
	
	
	List<ProjectPosts> findByTagName(@Param("tag") String tag);

	//List<ProjectPosts> findByUsername(String username);
    // 新着順
    List<ProjectPosts> findAllOrderByDateDesc();


    // 古い順
    List<ProjectPosts> findAllOrderByDateAsc();

    
    
    // ユーザーIDで取得
    List<ProjectPosts> findByUserId(@Param("user_id") Long user_id);

    // ユーザー名で取得
//    List<ProjectPosts> findByUsername(@Param("username") String username);

    // 検索
    List<ProjectPosts> search(@Param("keyword") String keyword);
}
