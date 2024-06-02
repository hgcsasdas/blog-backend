package hgc.backendblog.blog.service;

import hgc.backendblog.blog.DTO.CommentDeleteDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.DTO.CommentUpdateDto;
import hgc.backendblog.blog.Responses.CommentCUDResponse;

public interface CommentService {

    CommentCUDResponse addCommentToBlog(String blogId, CommentDto commentDto);

    CommentCUDResponse updateComment(String commentId, CommentUpdateDto commentDto);

    CommentCUDResponse deleteComment(String commentId, CommentDeleteDto commentDeleteDto);

}
