package org.example.oneviziontest;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/// Contains the business logic for Book-related operations.
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /// Gets all books sorted by title descending order.
    public List<Book> getAllBooksSortedByTitle() {
        return bookRepository.findAllSortedByTitleDesc();
    }

    /// Adds a new book.
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    /// Groups all books by author.
    public Map<String, List<Book>> getBooksGroupedByAuthor() {
        return bookRepository.findAll().stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    /// Finds authors whose book titles contain a specific character most frequently.
    public List<Map<String, Long>> getAuthorsByMostCharacter(char character) {
        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream()
                .collect(Collectors.groupingBy(Book::getAuthor))
                .entrySet().stream()
                .map(entry -> {
                    String author = entry.getKey();
                    List<Book> books = entry.getValue();

                    // Count character frequency in titles
                    long count = books.stream()
                            .map(Book::getTitle)
                            .flatMapToInt(String::chars)
                            .filter(c -> Character.toLowerCase(c) == Character.toLowerCase(character))
                            .count();

                    return Map.entry(formatAuthorName(author), count);
                })
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Long> singleEntryMap = new HashMap<>();
                    singleEntryMap.put(entry.getKey(), entry.getValue());
                    return singleEntryMap;
                })
                .collect(Collectors.toList());
    }

    /// Formats author name to "first_initial. last_name" given full-name.
    private String formatAuthorName(String fullName) {
        if (fullName == null || fullName.isBlank())
            return "";

        String[] names = fullName.split("\\s+");
        if (names.length < 2)
            return fullName;

        char firstInitial = names[0].charAt(0);
        String lastName = names[names.length - 1];

        return firstInitial + ". " + lastName;
    }
}
