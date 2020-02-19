import java.util.Date;

abstract class Login {
    private String id;
    private String password;

    Login(String id,String password)
    {
        this.id=id;
        this.password=password;
    }
}

class librarian extends Login
{
    librarian(String id, String password) {
        super(id, password);
    }

    public void addBookItem(String bookItem)
    {
        //function to add books in the library
    }
    public void issueBook(String bookItem)
    {
        //function to issue books to member
    }
    public void calculateFine(Date duedate)
    {
        //function to issue fine for books returned after due date
    }
}

class Student extends Login
{
    private Date dateOfMembership;
    private int totalBooks;

    Student(String id, String password) {
        super(id, password);
    }
    public void searchBook(String bookItem)
    {
        //member searches for book hhe wants to issue
    }
    public void requestBook(String bookItem)
    {
        //issue book from librarian
    }
    public void returnBook(String bookItem)
    {
        //return book to librarian
    }
}

 class Book {
    private String ISBN;
    private String title;
    private String publisher;
    private String authors;
    private double price;
    private Date dueDate;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

public class Q1 {
    public static void main(String[] args) {


                Book bookitem = new Book();
                Student student = new Student("Pinki","pinki@123");
                librarian lib = new librarian("Chetna","Chetna@123");

                lib.addBookItem(bookitem.getTitle());
                student.searchBook(bookitem.getTitle());
                student.requestBook(bookitem.getTitle());
                lib.issueBook(bookitem.getTitle());
                student.returnBook(bookitem.getTitle());
                lib.calculateFine(bookitem.getDueDate());

            }
        }



