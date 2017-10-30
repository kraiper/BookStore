/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kraiper
 */
public class BookKart implements BookKartInterface{

    List<BookStock> bookKart = new ArrayList<BookStock>();
    
    @Override
    public int addToKart(Book book, int quantity) {
        // return 1 for new book added, return 2 for book updated
        for(int i = 0; i < bookKart.size();i++)
        {
            if(bookKart.get(i).book.equals(book))
            {
                bookKart.get(i).stock = quantity;
                return 2;
            }
        }
        
        BookStock newBook = new BookStock();
        newBook.book = book;
        newBook.stock = quantity;
        bookKart.add(newBook);
        return 1;
    }

    @Override
    public void removeBook(Book book) {
        for(int i = 0; i < bookKart.size();i++)
        {
            if(bookKart.get(i).book.equals(book))
            {
                bookKart.remove(i);
            }
        }
    }

    @Override
    public BookStock[] getKart() {
        BookStock[] returnArray = bookKart.toArray(new BookStock[bookKart.size()]);
        return returnArray;
    }

    @Override
    public void clearKart() {
        bookKart.clear();
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal price = new BigDecimal("0");
        for(BookStock book: bookKart)
        {
            for(int i = 0; i < book.stock;i++)
            {
                price = price.add(book.book.GetPrice());
            }
        }
        return price;
    }
    
}
