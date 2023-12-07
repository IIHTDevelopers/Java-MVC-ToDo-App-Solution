package com.yaksha.training.todo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaksha.training.todo.entity.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasterData {

    private static Random rnd = new Random();

    public static Todo getTodo() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTask(randomStringWithSize(10));
        todo.setDescription(randomStringWithSize(10));
        todo.setTaskDate("2023-01-01");
        todo.setTaskTime("11:01");
        return todo;
    }

    public static List<Todo> getTodoList(int size) {
        Long id = 0L;
        List<Todo> todos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Todo todo = new Todo();
            todo.setId(++id);
            todo.setTask(randomStringWithSize(10));
            todo.setDescription(randomStringWithSize(10));
            todo.setTaskDate("2023-01-01");
            todo.setTaskTime("11:01");
            todos.add(todo);
        }
        return todos;
    }


    public static String randomStringWithSize(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String s = "";
        for (int i = 0; i < size; i++) {
            s = s + (String.valueOf(alphabet.charAt(rnd.nextInt(alphabet.length()))));
        }
        return s;
    }

    public static boolean randomBoolean() {
        String alphabet = "1234567890";
        return rnd.nextInt(alphabet.length()) % 2 == 0;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            final String jsonContent = mapper.writeValueAsString(obj);

            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
