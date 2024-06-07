package hgc.backendblog.blog.controller;

import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hgcBackendBlogs/users/outer/api/blogs/")
@RequiredArgsConstructor
public class BlogUserOuterController {

	private final BlogService blogService;

	public BlogUserOuterController(BlogService blogService) {
		this.blogService = blogService;
	}

	@GetMapping("user/{username}")
	public ResponseEntity<?> getBlogsByUser(@RequestHeader("API-Key") String apiKey,
			@PathVariable("username") String username) {


		List<Blog> blogs = blogService.getBlogsByApiKey(username, apiKey);
		return ResponseEntity.ok(blogs);
	}

}
