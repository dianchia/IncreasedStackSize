package increasedStackSize.patches;

import increasedStackSize.ConfigLoader;
import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = Item.class, arguments = {int.class})
public class ItemConstructorPatch {
    public static int multiplier = -1;

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.FieldValue(value = "stackSize", readOnly = false) int stackSize) {
        if (multiplier <= 0) {
            System.out.println("Patching item stack size...");
            ConfigLoader loader = new ConfigLoader("settings.cfg");
            multiplier = loader.config.getMultiplier();
            System.out.println("Initialized mod IncreasedStackSize with multiplier of " + ItemConstructorPatch.multiplier);
        }
        stackSize = stackSize * multiplier;
    }
}
