/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;

/**
 *
 * @author Kraiper
 */
public class BookStore {
    private static BookList storeInterface = new BookManager();
    private static BookKartInterface kartInterface = new BookKart();
    private static PrintStream out;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader reader;
    
    public enum programState {mainMenu, displayAllBooks, bookSearch, displayKart, exit};
    public static programState state = programState.mainMenu;
    
    public static void print(String... printString)
    {
        for(String string: printString)
        {
            out.println(string);
        }
    }
    
    public static void print(Book... printArray)
    {
        int i = 1;
        for(Book book: printArray)
        {
            out.println(i + ". " + book.GetTitle() + ", " + book.GetAuthor() + ", " + book.GetPrice());
            i++;
        }
    }
    
    public static void print(BookStock... printArray)
    {
        int i = 1;
        for(BookStock bookStock: printArray)
        {
            out.println(i + ". " + bookStock.book.GetTitle() + ", " + bookStock.book.GetAuthor() + ", " + bookStock.book.GetPrice() + ", " + bookStock.stock);
            i++;
        }
    }
    
    private static boolean init() throws UnsupportedEncodingException
    {
        
        out = new PrintStream(System.out, true, "UTF-8");
        inputStreamReader = new InputStreamReader(System.in, "UTF-8");//, "Cp437"
        reader = new BufferedReader(inputStreamReader);
        
        out.println(inputStreamReader.getEncoding());
        
        out.println("Adding books");
        try {
            URL url = new URL("https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            
            String bookLine;
            while ((bookLine = in.readLine()) != null)
            {
                out.println(bookLine);
                
                String[] parts = bookLine.split(";");
                
                out.println(parts[0]);
                out.println(parts[1]);
                out.println(parts[2].toString());
                out.println(parts[3]);
                
                Book newBook = new Book(parts[0], parts[1], new BigDecimal(parts[2].replaceAll(",", "")));
                
                storeInterface.add(newBook, Integer.parseInt(parts[3]));
            }
            
        }
        catch (IOException e) {
            out.println("General I/O exception: " + e.getMessage());
            return false;
        }
        
        out.println("Printing available books");
        Book[] availableBooks = storeInterface.list("");
        
        for(Book book : availableBooks)
        {
            out.println(book.GetTitle() + ", " + book.GetAuthor());
        }
        
        return true;
    }
    
    private static void shopBooks(Book... bookArray) throws IOException
    {
        print(bookArray);
        print("Select a book to add it to your kart.");
        print("Type return to go back to the main menu.");
        String input = reader.readLine();
        int parsedInt;
        try
        {
            parsedInt = Integer.parseInt(input);
        }
        catch(NumberFormatException e)
        {
            parsedInt = 0;
        }
        if(parsedInt > 0 && parsedInt <= bookArray.length + 1)
        {
            print("Input the quantity of " + bookArray[parsedInt-1].GetTitle() + " you would like to buy.");
            input = reader.readLine();
            try
            {
                int parsedQuantity = Integer.parseInt(input);
                if(parsedQuantity > 0)
                {
                    int result = kartInterface.addToKart(bookArray[parsedInt-1], parsedQuantity);
                    if(result == 1)
                    {
                        print(parsedQuantity + " copys of " + bookArray[parsedInt-1].GetTitle() + " was added to your kart.");
                    }
                    else if(result == 2)
                    {
                        print("A previous entry of " + bookArray[parsedInt-1].GetTitle() + 
                                " was found in the kart and that entry was updated to a purchase of " + parsedQuantity + " copys.");
                    }
                }
                else
                {
                    throw new NumberFormatException("Bad input");
                }
            }
            catch(NumberFormatException e)
            {
                print("Error interpreting number: " + e.toString());
            }
            }
        else if(input.equals("return"))
        {
            state = programState.mainMenu;
        }
        else if(input.equals("exit"))
        {
            state = programState.exit;
        }
        else
        {
            print("Invalid option!");
        }
    }
    
    private static void programLoop() throws IOException
    {
        
        boolean runProgram = true;
        while(runProgram)
        {
            switch(state)
            {
                case mainMenu:
                    String[] optionString = {"Select your option:", "1. Show all books", "2. Search for books",
                        "3. Displey kart", "Type exit to close program"};
                    print(optionString);
                    String input = reader.readLine();
                    switch (input) 
                    {
                        case "1":
                            state = programState.displayAllBooks;
                            break;
                        case "2":
                            state = programState.bookSearch;
                            break;
                            case "3":
                            state = programState.displayKart;
                            break;
                        case "exit":
                            state = programState.exit;
                            break;
                        default:
                            print("Invalid option!");
                            break;
                    }
                    break;
                case displayAllBooks:
                    Book[] bookArray = storeInterface.list("");
                    shopBooks(bookArray);
                    break;
                case bookSearch:
                    print("Input search string");
                    input = reader.readLine();
                    bookArray = storeInterface.list(input);
                    
                    if(bookArray.length == 0)
                    {
                        print("No books matched your search.");
                        state = programState.mainMenu;
                    }
                    else
                    {
                        shopBooks(bookArray);
                    }
                    break;
                case displayKart:
                    BookStock[] bookKart = kartInterface.getKart();
                    print(bookKart);
                    print("The total price for wares in your kart is: " + kartInterface.getPrice());
                    print("Select a entry to delete it from your kart.");
                    print("Type buy to buy the books in your kart.");
                    print("Type return to go back to main menu");
                    input = reader.readLine();
                    int parsedInt;
                    try
                    {
                        parsedInt = Integer.parseInt(input);
                    }
                    catch(NumberFormatException e)
                    {
                        parsedInt = 0;
                    }
                    if(parsedInt > 0 && parsedInt <= bookKart.length + 1)
                    {
                        kartInterface.removeBook(bookKart[parsedInt-1].book);
                    }
                    else if(input.equals("buy"))
                    {
                        BigDecimal totalPrice = new BigDecimal("0");
                        for(int i = 0; i < bookKart.length; i++)
                        {
                            Book[] buyBooks = new Book[bookKart[i].stock];
                            for(int j = 0; j < bookKart[i].stock; j++)
                            {
                                buyBooks[j] = bookKart[i].book;
                            }
                            int boughtBooks = 0;
                            int[] buyOutput = storeInterface.buy(buyBooks);
                            OUTER:
                            for (int k : buyOutput) {
                                switch (k) {
                                    case 2:
                                        break OUTER;
                                    case 1:
                                        break OUTER;
                                    case 0:
                                        boughtBooks++;
                                        totalPrice = totalPrice.add(bookKart[i].book.GetPrice());
                                        break;
                                }
                            }
                            if(buyOutput[0] == 2)
                            {
                                print("The book " + bookKart[i].book + " does not exist in the store.");
                            }
                            else if(boughtBooks == 0)
                            {
                                print("The book " + bookKart[i].book.GetTitle() + " is out of stock.");
                            }
                            else if(boughtBooks < bookKart[i].stock)
                            {
                                print("You bought " + boughtBooks + " copys of " + bookKart[i].book.GetTitle() + " before the store stock ran out.");
                            }
                            else
                            {
                                print("You bought " + boughtBooks + " copys of " + bookKart[i].book.GetTitle());
                            }
                        }
                        print("You bought books for a total of " + totalPrice + " currency.");
                        kartInterface.clearKart();
                        state = programState.mainMenu;
                    }
                    else if(input.equals("return"))
                    {
                        state = programState.mainMenu;
                    }
                    else if(input.equals("exit"))
                    {
                        state = programState.exit;
                    }
                    break;
                case exit:
                    print("Terminating!");
                    runProgram = false;
                    break;
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        System.out.println("Booting store");
        try
        {
            if(!init())
            {
                out.println("Error encountered in initiation of the book store!");
                System.exit(1);
            }
        }
        catch(UnsupportedEncodingException e)
        {
            System.out.println("Error encoding system print output: " + e.getMessage());
            System.exit(1);
        }
        
        out.println("Launcing main program loop");
        try
        {
            programLoop();
        }
        catch(IOException e)
        {
            System.out.println("Error encoding system print output: " + e.getMessage());
            System.exit(1);
        }
    }
    
}
