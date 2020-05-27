package com.nextcloudapps.blogapi.service;

import com.nextcloudapps.blogapi.exception.UnauthorizedException;
import com.nextcloudapps.blogapi.model.category.Category;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import com.nextcloudapps.blogapi.payload.PagedResponse;

public interface CategoryService {

	PagedResponse<Category> getAllCategories(int page, int size);

	ResponseEntity<Category> getCategory(Long id);

	ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

	ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
			throws UnauthorizedException;

	ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;

}