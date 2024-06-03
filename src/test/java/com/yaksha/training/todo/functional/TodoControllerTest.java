package com.yaksha.training.todo.functional;

import com.yaksha.training.todo.controller.TodoController;
import com.yaksha.training.todo.entity.TaskStatus;
import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.service.TodoService;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testControllerListTodosHome() throws Exception {
        List<Todo> expected = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(expected);

        when(todoService.getTodos(pageable)).thenReturn(todoPage);
        MvcResult result = this.mockMvc.perform(get("/")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                        && result.getModelAndView().getViewName() != null
                        && result.getModelAndView().getViewName().contentEquals("list-todos")
                        && asJsonString(expected).equals(asJsonString(result.getModelAndView().getModel().get("todos")))
                        ? "true"
                        : "false"
                , businessTestFile);
    }

    @Test
    public void testControllerListTodos() throws Exception {
        List<Todo> expected = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(expected);

        when(todoService.getTodos(pageable)).thenReturn(todoPage);
        MvcResult result = this.mockMvc.perform(get("/list")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                        && result.getModelAndView().getViewName() != null
                        && result.getModelAndView().getViewName().contentEquals("list-todos")
                        && asJsonString(expected).equals(asJsonString(result.getModelAndView().getModel().get("todos")))
                        ? "true"
                        : "false"
                , businessTestFile);
    }

    @Test
    public void testControllerShowFormForAdd() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/addTodoForm")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("add-todo-form"), businessTestFile);
    }

    @Test
    public void testControllerSaveTodo() throws Exception {
        Todo todo = getTodo();
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/todo/list"), businessTestFile);
    }

    @Test
    public void testControllerSaveTodoHasError() throws Exception {
        Todo todo = getTodo();
        todo.setId(null);
        todo.setTask(null);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("add-todo-form"), businessTestFile);
    }

    @Test
    public void testControllerUpdateTodoHasError() throws Exception {
        Todo todo = getTodo();
        todo.setDescription(null);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("update-todo-form"), businessTestFile);
    }

    @Test
    public void testControllerShowFormForUpdate() throws Exception {
        Todo todo = getTodo();
        when(todoService.getTodo(todo.getId())).thenReturn(todo);
        MvcResult result = this.mockMvc.perform(get("/updateTodoForm")
                .param("todoId", todo.getId().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("update-todo-form"), businessTestFile);
    }

    @Test
    public void testControllerDeleteTodo() throws Exception {
        Todo todo = getTodo();
        MvcResult result = this.mockMvc.perform(get("/delete")
                .param("todoId", todo.getId().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/todo/list"), businessTestFile);
    }

    @Test
    public void testControllerSearchTodosWithSearchValueAndDate() throws Exception {
        String key = randomStringWithSize(2);
        List<Todo> expected = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(expected);

        when(todoService.searchTodos(key, LocalDate.now(), pageable)).thenReturn(todoPage);
        MvcResult result = this.mockMvc.perform(post("/search")
                .param("theSearchName", key)
                .param("theSearchDate", LocalDate.now().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                        && result.getModelAndView().getViewName() != null
                        && result.getModelAndView().getViewName().contentEquals("list-todos")
                        && asJsonString(expected).equals(asJsonString(result.getModelAndView().getModel().get("todos")))
                        ? "true"
                        : "false",
                businessTestFile);
    }

    @Test
    public void testControllerSearchTodosWithNullValues() throws Exception {
        List<Todo> expected = getTodoList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Todo> todoPage = new PageImpl<>(expected);

        when(todoService.searchTodos(null, null, pageable)).thenReturn(todoPage);
        MvcResult result = this.mockMvc.perform(post("/search")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                        && result.getModelAndView().getViewName() != null
                        && result.getModelAndView().getViewName().contentEquals("list-todos")
                        && asJsonString(expected).equals(asJsonString(result.getModelAndView().getModel().get("todos")))
                        ? "true"
                        : "false",
                businessTestFile);
    }

    @Test
    public void testControllerUpdateStatus() throws Exception {
        Todo todo = getTodo();
        String status = randomBoolean() ? TaskStatus.COMPLETED.toString() : TaskStatus.DECLINE.toString();

        MvcResult result = this.mockMvc.perform(get("/updateStatus")
                        .param("status", status)
                        .param("todoId", todo.getId().toString()))
                .andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/todo/list"), businessTestFile);
    }

}
