package bci.works;

import bci.Visitable;
import bci.Visitor;

public enum Category implements Visitable {
    FICTION,
    REFERENCE,
    SCITECH;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
