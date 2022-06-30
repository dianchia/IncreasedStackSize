package increasedStackSize;

public class Config {
    private int multiplier;

    public Config() {
        this(5);
    }
    public Config(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int newValue) {
        multiplier = Math.max(newValue, 1);
    }
}
