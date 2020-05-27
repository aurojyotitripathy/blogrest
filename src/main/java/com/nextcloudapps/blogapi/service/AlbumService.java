package com.nextcloudapps.blogapi.service;

import com.nextcloudapps.blogapi.model.album.Album;
import com.nextcloudapps.blogapi.payload.AlbumResponse;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.payload.request.AlbumRequest;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import com.nextcloudapps.blogapi.payload.PagedResponse;

public interface AlbumService {

	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

	ResponseEntity<Album> getAlbum(Long id);

	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

	ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

	PagedResponse<Album> getUserAlbums(String username, int page, int size);

}