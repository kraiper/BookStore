/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Kraiper
 */
public class BookManager implements BookList{
    
    List<BookStock> bookStock = new ArrayList<BookStock>();
    
    @Override
    public Book[] list(String searchString) {
        // Return Book array depending on search string. If the string is empty return all books
        if (searchString == null || searchString.isEmpty()) // Empty string return all books
        {
            Book[] searchReturn = new Book[bookStock.size()];
            for(int i = 0; i < bookStock.size();i++)
            {
                searchReturn[i] = bookStock.get(i).book;
            }
            return searchReturn;
        }
        
        List<Book> searchResults = new ArrayList<Book>();
        Pattern pattern = Pattern.compile(Pattern.quote(searchString.toLowerCase()));
        for (BookStock bookSearch : bookStock)
        {
            if(bookSearch.book.GetTitle().contains(searchString))
            {
                searchResults.add(bookSearch.book);
            }
            else if(bookSearch.book.GetAuthor().contains(searchString))
            {
                searchResults.add(bookSearch.book);
            }
        }
        
        Book[] searchReturn = new Book[searchResults.size()];
        searchResults.toArray(searchReturn);
        return searchReturn;
        
    }

    @Override
    public boolean add(Book book, int quantity) {
        // Add new book or refill existing book stock
        if(book == null)
        {
            return false;
        }
        else if(book.GetTitle() == null || book.GetAuthor() == null || book.GetPrice() == null)
        {
            return false;
        }
        if(book.GetAuthor().length() == 0)
        {
            return false;
        }
        if(book.GetTitle().length() == 0)
        {
            return false;
        }
        if(book.GetPrice().compareTo(BigDecimal.ZERO) < 0)
        {
            return false;
        }
        if(quantity < 0)
        {
            return false;
        }
        
        // Refill existing book
        for (BookStock bookSearch : bookStock)
        {
            if(bookSearch.book.equals(book))
            {
                bookSearch.stock = bookSearch.stock + quantity;
                return true;
            }
        }
        // Add new book
        BookStock addBook = new BookStock();
        addBook.book = book;
        addBook.stock = quantity;
        bookStock.add(addBook);
        return true;
    }

    @Override
    public int[] buy(Book... books) {

        if(books == null)
        {
            int[] returnFail = new int[0];
            return returnFail;
        }
        
        int[] returnInt = new int[books.length];

        //Handle each of the books that the user wants to buy
        for(int i = 0; i < books.length; i++)
        {
            //Check if the book exists
            boolean bookExists = false;
            for (BookStock bookSearch: bookStock)
            {
                if(bookSearch.book.equals(books[i]))
                {
                    if(bookSearch.stock > 0)
                    {
                        returnInt[i] = 0;
                        bookSearch.stock = bookSearch.stock - 1;
                        bookExists = true;
                    }
                    else
                    {
                        returnInt[i] = 1;
                        bookExists = true;
                    }
                    break;
                }
            }
            if(!bookExists)
            {
                returnInt[i] = 2;
            }
        }
        
        return returnInt;
    }
    
}
