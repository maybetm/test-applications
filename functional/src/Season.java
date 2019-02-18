import java.util.function.Function;

public enum Season {

    WINTER(true, season -> season.prim),
    SPRING(false, (e) -> e.prim),
    SUMMER(true, (e) -> e.prim),
    AUTUMN(false, (e) -> e.prim);

    Boolean prim;

    Season(Boolean prim, Function<Season, Boolean> getter) {
        this.prim = prim;
        this.getter = getter;
    }
    private final Function<Season, Boolean> getter;

    public Boolean getValue(Season data)
    {
        return getter.apply(data);
    }

    public boolean isPrimitive() { return prim; }
}
