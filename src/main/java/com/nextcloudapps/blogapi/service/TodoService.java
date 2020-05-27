package com.nextcloudapps.blogapi.service;

import com.nextcloudapps.blogapi.model.todo.Todo;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.security.UserPrincipal;
import com.nextcloudapps.blogapi.payload.PagedResponse;

public interface TodoService {

	Todo completeTodo(Long id, UserPrincipal currentUser);

	Todo unCompleteTodo(Long id, UserPrincipal currentUser);

	PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

	Todo addTodo(Todo todo, UserPrincipal currentUser);

	Todo getTodo(Long id, UserPrincipal currentUser);

	Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

	ApiResponse deleteTodo(Long id, UserPrincipal currentUser);

}