package com.book.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.exchange.model.payload.request.BookRequestPayload;
import com.book.exchange.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Book Management", description = "Operations related to book management")
@CrossOrigin
public class BookController {

	@Autowired
	private BookService bookService;

	@Operation(summary = "Get all books", description = "Fetches all books from the system")
	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks() {
		return ResponseEntity.ok(bookService.books());
	}

	@Operation(summary = "Get all books by User Id", description = "Fetches all books from the system which are belongs to requested user")
	@GetMapping("/book")
	public ResponseEntity<?> getBookByUserId(@RequestParam Long userId) {
		return ResponseEntity.ok(bookService.getBookByUserId(userId));
	}

	@Operation(summary = "Get book by book id", description = "Fetches book by book id")
	@GetMapping("/book/{bookId}")
	public ResponseEntity<?> getBookByBookId(Long bookId) {
		return ResponseEntity.ok(bookService.getBookById(bookId));
	}

	@Operation(summary = "Create new book entry by user", description = "Creates new book in the system")
	@PostMapping("/book")
	public ResponseEntity<?> createBook(@RequestBody BookRequestPayload book) {
		return ResponseEntity.ok(bookService.createBook(book));
	}

	@Operation(summary = "Update book details by book id", description = "Updates book information in the system")
	@PutMapping("/book/{bookId}")
	public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookRequestPayload book) {
		return ResponseEntity.ok(bookService.updateBook(bookId, book));
	}

	@Operation(summary = "Delete book by book id", description = "Deletes book entry from the system")
	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId, @RequestParam Long userId) {
		return ResponseEntity.ok(bookService.deleteBook(bookId, userId));
	}

	@Operation(summary = "Search books using pagination", description = "Paginated book search")
	@PostMapping("/search-books")
	public ResponseEntity<?> searchBooksByCriteria(@RequestBody BookRequestPayload bookRequest,
	        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(bookService.searchBooksByCriteria(bookRequest, pageable));
	}
}
