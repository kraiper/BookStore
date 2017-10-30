/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bookstore.Book;
import bookstore.BookList;
import bookstore.BookManager;
import java.math.BigDecimal;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Kraiper
 */
public class BookStoreTest {
    
    @Test
    public void testAdd()
    {
        BookList testShop = new BookManager();
        Book book = null;
        assertEquals(false, testShop.add(book, 0));
        book = new Book("My book", "My name", new BigDecimal("1337"));
        assertEquals(true, testShop.add(book, 0));
        book = new Book(null, "My name", new BigDecimal("1337"));
        assertEquals(false, testShop.add(book, 0));
        book = new Book("My book", null, new BigDecimal("1337"));
        assertEquals(false, testShop.add(book, 0));
        book = new Book("My book", "My name", null);
        assertEquals(false, testShop.add(book, 0));
        book = new Book("", "My name", new BigDecimal("1337"));
        assertEquals(false, testShop.add(book, 0));
        book = new Book("My Book", "", new BigDecimal("1337"));
        assertEquals(false, testShop.add(book, 0));
        book = new Book("My Book", "My name", new BigDecimal("0"));
        assertEquals(true, testShop.add(book, 0));
        book = new Book("My Book", "My Name", new BigDecimal("-1"));
        assertEquals(false, testShop.add(book, 0));
    }

    @Test
    public void testList()
    {
        BookList testShop = new BookManager();
        Book booka = new Book("a", "å", new BigDecimal("1337"));
        Book bookb = new Book("b", "ä", new BigDecimal("1337"));
        Book bookc = new Book("c", "ö", new BigDecimal("1337"));
        Book bookd = new Book("d", "ö", new BigDecimal("1337"));
        
        testShop.add(booka, 1);
        testShop.add(bookb, 1);
        testShop.add(bookc, 1);
        testShop.add(bookd, 1);
        
        Book[] bookArray = {booka, bookb, bookc, bookd};
        
        Assert.assertArrayEquals(bookArray, testShop.list(""));
        Assert.assertArrayEquals(bookArray, testShop.list(null));
        
        bookArray = new Book[1];
        bookArray[0] = booka;
        Assert.assertArrayEquals(bookArray, testShop.list("a"));
        Assert.assertArrayEquals(bookArray, testShop.list("å"));
        
        bookArray = new Book[2];
        bookArray[0] = bookc;
        bookArray[1] = bookd;
        Assert.assertArrayEquals(bookArray, testShop.list("ö"));
        
        bookArray = new Book[0];
        Assert.assertArrayEquals(bookArray, testShop.list("fail"));
        
        Book booke = new Book("fraise", "test", new BigDecimal("1337"));
        testShop.add(booke, 1);
        bookArray = new Book[1];
        bookArray[0] = booke;
        
        Assert.assertArrayEquals(bookArray, testShop.list("fraise"));
        Assert.assertArrayEquals(bookArray, testShop.list("test"));
    }
    
    @Test
    public void testBuy()
    {
        BookList testShop = new BookManager();
        Book booka = new Book("a", "å", new BigDecimal("1337"));
        Book bookb = new Book("b", "ä", new BigDecimal("1337"));
        Book bookc = new Book("c", "ö", new BigDecimal("1337"));
        Book bookd = new Book("d", "ö", new BigDecimal("1337"));
        
        testShop.add(booka, 1);
        testShop.add(bookb, 2);
        testShop.add(bookc, 3);
        testShop.add(bookd, 4);
        
        Book[] bookArray = new Book[0];
        int[] intArray = new int[0];
        
        Assert.assertArrayEquals(intArray, testShop.buy(null));
        Assert.assertArrayEquals(intArray, testShop.buy(bookArray));
        
        bookArray = new Book[4];
        bookArray[0] = booka;
        bookArray[1] = bookb;
        bookArray[2] = bookc;
        bookArray[3] = bookd;
        intArray = new int[4];
        intArray[0] = 0;
        intArray[1] = 0;
        intArray[2] = 0;
        intArray[3] = 0;
        
        Assert.assertArrayEquals(intArray, testShop.buy(bookArray));
        
        testShop.add(booka, 1);
        
        bookArray[0] = booka;
        bookArray[1] = booka;
        bookArray[2] = booka;
        bookArray[3] = booka;
        intArray[0] = 0;
        intArray[1] = 1;
        intArray[2] = 1;
        intArray[3] = 1;
        
        Assert.assertArrayEquals(intArray, testShop.buy(bookArray));
        
        Book bookg = new Book("z", "y", new BigDecimal("10"));
        
        bookArray = new Book[1];
        bookArray[0] = bookg;
        intArray = new int[1];
        intArray[0] = 2;
        
        Assert.assertArrayEquals(intArray, testShop.buy(bookArray));
    }
}
