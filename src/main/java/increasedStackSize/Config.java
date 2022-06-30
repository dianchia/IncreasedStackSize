package increasedStackSize;

public class Config {
    private boolean isInitialized = false;
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
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}
