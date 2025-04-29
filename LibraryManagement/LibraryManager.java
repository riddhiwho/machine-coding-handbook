package LibraryManagement;

import java.util.List;

public class LibraryManager {

    public static void main(String[] args) {
        Library library = new Library();
        Book book1 = new Book("123", "Harry Potter", "J.K. Rowling", BOOK_STATUS.AVAILABLE);
        Book book2 = new Book("456", "The Running Grave", "J.K. Rowling", BOOK_STATUS.AVAILABLE);
        Book book3 = new Book("789", "Fantastic Beasts", "J.K. Rowling", BOOK_STATUS.AVAILABLE);
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        List<Book> authorBooks = library.searchBookByAuthor("J.K. Rowling");
        for(Book book : authorBooks){
            System.out.print(book.getTitle() + " ");
        }
        System.out.println();
        List<Book> titleBooks = library.searchBookByTitle("Harry Potter");
        for(Book book : titleBooks){
            System.out.print(book.getAuthor() + " ");
        }
        System.out.println();

    }
}
