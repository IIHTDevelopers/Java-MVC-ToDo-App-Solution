package com.yaksha.training.todo.service;

import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos;
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo getTodo(Long id) {
        return todoRepository.findById(id).get();
    }

    public boolean deleteTodo(Long id) {
        todoRepository.deleteById(id);
        return true;
    }

    public List<Todo> searchTodos(String theSearchName) {
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            return todoRepository.findByTask(theSearchName);
        } else {
            return todoRepository.findAll();
        }
    }
}
