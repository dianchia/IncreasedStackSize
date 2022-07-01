package increasedStackSize;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class IncreasedStackSize {

    public static Config config = new Config();

    public void preInit() {
        System.out.println("Initializing IncreasedStackSize...");
        ConfigLoader.loadConfig("settings.cfg");
        System.out.println("Initialized mod IncreasedStackSize with multiplier of " + config.getMultiplier());
    }

    public void init() {
        System.out.println("Loaded Increased Stack Size mod... Enjoy!");
    }

    public void initResources() {
    }

    public void postInit() {
    }

}
