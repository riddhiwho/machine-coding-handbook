public enum Status {
    SELECTED,
    NOT_SELECTED,
    TBD;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
