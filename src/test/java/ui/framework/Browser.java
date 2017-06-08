package ui.framework;

/**
 * @author BZagorski on 6/3/2017.
 */
public enum Browser {

    Firefox,
    Chrome,
    Internet_Explorer,
    Phantom;

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
