package com.example.demo.mapper;

import java.util.List;

public interface ProjectImagesMapper {

    // 画像を保存
    void insert(ProjectImages post_id);

    // 投稿IDに紐づく画像一覧を取得
    List<ProjectImages> findByPostId(long post_id);

    // 画像IDで1件取得（必要なら）
//    ProjectImages findById(Long image_id);

}
