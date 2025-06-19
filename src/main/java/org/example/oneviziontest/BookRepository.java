package org.example.oneviziontest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/// Handles database operations for the Book entity using JdbcTemplate.
@Repository
public class BookRepository {

    private final JdbcTemplate jdbc;

    public BookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // RowMapper to map a database row to a Book object
    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("description")
    );

    /// Return a list of all books from database
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbc.query(sql, bookRowMapper);
    }

    /// Return a list of all books sorted by title in descending order from database
    public List<Book> findAllSortedByTitleDesc() {
        String sql = "SELECT * FROM book ORDER BY title DESC";
        return jdbc.query(sql, bookRowMapper);
    }

    /// Save a new book to database
    public void save(Book book) {
        String sql = "INSERT INTO book (id, title, author, description) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
    }
}
