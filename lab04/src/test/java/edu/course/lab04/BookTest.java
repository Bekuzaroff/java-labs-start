package edu.course.lab04;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void SearchWithAnotherObjectSameISBN() {
        HashMap<Book, String> books = new HashMap<>();

        Book key1 = new Book("I-01", "rich dad, poor dad");
        Book key2 = new Book("I-02", "the richest man in the world");

        books.put(key1, "business");
        books.put(key2, "business 2");

        String res = books.get(new Book("I-01", "something rich")); // another obj with same ISBN

        assertEquals("business", res);


    }

    @Test
    public void ObjectsNotEqualIfISBNThough() {
        Book obj1 = new Book("I-01", "World");
        Book obj2 = new Book("I-02", "World");

        assertNotEquals(obj1, obj2);

        obj2.setIsbn("I-01");

        assertEquals(obj1, obj2);

    }
}
