package in.manipal.mit.ccd.lab4.controller;

import in.manipal.mit.ccd.lab4.dto.TodoCreateRequest;
import in.manipal.mit.ccd.lab4.dto.TodoEditRequest;
import in.manipal.mit.ccd.lab4.model.Todo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/todos")
@Tag(name = "Todo", description = "Todo item management")
public class TodoController {

    private static final Logger log = LoggerFactory.getLogger(TodoController.class);

    private final List<Todo> todos = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idCounter = new AtomicLong(0);

    @PostMapping
    @Operation(summary = "Create a new todo item")
    public ResponseEntity<?> createTodo(@RequestBody TodoCreateRequest request) {
        if (request.getTaskDescription() == null || request.getTaskDescription().isBlank()) {
            return ResponseEntity.badRequest().body("taskDescription is required");
        }

        Todo todo = new Todo();
        todo.setTodoId(idCounter.incrementAndGet());
        todo.setTaskDescription(request.getTaskDescription());
        todo.setStatus("added");
        todo.setUserId(request.getUserId());
        LocalDateTime now = LocalDateTime.now();
        todo.setDateTimeCreated(now);
        todo.setDateTimeUpdated(now);

        todos.add(todo);
        log.info("Created todo with id {}", todo.getTodoId());

        return ResponseEntity.created(URI.create("/api/v1/todos/" + todo.getTodoId())).body(todo);
    }

    @PutMapping("/{todoId}/complete")
    @Operation(summary = "Mark an existing todo item as completed")
    public ResponseEntity<?> completeTodo(@PathVariable Long todoId) {
        Optional<Todo> existing = findById(todoId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Todo todo = existing.get();
        todo.setStatus("completed");
        todo.setDateTimeUpdated(LocalDateTime.now());

        log.info("Completed todo with id {}", todoId);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}/edit")
    @Operation(summary = "Edit an existing todo item's taskDescription")
    public ResponseEntity<?> editTodo(@PathVariable Long todoId, @RequestBody TodoEditRequest request) {
        Optional<Todo> existing = findById(todoId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (request.getTaskDescription() == null || request.getTaskDescription().isBlank()) {
            return ResponseEntity.badRequest().body("taskDescription is required");
        }

        Todo todo = existing.get();
        todo.setTaskDescription(request.getTaskDescription());
        todo.setDateTimeUpdated(LocalDateTime.now());

        log.info("Edited todo with id {}", todoId);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "Hard delete a todo item")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId) {
        Optional<Todo> existing = findById(todoId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        todos.remove(existing.get());
        log.info("Deleted todo with id {}", todoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "List all todo items")
    public ResponseEntity<List<Todo>> listTodos(
            @RequestHeader(value = "X-Gateway-Origin", required = false) String gatewayOrigin) {
        log.info("X-Gateway-Origin: {}", gatewayOrigin);
        return ResponseEntity.ok(new ArrayList<>(todos));
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "Fetch a single todo item by id")
    public ResponseEntity<Todo> getTodo(@PathVariable Long todoId) {
        return findById(todoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private Optional<Todo> findById(Long todoId) {
        synchronized (todos) {
            return todos.stream().filter(t -> t.getTodoId().equals(todoId)).findFirst();
        }
    }
}
