CREATE TABLE book (
    id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    description VARCHAR(150),
    CONSTRAINT book_pk PRIMARY KEY (id)
);
