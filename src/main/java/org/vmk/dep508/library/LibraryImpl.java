package org.vmk.dep508.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryImpl implements Library{
    private String jdbcUrl;
    private String user;
    private String password;

    public LibraryImpl(String Url, String user, String password)
    {
        this.jdbcUrl = Url;
        this.user = user;
        this.password = password;
    }
    /* Регистрация новой книги */
    @Override
    public void addNewBook(Book book)
    {
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                PreparedStatement booktb = table.prepareStatement("INSERT INTO books VALUES (?,?,0)");)
        {
            booktb.setInt(1, book.getId());
            booktb.setString(2, book.getTitle());
            booktb.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /* Добавление нового абонента */
    @Override
    public void addAbonent(Student student)
    {
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                PreparedStatement studenttb = table
                        .prepareStatement("INSERT INTO abonents VALUES(?,?)");)
        {
            studenttb.setInt(1, student.getId());
            studenttb.setString(2, student.getName());
            studenttb.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    /* Список всех записанных в библиотеку студентов*/
    @Override
    public List<Student> getAllStudents()
    {
        List<Student> students = new ArrayList<>();
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                Statement studenttb = table.createStatement();
                ResultSet StudentList = studenttb.executeQuery("SELECT student_id,student_name FROM abonents");)
        {
            while (StudentList.next())
            {
                int i = StudentList.getInt("student_id");
                String str = StudentList.getString("student_name");
                students.add(new Student(i, str));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return students;
    }





    /* Студент берет книгу */

    @Override
    public void borrowBook(Book book, Student student)
    {
        if (findAvailableBooks().contains(book))
            return;
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                PreparedStatement studenttb = table
                        .prepareStatement("update books set student_id = ? where book_id = ?");)
        {
            studenttb.setInt(1, student.getId());
            studenttb.setInt(2, book.getId());
            studenttb.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /* Студент возвращает книгу */
    @Override
    public void returnBook(Book book, Student student)
    {
        if (findAvailableBooks().contains(book))
            return;
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                PreparedStatement studenttb = table.prepareStatement("UPDATE books SET student_id = 0 WHERE student_id = ? AND book_id = ?");)
        {
            studenttb.setInt(1, student.getId());
            studenttb.setInt(2, book.getId());
            studenttb.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    /* Получить список свободных книг */
    @Override
    public List<Book> findAvailableBooks()
    {
        List<Book> books = new ArrayList<>();
        try (
                Connection table = DriverManager.getConnection(jdbcUrl, user, password);
                Statement booktb = table.createStatement();
                ResultSet BookList = booktb.executeQuery("SELECT book_id,book_title FROM books WHERE student_id = 0");)
        {
            while (BookList.next())
            {
                int i = BookList.getInt("book_id");
                String str = BookList.getString("book_title");
                books.add(new Book(i, str));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return books;
    }
}
