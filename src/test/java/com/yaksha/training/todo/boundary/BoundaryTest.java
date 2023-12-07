package com.yaksha.training.todo.boundary;


import com.yaksha.training.todo.entity.Todo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.yaksha.training.todo.utils.MasterData.getTodo;
import static com.yaksha.training.todo.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.todo.utils.TestUtils.boundaryTestFile;
import static com.yaksha.training.todo.utils.TestUtils.currentTest;
import static com.yaksha.training.todo.utils.TestUtils.testReport;
import static com.yaksha.training.todo.utils.TestUtils.yakshaAssert;

@ExtendWith(SpringExtension.class)
public class BoundaryTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void afterAll() {
        testReport();
    }

    @Test
    public void testTodoTaskNotBlank() throws Exception {
        Todo todo = getTodo();
        todo.setTask("");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskNotNull() throws Exception {
        Todo todo = getTodo();
        todo.setTask(null);
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskMinTwo() throws Exception {
        Todo todo = getTodo();
        todo.setTask(randomStringWithSize(1));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskMaxForty() throws Exception {
        Todo todo = getTodo();
        todo.setTask(randomStringWithSize(41));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoDescriptionNotBlank() throws Exception {
        Todo todo = getTodo();
        todo.setDescription("");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoDescriptionNotNull() throws Exception {
        Todo todo = getTodo();
        todo.setDescription(null);
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoDescriptionMinTwo() throws Exception {
        Todo todo = getTodo();
        todo.setDescription(randomStringWithSize(1));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoDescriptionMaxTwoHundred() throws Exception {
        Todo todo = getTodo();
        todo.setDescription(randomStringWithSize(201));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskDateNotBlank() throws Exception {
        Todo todo = getTodo();
        todo.setTaskDate("");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskDateNotNull() throws Exception {
        Todo todo = getTodo();
        todo.setTaskDate(null);
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskDateInValidFormat() throws Exception {
        Todo todo = getTodo();
        todo.setTaskDate(randomStringWithSize(10));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskDateValidFormat() throws Exception {
        Todo todo = getTodo();
        todo.setTaskDate("2023-01-01");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? false : true, boundaryTestFile);
    }

    @Test
    public void testTodoTaskTimeNotBlank() throws Exception {
        Todo todo = getTodo();
        todo.setTaskTime("");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskTimeNotNull() throws Exception {
        Todo todo = getTodo();
        todo.setTaskTime(null);
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskTimeInValidFormat() throws Exception {
        Todo todo = getTodo();
        todo.setTaskTime(randomStringWithSize(10));
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testTodoTaskTimeValidFormat() throws Exception {
        Todo todo = getTodo();
        todo.setTaskTime("11:00");
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        yakshaAssert(currentTest(), !violations.isEmpty() ? false : true, boundaryTestFile);
    }


}
