package org.example.oneviziontest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// REST controller for managing books.
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /// 1. GET /api/books
    /// Returns a list of all books sorted in descending order by title.
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooksSortedByTitle();
    }

    /// 2. POST /api/books
    /// Adds a new book to the database.
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /// 3. GET /api/group-by-author
    /// Returns a list of all books grouped by author.
    @GetMapping("/group-by-author")
    public Map<String, List<Book>> getBooksGroupedByAuthor() {
        return bookService.getBooksGroupedByAuthor();
    }

    /// 4. GET /api/authors-by-character
    /// Returns authors based on a character's frequency in book titles.
    @GetMapping("/authors-by-character")
    public List<Map<String, Long>> getAuthorsByCharacter(@RequestParam char character) {
        return bookService.getAuthorsByMostCharacter(character);
    }
}