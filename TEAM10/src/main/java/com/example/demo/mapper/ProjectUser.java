package com.example.demo.mapper;

import lombok.Data;

@Data
public class ProjectUser {
	
    //private Long id;  // ★必須

    private Long user_id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private String roles;
}


//public class POSTS {
//	private int post_id;
//	private int  user_id;
//	private String text;
//	private String title;
//	private LocalDateTime post_date;
//	
//}
//
//public class IMAGES {
//	private int image_id;
//	private int post_id;
//	private String image_path;
//	
//}
//
//public class TAGS {
//	private int post_id;
//	private int user_id;
//}
//
//public class POST_TAGS {
//	private String tag_name;
//	private int tag_id;
//}
