package LibraryManagement;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private BOOK_STATUS status;

    public Book(String ISBN, String title, String author, BOOK_STATUS status){
        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.status=status;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setStatus(BOOK_STATUS status){
        this.status = status;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public BOOK_STATUS getStatus(){
        return this.status;
    }

    public String getISBN(){
        return this.ISBN;
    }

}
