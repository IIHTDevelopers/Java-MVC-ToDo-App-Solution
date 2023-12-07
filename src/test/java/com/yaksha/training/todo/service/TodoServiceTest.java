package com.yaksha.training.todo.service;

import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.repository.TodoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.yaksha.training.todo.utils.MasterData.asJsonString;
import static com.yaksha.training.todo.utils.MasterData.getTodo;
import static com.yaksha.training.todo.utils.MasterData.getTodoList;
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
        when(todoRepository.findAll()).thenReturn(actual);
        List<Todo> expected = todoService.getTodos();
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
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
        when(todoRepository.findAll()).thenReturn(actual);
        List<Todo> expected = todoService.searchTodos(null);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSearchTodoWithSearchName() throws Exception {
        String searchKey = randomStringWithSize(2);
        List<Todo> actual = getTodoList(5);
        when(todoRepository.findByTask(searchKey)).thenReturn(actual);
        List<Todo> expected = todoService.searchTodos(searchKey);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

}
