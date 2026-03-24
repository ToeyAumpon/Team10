package com.example.demo.mapper;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ProjectPosts {
	private int post_id;
	private Long  user_id;
	private String username;
	private String content;
	private String title;
	private String tag;
	private LocalDateTime post_date;
	private String image_path;
	private List<ProjectImages> images;
}
