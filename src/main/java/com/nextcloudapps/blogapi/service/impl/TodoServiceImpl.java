package com.nextcloudapps.blogapi.service.impl;

import java.util.Collections;
import java.util.List;

import com.nextcloudapps.blogapi.exception.BadRequestException;
import com.nextcloudapps.blogapi.exception.ResourceNotFoundException;
import com.nextcloudapps.blogapi.exception.UnauthorizedException;
import com.nextcloudapps.blogapi.model.todo.Todo;
import com.nextcloudapps.blogapi.model.user.User;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.repository.TodoRepository;
import com.nextcloudapps.blogapi.repository.UserRepository;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import com.nextcloudapps.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nextcloudapps.blogapi.payload.PagedResponse;
import com.nextcloudapps.blogapi.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {
	

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Todo completeTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.TODO, AppConstants.ID, id));
		
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		
		if (todo.getUser().getId().equals(user.getId())) {
			todo.setCompleted(Boolean.TRUE);
			Todo result = todoRepository.save(todo);
			return result;
		}
		
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstants.YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
		
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public Todo unCompleteTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.TODO, AppConstants.ID, id));
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		if (todo.getUser().getId().equals(user.getId())) {
			todo.setCompleted(Boolean.FALSE);
			Todo result = todoRepository.save(todo);
			return result;
		}
		
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstants.YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
		
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size) {
		validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, AppConstants.CREATED_AT);

		Page<Todo> todos = todoRepository.findByCreatedBy(currentUser.getId(), pageable);
		
		List<Todo> content = todos.getNumberOfElements() == 0 ? Collections.emptyList() : todos.getContent();

		return new PagedResponse<>(content, todos.getNumber(), todos.getSize(), todos.getTotalElements(),
				todos.getTotalPages(), todos.isLast());
	}

	@Override
	public Todo addTodo(Todo todo, UserPrincipal currentUser) {
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		todo.setUser(user);
		Todo result = todoRepository.save(todo);
		return result;
	}

	@Override
	public Todo getTodo(Long id, UserPrincipal currentUser) {
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.TODO, AppConstants.ID, id));
		
		if(todo.getUser().getId().equals(user.getId())) {
			return todo;
		}
		
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstants.YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
		
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser) {
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.TODO, AppConstants.ID, id));
		if (todo.getUser().getId().equals(user.getId())) {
			todo.setTitle(newTodo.getTitle());
			todo.setCompleted(newTodo.getCompleted());
			Todo result = todoRepository.save(todo);
			return result;
		}
		
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstants.YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
		
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public ApiResponse deleteTodo(Long id, UserPrincipal currentUser) {
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERNAME, currentUser.getUsername()));
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.TODO, AppConstants.ID, id));

		if (todo.getUser().getId().equals(user.getId())) {
			todoRepository.deleteById(id);
			return new ApiResponse(Boolean.TRUE, "You successfully deleted todo");
		}
		
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstants.YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
		
		throw new UnauthorizedException(apiResponse);
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size < 0) {
			throw new BadRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}
}
