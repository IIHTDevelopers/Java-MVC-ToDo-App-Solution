package com.yaksha.training.todo.service;

import com.yaksha.training.todo.entity.TaskStatus;
import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Page<Todo> getTodos(Pageable pageable) {
        Page<Todo> todos = todoRepository.findAllTodo(pageable);
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

    public Page<Todo> searchTodos(String theSearchName, LocalDate theSearchDate, Pageable pageable) {
        if ((theSearchName != null && theSearchName.trim().length() > 0) || theSearchDate !=null) {
            return todoRepository.findByTaskAndDescAndDate(theSearchName, theSearchDate, pageable);
        } else {
            return todoRepository.findAllTodo(pageable);
        }
    }

    public boolean updateTodoStatus(Long id, String status) {
        todoRepository.updateTodoStatus(id, status);
        return true;
    }
}
