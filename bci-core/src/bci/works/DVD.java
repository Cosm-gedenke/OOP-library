package bci.works;

import bci.Visitor;
import bci.creators.Creator;

public class DVD extends Work {
    /**
     * Director of the film
     */
    private Creator director;

    /**
     * IGAC registration number
     */
    private String igac;

    public DVD(int ID, int stock, int price, String title, Category category,
        Creator director, String igac) {
            super(ID, stock, price, title, category);
            this.director = director;
            this.igac = igac;
    }

    /**
     * Get the director of the film
     * @return director of the film
     */
    public Creator getDirector() {
        return this.director;
    }

    public boolean containsTerm(String searchterm) {
        return director.containsTerm(searchterm) || getTitle().contains(searchterm);
    }
    /**
     * Get the IGAC registration number of the DVD as a string
     * @return IGAC registration number
     */
    public String getIgac() {
        return this.igac;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Work copy() {
        DVD copy = new DVD(
            this.getID(), 
            this.getTotalStock(), 
            this.getPrice(), 
            this.getTitle(), 
            this.getCategory(),
            this.director,
            this.igac
        );
        
        copy.setAvailableStock(getAvailableStock());
        return copy;
    }
}
