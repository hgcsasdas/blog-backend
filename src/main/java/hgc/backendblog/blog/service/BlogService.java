package hgc.backendblog.blog.service;

import java.util.List;

import hgc.backendblog.blog.DTO.BlogDto;
import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Responses.BlogCUDResponse;

public interface BlogService {

    List<Blog> getAllBlogs();

    Blog getBlogById(String blogId);

    BlogCUDResponse createBlog(BlogDto blogDto);

    BlogCUDResponse updateBlog(String blogId, BlogDto blogDto);

    BlogCUDResponse deleteBlog(String blogId);

    List<Blog> getBlogsByUser(String user);
    
    List<Blog> getBlogsByApiKey(String username, String apiKey);

}
