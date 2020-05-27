package com.nextcloudapps.blogapi.controller;

import javax.validation.Valid;

import com.nextcloudapps.blogapi.model.album.Album;
import com.nextcloudapps.blogapi.model.post.Post;
import com.nextcloudapps.blogapi.model.user.User;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.payload.InfoRequest;
import com.nextcloudapps.blogapi.payload.UserIdentityAvailability;
import com.nextcloudapps.blogapi.payload.UserSummary;
import com.nextcloudapps.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nextcloudapps.blogapi.payload.PagedResponse;
import com.nextcloudapps.blogapi.payload.UserProfile;
import com.nextcloudapps.blogapi.security.CurrentUser;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import com.nextcloudapps.blogapi.service.AlbumService;
import com.nextcloudapps.blogapi.service.PostService;
import com.nextcloudapps.blogapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private AlbumService albumService;


	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = userService.getCurrentUser(currentUser);
		
		return new ResponseEntity<UserSummary>(userSummary, HttpStatus.OK);
	}

	@GetMapping("/checkUsernameAvailability")
	public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(@RequestParam(value = "username") String username) {
		UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);
		
		return new ResponseEntity<UserIdentityAvailability>(userIdentityAvailability, HttpStatus.OK);
	}

	@GetMapping("/checkEmailAvailability")
	public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(@RequestParam(value = "email") String email) {
		UserIdentityAvailability userIdentityAvailability =  userService.checkEmailAvailability(email);
		return new ResponseEntity<UserIdentityAvailability>(userIdentityAvailability, HttpStatus.OK);
	}

	@GetMapping("/{username}/profile")
	public ResponseEntity<UserProfile> getUSerProfile(@PathVariable(value = "username") String username) {
		UserProfile userProfile = userService.getUserProfile(username);
		
		return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
	}

	@GetMapping("/{username}/posts")
	public ResponseEntity<PagedResponse<Post>> getPostsCreatedBy(@PathVariable(value = "username") String username,
                                                                 @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                 @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		PagedResponse<Post> response = postService.getPostsByCreatedBy(username, page, size);
		
		return new ResponseEntity<PagedResponse<Post>>(response, HttpStatus.OK);
	}

	@GetMapping("/{username}/albums")
	public ResponseEntity<PagedResponse<Album>> getUserAlbums(@PathVariable(name = "username") String username,
                                                              @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                              @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Album> response = albumService.getUserAlbums(username, page, size);
		
		return new ResponseEntity<PagedResponse<Album>>(response, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User newUser = userService.addUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
			@PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
		User updatedUSer = userService.updateUser(newUser, username, currentUser);
		
		return new ResponseEntity<User>(updatedUSer, HttpStatus.CREATED);
	}

	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                                  @CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = userService.deleteUser(username, currentUser);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{username}/giveAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.giveAdmin(username);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{username}/takeAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.removeAdmin(username);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/setOrUpdateInfo")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserProfile> setAddress(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody InfoRequest infoRequest) {
		UserProfile userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);
		
		return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
	}

}
