package LibraryManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {

    private List<Book> booksCollection;
    private HashMap<String, List<Book>> titleToBooks;
    private HashMap<String, List<Book>> authorToBooks;

    public Library(){
        booksCollection = new ArrayList<>();
        titleToBooks =  new HashMap<>();
        authorToBooks =  new HashMap<>();
    }


    public void addBook(Book book){
        if(book==null || book.getISBN()==null){
            System.out.println("Enter a valid book.");
            return;
        }

        for(Book b : booksCollection){
            if(b.getISBN().equals(book.getISBN())){
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setISBN(book.getISBN());
                b.setStatus(BOOK_STATUS.AVAILABLE);
                return;
            }
        }

        booksCollection.add(book);

        titleToBooks.computeIfAbsent(book.getTitle().toLowerCase(), k -> new ArrayList<>()).add(book);

        authorToBooks.computeIfAbsent(book.getAuthor().toLowerCase(), k -> new ArrayList<>()).add(book);
    }

    public List<Book> searchBookByAuthor(String author){
        if(author==null){
            System.out.println("Enter valid author.");
            return new ArrayList<>();
        } 

        List<Book> books = authorToBooks.get(author.toLowerCase());
        if(books!=null && !books.isEmpty()){
            return books;
        }
        System.out.println("No books found for author: " + author);
        return new ArrayList<>();
    }

    public List<Book> searchBookByTitle(String title){
        if(title==null){
            System.out.println("Enter valid title.");
            return new ArrayList<>();
        } 

        List<Book> books = titleToBooks.get(title.toLowerCase());
        if(books!=null && !books.isEmpty()){
            return books;
        }
        System.out.println("No books found for title: " + title);
        return new ArrayList<>();
    }

    public Book checkoutBook(Book book){
        for(Book b : booksCollection){
            if(b.getISBN().equals(book.getISBN())){
                if(b.getStatus()==BOOK_STATUS.AVAILABLE){
                    b.setStatus(BOOK_STATUS.CHECKED_OUT);
                    System.out.println("Checkout successful!");
                    return book;
                }else{
                    System.out.println("Book not available. Current status: " + b.getStatus().toString());
                    return null;
                }
            }
        }
        System.out.println("Book not found!");
        return null;
    }

    public Book returnBook(Book book){
        for(Book b : booksCollection){
            if(b.getISBN().equals(book.getISBN())){
                if(b.getStatus()==BOOK_STATUS.CHECKED_OUT){
                    b.setStatus(BOOK_STATUS.AVAILABLE);
                    System.out.println("Return successful!");
                    return book;
                }else{
                    System.out.println("Book not checkout out previously. Current status: " + b.getStatus().toString());
                    return null;
                }
            }
        }
        System.out.println("Book doesn't belong to this libary. Can't return!");
        return null;
    }

}
