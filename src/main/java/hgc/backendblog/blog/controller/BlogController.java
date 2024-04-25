package hgc.backendblog.blog.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Responses.CommentResponse;
import hgc.backendblog.blog.service.BlogService;
import hgc.backendblog.blog.service.BlogServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hgcBackendBlogs/api/blogs/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class BlogController {
	
	@Autowired
	private final BlogService blogService;

	public BlogController(BlogService blogService) {
		this.blogService= blogService;
	}


    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() throws ExecutionException, InterruptedException {
        List<Blog> blogs = blogService.getAllBlogs();
        if (!blogs.isEmpty()) {
            return ResponseEntity.ok(blogs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable String blogId) {
        Blog blog = blogService.getBlogById(blogId);
        if (blog != null) {
            return ResponseEntity.ok(blog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
