package com.yaksha.training.todo.functional;

import com.yaksha.training.todo.controller.TodoController;
import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.service.TodoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.yaksha.training.todo.utils.MasterData.asJsonString;
import static com.yaksha.training.todo.utils.MasterData.getTodo;
import static com.yaksha.training.todo.utils.MasterData.getTodoList;
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
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testControllerListTodosHome() throws Exception {
        List<Todo> expected = getTodoList(5);
        when(todoService.getTodos()).thenReturn(expected);
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
        when(todoService.getTodos()).thenReturn(expected);
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
    public void testControllerSearchTodos() throws Exception {
        String key = randomStringWithSize(2);
        List<Todo> expected = getTodoList(5);
        when(todoService.searchTodos(key)).thenReturn(expected);
        MvcResult result = this.mockMvc.perform(post("/search")
                .param("theSearchName", key)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                        && result.getModelAndView().getViewName() != null
                        && result.getModelAndView().getViewName().contentEquals("list-todos")
                        && asJsonString(expected).equals(asJsonString(result.getModelAndView().getModel().get("todos")))
                        ? "true"
                        : "false",
                businessTestFile);
    }

}
