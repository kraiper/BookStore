/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.math.BigDecimal;

/**
 *
 * @author Kraiper
 */
public class Book {

    public Book(String _title, String _author, BigDecimal _price)
    {
        title = _title;
        author = _author;
        price = _price;
    }
    
    public String GetTitle()
    {
        return title;
    }
    
    public String GetAuthor()
    {
        return author;
    }
    
    public BigDecimal GetPrice()
    {
        return price;
    }
    
    private String title;
    private String author;
    private BigDecimal price;

}
