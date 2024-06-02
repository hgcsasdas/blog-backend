package hgc.backendblog.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hgc.backendblog.blog.DTO.CommentDeleteDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.DTO.CommentUpdateDto;
import hgc.backendblog.blog.Responses.CommentCUDResponse;
import hgc.backendblog.blog.service.CommentService;

@RestController
@RequestMapping("/hgcBackendBlogs/users/api/blogs/comments")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<CommentCUDResponse> addCommentToBlog(@PathVariable String blogId, @RequestBody CommentDto commentDto) {
        CommentCUDResponse response = commentService.addCommentToBlog(blogId, commentDto);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentCUDResponse> updateComment(@PathVariable String commentId, @RequestBody CommentUpdateDto commentDto) {
        CommentCUDResponse response = commentService.updateComment(commentId, commentDto);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentCUDResponse> deleteComment(@PathVariable String commentId, @RequestBody CommentDeleteDto commentDeleteDto) {
        CommentCUDResponse response = commentService.deleteComment(commentId, commentDeleteDto);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
