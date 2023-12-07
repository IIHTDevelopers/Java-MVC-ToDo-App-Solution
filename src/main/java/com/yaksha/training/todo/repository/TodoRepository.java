package com.yaksha.training.todo.repository;

import com.yaksha.training.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "Select c from Todo c where lower(task) like %:keyword%")
    List<Todo> findByTask(@Param("keyword") String keyword);
}
