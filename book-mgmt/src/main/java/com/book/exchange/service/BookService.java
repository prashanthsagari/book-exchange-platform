package com.book.exchange.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.book.exchange.entity.Book;
import com.book.exchange.entity.User;
import com.book.exchange.model.payload.request.BookRequestPayload;
import com.book.exchange.repository.BookRepository;
import com.book.exchange.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

	private static final String DUMMY_PASSWORD = "DUMMY PASSWORD";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	public Page<Book> searchBooksByCriteria(BookRequestPayload bookRequest, Pageable pageable) {

		Page<Book> paginatedBooks = null;
		if (StringUtils.hasText(bookRequest.getTitle())) {
			return bookRepository.findByTitleContainingIgnoreCase(bookRequest.getTitle(), pageable);
		}

		else if (StringUtils.hasText(bookRequest.getAuthor())) {
			return bookRepository.findByAuthorContainingIgnoreCase(bookRequest.getAuthor(), pageable);
		}

		else if (StringUtils.hasText(bookRequest.getGenre())) {
			return bookRepository.findByGenreContainingIgnoreCase(bookRequest.getGenre(), pageable);
		}

		else if (StringUtils.hasText(bookRequest.getCondition())) {
			return bookRepository.findByBookConditionContainingIgnoreCase(bookRequest.getCondition(), pageable);
		}

		else if (StringUtils.hasText(bookRequest.getLocation())) {
			return bookRepository.findByLocationContainingIgnoreCase(bookRequest.getLocation(), pageable);
		}

		else if (bookRequest.isAvailable() == false || bookRequest.isAvailable() == true) {
			return bookRepository.findByIsAvailableContainingIgnoreCase(bookRequest.isAvailable(), pageable);
		}

		return null;
	}

	public Book createBook(BookRequestPayload bookRequest) {
		Optional<User> userData = userRepository.findById(bookRequest.getUserId());
		if (userData.isPresent()) {
			User user = userData.get();

			// create a book
			Book book = bookRepository
			        .save(Book.builder().user(user).title(bookRequest.getTitle()).author(bookRequest.getAuthor())
			                .bookCondition(bookRequest.getCondition()).genre(bookRequest.getGenre())
			                .location(bookRequest.getLocation()).isAvailable(bookRequest.isAvailable()).build());
			book.getUser().setPassword(DUMMY_PASSWORD);
			return book;
		} else {
			throw new EntityNotFoundException("User is not available.");
		}

	}

	public Book deleteBook(Long bookId, Long userId) {
		Optional<Book> bookData = bookRepository.findById(bookId);
		if (bookData.isPresent()) {
			Book book = bookData.get();
			if (!book.getUser().getId().equals(userId)) {
				throw new InvalidDataAccessResourceUsageException("You do not have access to delete book.");
			}
			bookRepository.delete(book);
			book.getUser().setPassword(DUMMY_PASSWORD);
			return book;
		} else {
			throw new EntityNotFoundException("Book is not available.");
		}
	}

	public List<Book> books() {
		List<Book> books = bookRepository.findAll().stream().map(book -> {
			book.getUser().setPassword(DUMMY_PASSWORD);
			return book;
		}).collect(Collectors.toList());

		return books;
	}

	public List<Book> getBookByUserId(Long userId) {
		List<Book> books = bookRepository.findByUserId(userId).stream().map(book -> {
			book.getUser().setPassword(DUMMY_PASSWORD);
			return book;
		}).collect(Collectors.toList());
		return books;
	}

	public Book getBookById(Long bookId) {
		Optional<Book> bookData = bookRepository.findById(bookId);
		if (bookData.isPresent()) {
			Book book = bookData.get();
			book.getUser().setPassword(DUMMY_PASSWORD);
			return book;
		} else {
			throw new EntityNotFoundException("Book is not available.");
		}
	}

	public Book updateBook(Long bookId, BookRequestPayload bookRequest) {
		Optional<Book> bookData = bookRepository.findById(bookId);
		if (bookData.isPresent()) {
			Book book = bookData.get();
			if (!book.getUser().getId().equals(bookRequest.getUserId())) {
				throw new InvalidDataAccessResourceUsageException("You do not have access to modify book.");
			}
			updateBookData(bookRequest, book);
			Book bookResponse = bookRepository.saveAndFlush(book);
			bookResponse.getUser().setPassword(DUMMY_PASSWORD);
			return bookResponse;
		} else {
			throw new EntityNotFoundException("Book is not available.");
		}
	}

	private void updateBookData(BookRequestPayload bookRequest, Book book) {
		if (StringUtils.hasText(bookRequest.getTitle())) {
			book.setTitle(bookRequest.getTitle());
		}
		if (StringUtils.hasText(bookRequest.getAuthor())) {
			book.setAuthor(bookRequest.getAuthor());
		}
		if (StringUtils.hasText(bookRequest.getLocation())) {
			book.setLocation(bookRequest.getLocation());
		}
		if (StringUtils.hasText(bookRequest.getGenre())) {
			book.setGenre(bookRequest.getGenre());
		}
		if (bookRequest.isAvailable()) {
			book.setAvailable(true);
		} else {
			book.setAvailable(false);
		}

		if (StringUtils.hasText(bookRequest.getCondition())) {
			book.setBookCondition(bookRequest.getCondition());
		}

	}
}
