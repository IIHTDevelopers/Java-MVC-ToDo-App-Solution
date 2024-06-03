package com.yaksha.training.todo.repository;

import com.yaksha.training.todo.entity.TaskStatus;
import com.yaksha.training.todo.entity.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t")
    Page<Todo> findAllTodo(Pageable pageable);

    @Query(value = "Select c from Todo c where (:keyword IS NULL OR lower(task) like %:keyword% " +
            "or lower(description) like %:keyword% )" +
            "and (:taskDate IS NULL OR c.taskDate = :taskDate)")
    Page<Todo> findByTaskAndDescAndDate(@Param("keyword") String keyword,
                                        @Param("taskDate") LocalDate taskDate,
                                        Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE todo SET status = :status where id = :id", nativeQuery = true)
    void updateTodoStatus(@Param("id") Long id, @Param("status") String status);

}
