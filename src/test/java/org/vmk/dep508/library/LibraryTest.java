package org.vmk.dep508.library;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class LibraryTest {
    @Before
    public void setUp() throws Exception {
//        library = new LibraryImpl("jdbc:h2:mem:library", "", "");
//        try(
//                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
//                Statement stmt = connection.createStatement();)
//        {
//            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
//            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addNewBook() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try (
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();) {
            stmt.execute("CREATE TABLE abonents(student_id int, student_name varchar(255))");
            stmt.execute("CREATE TABLE books(book_id int, book_title varchar(255), student_id int)");
            library.addNewBook(new Book(12345, "Book3"));
            library.addNewBook(new Book(123456, "Book4"));
            System.out.println(library.findAvailableBooks());
        }
    }

    @Test
    public void addAbonent() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try (
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();) {
            stmt.execute("CREATE TABLE abonents(student_id int, student_name varchar(255))");
            stmt.execute("CREATE TABLE books(book_id int, book_title varchar(255), student_id int)");
            library.addAbonent(new Student(11, "Ivanov"));
            library.addAbonent(new Student(12, "Petrov"));
            System.out.println(library.getAllStudents());
        }
    }

    @Test
    public void borrowBook() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try (
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();) {
            stmt.execute("CREATE TABLE abonents(student_id int, student_name varchar(255))");
            stmt.execute("CREATE TABLE books(book_id int, book_title varchar(255), student_id int)");
            library.addAbonent(new Student(11, "Ivanov"));
            library.addAbonent(new Student(21, "Petrov"));

            library.addNewBook(new Book(123, "Book1"));
            library.addNewBook(new Book(1234, "Book2"));


            library.borrowBook(new Book(123, "Book1"), new Student(11, "Ivanov"));


            System.out.println(library.findAvailableBooks());

            assertTrue(library.findAvailableBooks().size() == 1);
            library.returnBook(new Book(123, "Book1"), new Student(11, "Ivanov"));
            System.out.println(library.findAvailableBooks());

            assertTrue(library.findAvailableBooks().size() == 2);
        }
    }


}