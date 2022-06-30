package increasedStackSize;

import increasedStackSize.patches.ItemConstructorPatch;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class IncreasedStackSize {

    public static Config config = new Config();

    public void init() {
        System.out.println("Loading Increased Stack Size mod... Enjoy!");
    }

    public void initResources() {
    }

    public void postInit() {
    }

}
