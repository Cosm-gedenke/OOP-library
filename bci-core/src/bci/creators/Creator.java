package bci.creators;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import java.io.Serializable;

import bci.works.Work;


public class Creator implements Serializable {
    /**
     * Name of the creator
     */
    private String name;

    /**
     * Collection of works made by the creator
     */
    private Set<Work> works = new TreeSet<Work>();

    public Creator(String name) {
        this.name = name;
    }

    /**
     * Get the name of the creator
     * @return name of the creator
     */
    public String getName() {
        return this.name;
    }

    /**
     * Add a work to the creator's registry
     * @param work work to add
     */
    public void addWork(Work work) {
        this.works.add(work);
    }

    public void removeWork(Work work) {
        this.works.remove(work);
    }

    public boolean hasWorks() {
        return !this.works.isEmpty();
    }

    /**
     * Get all the works of the creator
     * @return collection of the creator's works
     */
    public Collection<Work> getWorks() {
        return this.works;
    }

    public boolean containsTerm(String searchterm) {
        return name.contains(searchterm);
    } 
}
