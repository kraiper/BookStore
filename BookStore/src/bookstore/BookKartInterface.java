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
public interface BookKartInterface {
    
    public int addToKart(Book book, int quantity);
    public void removeBook(Book book);
    public BookStock[] getKart();
    public void clearKart();
    public BigDecimal getPrice();
    
}
