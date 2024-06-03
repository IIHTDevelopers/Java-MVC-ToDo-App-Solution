package com.yaksha.training.todo.exception;

import com.yaksha.training.todo.controller.TodoController;
import com.yaksha.training.todo.entity.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static com.yaksha.training.todo.utils.MasterData.getTodo;
import static com.yaksha.training.todo.utils.TestUtils.currentTest;
import static com.yaksha.training.todo.utils.TestUtils.exceptionTestFile;
import static com.yaksha.training.todo.utils.TestUtils.testReport;
import static com.yaksha.training.todo.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TodoExceptionTest {

    @InjectMocks
    private TodoController todoController;

    private MockMvc mockMvc;

    BindingResult bindingResult = mock(BindingResult.class);

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
    public void testExceptionSaveTodoTaskAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setId(null);
        todo.setTask(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("add-todo-form")), exceptionTestFile);
    }

    @Test
    public void testExceptionSaveTodoDescAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setId(null);
        todo.setDescription(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("add-todo-form")), exceptionTestFile);
    }

    @Test
    public void testExceptionSaveTodoDateAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setId(null);
        todo.setTaskDate(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("add-todo-form")), exceptionTestFile);
    }

    @Test
    public void testExceptionUpdateTodoTaskAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setTask(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("update-todo-form")), exceptionTestFile);
    }

    @Test
    public void testExceptionUpdateTodoDescAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setDescription(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("update-todo-form")), exceptionTestFile);
    }

    @Test
    public void testExceptionUpdateTodoDateAsNull() throws Exception {
        Todo todo = getTodo();
        todo.setTaskDate(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveTodo")
                .flashAttr("todo", todo)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("update-todo-form")), exceptionTestFile);
    }


}