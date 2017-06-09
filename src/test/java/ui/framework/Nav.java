package ui.framework;

/**
 * @author BZagorski on 6/4/2017.
 */
public enum Nav {
    Upload,
    Explore,
    Categories,
    Favorites;

    @Override
    public String toString() {
        return this.name();
    }
}
