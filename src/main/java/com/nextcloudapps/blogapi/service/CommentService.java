package com.nextcloudapps.blogapi.service;

import com.nextcloudapps.blogapi.model.comment.Comment;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.payload.CommentRequest;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import com.nextcloudapps.blogapi.payload.PagedResponse;

public interface CommentService {

	PagedResponse<Comment> getAllComments(Long postId, int page, int size);

	Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

	Comment getComment(Long postId, Long id);

	Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

	ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}