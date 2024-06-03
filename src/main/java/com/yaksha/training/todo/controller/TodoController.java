package com.yaksha.training.todo.controller;

import com.yaksha.training.todo.entity.Todo;
import com.yaksha.training.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import java.time.LocalDate;


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
    public String listTodos(@PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<Todo> todos = todoService.getTodos(pageable);
        model.addAttribute("todos", todos.getContent());
        model.addAttribute("theSearchName", "");
        model.addAttribute("totalPage", todos.getTotalPages());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");
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

    @RequestMapping("/search")
    public String searchTodos(@RequestParam(value = "theSearchName", required = false) String theSearchName,
                              @RequestParam(value = "theSearchDate", required = false) LocalDate theSearchDate,
                              @PageableDefault(size = 5) Pageable pageable,
                              Model theModel) {

        Page<Todo> theTodos = todoService.searchTodos(theSearchName, theSearchDate, pageable);
        theModel.addAttribute("todos", theTodos.getContent());
        theModel.addAttribute("theSearchName", theSearchName != null ? theSearchName : "");
        theModel.addAttribute("theSearchDate", theSearchDate != null ? theSearchDate : "");
        theModel.addAttribute("totalPage", theTodos.getTotalPages());
        theModel.addAttribute("page", pageable.getPageNumber());
        theModel.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");

        return "list-todos";
    }

    @GetMapping("/updateStatus")
    public String updateStatus(@RequestParam("todoId") Long id,
                               @RequestParam("status") String status) {
        todoService.updateTodoStatus(id, status);
        return "redirect:/todo/list";
    }
}
