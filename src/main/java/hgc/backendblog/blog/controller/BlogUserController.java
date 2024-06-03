package hgc.backendblog.blog.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hgc.backendblog.blog.DTO.BlogDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Responses.BlogCUDResponse;
import hgc.backendblog.blog.service.BlogService;

@RestController
@RequestMapping("/hgcBackendBlogs/users/api/blogs")
@CrossOrigin(origins = { "http://localhost:4200" })
public class BlogUserController {

    private final BlogService blogService;

    public BlogUserController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<BlogCUDResponse> createBlog(@RequestBody BlogDto blogDto) {
        BlogCUDResponse createdBlog = blogService.createBlog(blogDto);
        if (createdBlog != null) {
            return ResponseEntity.ok(createdBlog);
        } else {
            return ResponseEntity.notFound().build();
        }    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
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

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogCUDResponse> updateBlog(@PathVariable String blogId, @RequestBody BlogDto blogDto) {
        BlogCUDResponse updatedBlog = blogService.updateBlog(blogId, blogDto);
        if (updatedBlog != null) {
            return ResponseEntity.ok(updatedBlog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<BlogCUDResponse> deleteBlog(@PathVariable String blogId) {
        BlogCUDResponse response = blogService.deleteBlog(blogId);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{user}")
    public ResponseEntity<List<Blog>> getBlogsByUserId(@PathVariable String user) {
        List<Blog> blogs = blogService.getBlogsByUser(user);
        if (blogs != null && !blogs.isEmpty()) {
            return ResponseEntity.ok(blogs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
