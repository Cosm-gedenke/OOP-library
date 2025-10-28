package bci.app;

import java.util.StringJoiner;

import bci.Visitor;
import bci.creators.Creator;
import bci.users.User;
import bci.users.behaviour.*;
import bci.users.userstate.*;
import bci.works.*;
import bci.notifications.*;

public class StringConverter extends Visitor<String> {

    /**
     * Store user information temporarily
     */
    private User user;
    
    @Override
    public String visit(User user) {
        this.user = user;
        return new StringJoiner(" - ")
            .add(Integer.toString(user.getID()))
            .add(user.getName())
            .add(user.getEmail())
            .add(user.getBehaviour().accept(this))
            .add(user.getState().accept(this)).toString();
    }

    @Override
    public String visit(ActiveState state) {
        return "ACTIVO";
    }

    @Override
    public String visit(SuspendedState state) {
        return "SUSPENSO - EUR " + this.user.getDebt();
    }

    @Override
    public String visit(Defaulter behaviour) {
        return "FALTOSO";
    }

    @Override
    public String visit(Dutiful behaviour) {
        return "CUMPRIDOR";
    }

    @Override
    public String visit(Normal behaviour) {
        return "NORMAL";
    }

    @Override
    public String visit(Book book) {
        StringJoiner authorsJoiner = new StringJoiner("; ");
        book.getAuthors().stream()
            .map(Creator::getName)
            .forEach(authorsJoiner::add);

        return new StringJoiner(" - ")
            .add(Integer.toString(book.getID()))
            .add(book.getAvailableStock() + " de " + book.getTotalStock())
            .add("Livro")
            .add(book.getTitle())
            .add(Integer.toString(book.getPrice()))
            .add(book.getCategory().accept(this))
            .add(authorsJoiner.toString())
            .add(book.getIsbn()).toString();
    }

    // TODO: Fix "X de Y elementos disponiveis"
    @Override
    public String visit(DVD dvd) {
        return new StringJoiner(" - ")
            .add(Integer.toString(dvd.getID()))
            .add(dvd.getAvailableStock() + " de " + dvd.getTotalStock())
            .add("DVD")
            .add(dvd.getTitle())
            .add(Integer.toString(dvd.getPrice()))
            .add(dvd.getCategory().accept(this))
            .add(dvd.getDirector().getName())
            .add(dvd.getIgac()).toString();
    }

    @Override
    public String visit(Category category) {
        switch (category) {
            case FICTION: return "Ficção";
            case REFERENCE: return "Referência";
            case SCITECH: return "Técnica e Científica";
            default: return ""; // should never happen
        }
    }

    @Override
    public String visit(RequestNotification notification) {
        return "REQUISIÇÃO: " + notification.getWork().accept(this);
    }

    @Override
    public String visit(AvailabilityNotification notification) {
        return "DISPONIBILIDADE: " + notification.getWork().accept(this);
    }
}
