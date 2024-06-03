package com.yaksha.training.todo.service;

import com.yaksha.training.todo.entity.TaskStatus;
import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.repository.TodoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.yaksha.training.todo.utils.MasterData.asJsonString;
import static com.yaksha.training.todo.utils.MasterData.getTodo;
import static com.yaksha.training.todo.utils.MasterData.getTodoList;
import static com.yaksha.training.todo.utils.MasterData.randomBoolean;
import static com.yaksha.training.todo.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.todo.utils.TestUtils.businessTestFile;
import static com.yaksha.training.todo.utils.TestUtils.currentTest;
import static com.yaksha.training.todo.utils.TestUtils.testReport;
import static com.yaksha.training.todo.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testServiceGetTodos() throws Exception {
        List<Todo> actual = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(actual);

        when(todoRepository.findAllTodo(pageable)).thenReturn(todoPage);
        Page<Todo> expected = todoService.getTodos(pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSaveTodo() throws Exception {
        Todo actual = getTodo();
        when(todoRepository.save(actual)).thenReturn(actual);
        Todo expected = todoService.saveTodo(actual);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceGetTodo() throws Exception {
        Todo actual = getTodo();
        when(todoRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Todo expected = todoService.getTodo(actual.getId());
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceDeleteTodo() throws Exception {
        Todo actual = getTodo();
        boolean expected = todoService.deleteTodo(actual.getId());
        yakshaAssert(currentTest(),
                (expected ? true : false),
                businessTestFile);
    }

    @Test
    public void testServiceSearchTodoWithNull() throws Exception {
        List<Todo> actual = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(actual);

        when(todoRepository.findAllTodo(pageable)).thenReturn(todoPage);
        Page<Todo> expected = todoService.searchTodos(null, null, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSearchTodoWithSearchName() throws Exception {
        String searchKey = randomStringWithSize(2);
        List<Todo> actual = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(actual);

        when(todoRepository.findByTaskAndDescAndDate(searchKey, LocalDate.now(), pageable)).thenReturn(todoPage);
        Page<Todo> expected = todoService.searchTodos(searchKey, LocalDate.now(), pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceUpdateStatus() throws Exception {
        Todo actual = getTodo();
        String status = randomBoolean() ? TaskStatus.COMPLETED.toString() : TaskStatus.DECLINE.toString();
        boolean expected = todoService.updateTodoStatus(actual.getId(), status);
        yakshaAssert(currentTest(),
                (expected ? true : false),
                businessTestFile);
    }
}
