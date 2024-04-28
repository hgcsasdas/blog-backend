package hgc.backendblog.blog.service;

import java.util.List;

import hgc.backendblog.blog.DTO.BlogDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Responses.BlogCUDResponse;

public interface BlogService {

    List<Blog> getAllBlogs();

    Blog getBlogById(String blogId);

    BlogCUDResponse createBlog(BlogDto blogDto);

    Blog updateBlog(String blogId, BlogDto blogDto);

    void deleteBlog(String blogId);

    Blog addCommentToBlog(String blogId, CommentDto commentDto);

}
