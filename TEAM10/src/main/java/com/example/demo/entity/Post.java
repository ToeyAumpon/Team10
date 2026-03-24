package com.example.demo.entity;

public class Post {
	
	private long post_id; // 投稿ID
	private String title;  // タイトル
	private String content;  // 内容
	private String tag;  // タグ
	private String image_path;
//	private String image1; // 画像1のパス
//	private String image2; // 画像2のパス
	//private String post_date; // 作成日時

	// --- Getter & Setter ---


	public long getPost_id() {
		return post_id;
	}
	public void setPost_id(long post_id) {	
		this.post_id = post_id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

//	public String getImage2() {
//		return image2;
//	}
//	public void setImage2(String image2) {
//		this.image2 = image2;
//	}

//	public String getPost_date() {
//		return post_date;
//	}
//	public void setPOst_date(String post_date) {
//		this.post_date = post_date;
//	}
	
}
