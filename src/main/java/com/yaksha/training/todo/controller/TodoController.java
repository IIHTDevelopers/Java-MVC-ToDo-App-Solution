package com.yaksha.training.todo.controller;

import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value = {"/todo", "/"})
public class TodoController {

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    private TodoService todoService;

    @GetMapping(value = {"/list", "/"})
    public String listTodos(Model model) {
        List<Todo> todos = todoService.getTodos();
        model.addAttribute("todos", todos);
        return "list-todos";
    }

    @GetMapping("/addTodoForm")
    public String showFormForAdd(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "add-todo-form";
    }

    @PostMapping("/saveTodo")
    public String saveTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (todo.getId() != null) {
                return "update-todo-form";
            }
            return "add-todo-form";
        } else {
            todoService.saveTodo(todo);
            return "redirect:/todo/list";
        }
    }

    @GetMapping("/updateTodoForm")
    public String showFormForUpdate(@RequestParam("todoId") Long id, Model model) {
        Todo todo = todoService.getTodo(id);
        model.addAttribute("todo", todo);
        return "update-todo-form";
    }

    @GetMapping("/delete")
    public String deleteTodo(@RequestParam("todoId") Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todo/list";
    }

    @PostMapping("/search")
    public String searchTodos(@RequestParam("theSearchName") String theSearchName,
                              Model theModel) {

        List<Todo> theTodos = todoService.searchTodos(theSearchName);
        theModel.addAttribute("todos", theTodos);
        return "list-todos";
    }
}
