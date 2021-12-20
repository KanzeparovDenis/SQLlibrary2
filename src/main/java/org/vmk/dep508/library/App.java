package org.vmk.dep508.library;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main(String [] args) throws SQLException
    {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
        try(Statement stmt = connection.createStatement();)
        {
            String tableSql = "create table abonents(student_id int, student_name varchar(255))";
            String tableBooksSql = "create table books(book_id int, book_title varchar(255), student_id int)";
            stmt.execute(tableSql);
            stmt.execute(tableBooksSql);
        }

        List students = new ArrayList<Student>();
        List books = new ArrayList<Book>();

        students.add(new Student(123,"Ivanov"));
        students.add(new Student(1234, "Petrov"));

        books.add(new Book(1,"Book1"));
        books.add(new Book(2,"Book2"));

        for (Object book: books)
        {
            library.addNewBook((Book) book);
        }
        for (Object student : students)
        {
            library.addAbonent((Student) student);
        }

        for (Object book : library.findAvailableBooks())
        {
            System.out.println((Book)book);
        }

        for(Object student : library.getAllStudents())
        {
            System.out.println((Student) student);
        }

    }
}
