package bci.works;

import bci.Visitor;
import bci.creators.Creator;
import java.util.Collection;
import java.util.Set;
import java.util.LinkedHashSet;

public class Book extends Work {
    /**
     * Ordered set of the book's authors
     */
    private Set<Creator> authors = new LinkedHashSet<Creator>();

    /**
     * ISBN registration number
     */
    private String isbn;

    public Book(int ID, int stock, int price, String title, Category category,
        Collection<Creator> authors, String isbn) {
            super(ID, stock, price, title, category);
            this.authors.addAll(authors);
            this.isbn = isbn;
    }

    /**
     * Get all the authors of the book
     * @return copy of the collection of authors
     */
    public Collection<Creator> getAuthors() {
        return new LinkedHashSet<Creator>(this.authors);
    }

    @Override
    public boolean containsTerm(String searchterm) {
        if (getTitle().contains(searchterm)) { return true;}
        for(Creator author : authors) {
            if (author.containsTerm(searchterm)) {return true;} 
        }
        return false;
    }
    /**
     * Get the ISBN registration number of the book as a string
     * @return ISBN registration number
     */
    public String getIsbn() {
        return this.isbn;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public Work copy() {
        Book copy = new Book(
            this.getID(), 
            this.getTotalStock(), 
            this.getPrice(), 
            this.getTitle(), 
            this.getCategory(),
            this.authors,
            this.isbn
        );
        
        copy.setAvailableStock(getAvailableStock());
        return copy;
    }
}
