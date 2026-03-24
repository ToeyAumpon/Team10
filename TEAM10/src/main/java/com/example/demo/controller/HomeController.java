package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.ProjectImages;
import com.example.demo.mapper.ProjectImagesMapper;
import com.example.demo.mapper.ProjectPosts;
import com.example.demo.mapper.ProjectPostsMapper;
import com.example.demo.mapper.ProjectTags;
import com.example.demo.mapper.ProjectTagsMapper;
import com.example.demo.mapper.ProjectUser;
import com.example.demo.service.PostService;
import com.example.demo.service.postLogic;
import com.example.demo.service.userLogic;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Principal principal, Model model, HttpSession session,Authentication auths) {
        model.addAttribute("username", principal != null ? principal.getName() : "anonymous");
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        // ADMINチェック
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            boolean isAdmin = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch("ROLE_ADMIN"::equals);
            System.out.println("isAdmin: " + isAdmin);
        }
    	if (auths != null && auths.isAuthenticated()
    			&& !"anonymousUser".equals(auths.getPrincipal())) {
    		return "redirect:/itirann";
    		}
        
        return "itirann";
    }
    
    private final userLogic userLogic;
    private final postLogic postLogic;
    private final PostService postService;
    private final ProjectPostsMapper projectPostsMapper;
    private final ProjectImagesMapper projectImagesMapper;
    private final ProjectTagsMapper projectTagsMapper;
    public HomeController(userLogic userLogic, postLogic postLogic, PostService postService, ProjectPostsMapper projectPostsMapper, ProjectImagesMapper projectImagesMapper, ProjectTagsMapper projectTagsMapper) {
        this.userLogic = userLogic;
        this.postLogic = postLogic;
        this.postService = postService;
        this.projectPostsMapper = projectPostsMapper;
        this.projectImagesMapper = projectImagesMapper;
        this.projectTagsMapper = projectTagsMapper;
    }
    
    
   
    @PostMapping("/sign_up")
    public String register(ProjectUser userDto, Model model) {

        try {
            userLogic.insert(userDto);
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
        	model.addAttribute("usernameError",e.getMessage());
            model.addAttribute("emailError", e.getMessage());
            return "sign_up";
        }
    }

    
    
    @PostMapping("/sinki")
    public String sinki(ProjectPosts postDto, @RequestParam("image") MultipartFile[] files ,HttpSession session)throws IOException {
    	//ログイン処理
    	String email = (String) session.getAttribute("email");
    	ProjectUser user = userLogic.findByUsername(email);
    	postDto.setUser_id(user.getUser_id());
    	postDto.setUsername(user.getUsername());
    	
    
    	
    	 if(files.length > 6) {
     		throw new IllegalArgumentException("画像は6枚までです");
     	}
    	
//      //画像保存処理
    	String uploadDir = "src/main/resources/static/images/";
    	//Files.createDirectories(Paths.get(uploadDir));
    	
    	postLogic.insertPost(postDto);
    	//postDto.setImage_path(fileName);
    	
    	//タグ登録処理
    	String tagName = postDto.getTag();
    	ProjectTags tag = projectTagsMapper.findByTagName(tagName);
    	if(tag == null) {
    		tag = new ProjectTags();
    		tag.setTag_name(tagName);
    		projectTagsMapper.insert(tag);
    	}
    	
    	//投稿とタグの紐づけ
    	projectTagsMapper.insertPostTag(postDto.getPost_id(), tag.getTag_id());
    	
    	
    	//imageDto を作成して image_path をセット
    	for (MultipartFile file : files) {

    	    if (file.isEmpty()) {
    	        continue; // ← 空ファイルはスキップ
    	    }

    	    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    	    Path path = Paths.get(uploadDir).resolve(fileName);
    	    Files.copy(file.getInputStream(), path);
    	    
    	    ProjectImages imageDto = new ProjectImages();
    	    imageDto.setPost_id(postDto.getPost_id());
    	    imageDto.setImage_path(fileName);
    	    postLogic.insertImage(imageDto);
    	    
    	   
    	}
    	
    	
    	
    	//DB登録
    	
    	System.out.print(postDto);
    	return "redirect:/itirann";

    }
    
    
    

    @GetMapping("/login")
    public String login(Authentication auth) {
    	if (auth != null && auth.isAuthenticated()
    			&& !"anonymousUser".equals(auth.getPrincipal())) {
    		return "redirect:/";
    		}
    	return "login";
    }
    
    @GetMapping("/sign_up")
	public String signUp(Model model,Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal())) {
        	return "redirect:/";
        }
    	model.addAttribute("projectUser", new ProjectUser());
		return "sign_up";
	}
    
    @GetMapping("/sinki")
    public String sinki(Model model) {
    	model.addAttribute("projectPost", new ProjectPosts());
    	return "sinki";
    }
	
    
    @GetMapping("/screen1")
    public String screen1() {
        return "screen1";
    }
    @GetMapping("/screen2")
    public String screen2() {
        return "screen2";
    }
        
    
    @GetMapping("/itirann")
    public String list(
            @RequestParam(name = "sort", required = false, defaultValue = "desc") String sort,
            Model model) {

        List<ProjectPosts> posts = projectPostsMapper.findAll();

        // ⭐ ONLY ADD THIS SORT PART
        if (sort.equals("asc")) {
            posts.sort(Comparator.comparing(ProjectPosts::getPost_date));
        } else {
            posts.sort(Comparator.comparing(ProjectPosts::getPost_date).reversed());
        }

        for(ProjectPosts post : posts) {
            List<ProjectImages> images = projectImagesMapper.findByPostId(post.getPost_id());
            post.setImages(images);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort); // ⭐ REQUIRED for toggle

        return "itirann";
    }


//    @GetMapping("/sinki")
//    public String sinki() {
//    	return "sinki";
//    }
    
 @GetMapping("/result")
 public String result(
            @RequestParam(name = "kensaku", required = false) String keyword,
            Model model,Authentication auth) {
    	//System.out.print(keyword);
        if (keyword == null || keyword.isBlank()) {
            model.addAttribute("message", "No data");
            model.addAttribute("posts", List.of()); 
            return "result";
        }
        
//       	if (auth != null && auth.isAuthenticated()
//    			&& !"anonymousUser".equals(auth.getPrincipal())) {
//    		return "redirect:/";
//    	}

        model.addAttribute("keyword", keyword);
        //model.addAttribute("posts", postLogic.search(keyword));
        List<ProjectPosts> posts = projectPostsMapper.search(keyword);
		
		for(ProjectPosts post : posts) {
			List<ProjectImages> images = projectImagesMapper.findByPostId(post.getPost_id());
			post.setImages(images);
		}
		
		System.out.println("検索キーワード: " + keyword);
    	model.addAttribute("posts", posts);
        
        return "result";


    }
    
    @GetMapping("/result_tags/{tag}")
    public String result_tags(@PathVariable String tag, Model model) {
        
        model.addAttribute("tag_name", tag);

        List<ProjectPosts> posts = projectPostsMapper.findByTagName(tag);
        for(ProjectPosts post : posts) {
            List<ProjectImages> images = projectImagesMapper.findByPostId(post.getPost_id());
            post.setImages(images);
        }

        model.addAttribute("posts", posts);
        return "result_tags";
    }

    

    @GetMapping("/toukousya/{name}")
    public String toukousyaPage(@PathVariable("name") String name, Model model) {

        System.out.println("投稿者ページ: " + name);

        // 投稿取得
        List<ProjectPosts> posts = projectPostsMapper.findByUsername(name);

        System.out.println("投稿数: " + posts.size());

        // 画像取得
        for(ProjectPosts post : posts) {
            List<ProjectImages> images =
                    projectImagesMapper.findByPostId(post.getPost_id());
            post.setImages(images);
        }

        model.addAttribute("username", name);
        model.addAttribute("posts", posts);

        return "toukousya";
    }



    
}
