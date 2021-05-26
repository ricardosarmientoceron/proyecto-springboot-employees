package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.todo.Todo;

@CrossOrigin(origins = "http://prodigio-angular-final.s3-website.us-east-2.amazonaws.com")
@RestController
public class TodoResource {

	@Autowired
	private TodoHardcodedService todoService;

	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) throws Exception {

		List<Todo> listTax = new ArrayList<Todo>();

		try {
			listTax = todoService.findAll();
			return listTax;
		} catch (Exception e) {
			return listTax;
		}

	}

	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) throws Exception {

		Todo task = new Todo();

		try {
			task = todoService.findById(id);
			return task;
		} catch (Exception e) {
			return task;
		}

	}

	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) throws Exception {

		Todo todo = todoService.deleteById(id);

		try {
			if (todo != null) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id,
			@RequestBody Todo todo) throws Exception {

		
		
		try {
			Todo todoUpdated = todoService.save(todo);

			return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Todo>(todo, HttpStatus.BAD_REQUEST);

		}
		
	}

	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> updateTodo(@PathVariable String username, @RequestBody Todo todo) {

		Todo createdTodo = todoService.save(todo);

		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
