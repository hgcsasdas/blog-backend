package hgc.backendblog.blog.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import hgc.backendblog.blog.service.BlogService;
import hgc.backendblog.blog.service.BlogServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hgcBackendBlogs/users/api/blogs")
@CrossOrigin(origins = { "http://localhost:4200" })
public class BlogUserController {

    private final BlogService blogService;

    @Autowired
    public BlogUserController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody BlogDto blogDto) {
    	System.out.println("entré");
        Blog createdBlog = blogService.createBlog(blogDto);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

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
    public ResponseEntity<Blog> updateBlog(@PathVariable String blogId, @RequestBody BlogDto blogDto) {
        Blog updatedBlog = blogService.updateBlog(blogId, blogDto);
        if (updatedBlog != null) {
            return ResponseEntity.ok(updatedBlog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{blogId}/comments")
    public ResponseEntity<Blog> addCommentToBlog(@PathVariable String blogId, @RequestBody CommentDto commentDto) {
        Blog updatedBlog = blogService.addCommentToBlog(blogId, commentDto);
        if (updatedBlog != null) {
            return ResponseEntity.ok(updatedBlog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
