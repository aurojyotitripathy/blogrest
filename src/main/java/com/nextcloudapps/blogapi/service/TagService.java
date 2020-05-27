package com.nextcloudapps.blogapi.service;

import com.nextcloudapps.blogapi.model.tag.Tag;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import com.nextcloudapps.blogapi.payload.PagedResponse;

public interface TagService {

	PagedResponse<Tag> getAllTags(int page, int size);

	Tag getTag(Long id);

	Tag addTag(Tag tag, UserPrincipal currentUser);

	Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser);

	ApiResponse deleteTag(Long id, UserPrincipal currentUser);

}