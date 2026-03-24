package com.example.demo.mapper;

import java.util.List;

public interface ProjectPost_TagsMapper {
	
    // 投稿にタグを紐づける
    void insert(ProjectPost_Tags pt);

    // 投稿に紐づくタグ一覧を取得
    List<ProjectPost_Tags> findByPostId(Long post_id);

    // タグに紐づく投稿一覧を取得（必要なら）
    List<ProjectPost_Tags> findByTagId(Long tag_id);

}
