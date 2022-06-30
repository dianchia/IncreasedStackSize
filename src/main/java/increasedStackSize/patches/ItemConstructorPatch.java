package increasedStackSize.patches;

import increasedStackSize.ConfigLoader;
import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = Item.class, arguments = {int.class})
public class ItemConstructorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.FieldValue(value = "stackSize", readOnly = false) int stackSize) {
        if (!IncreasedStackSize.config.isInitialized()) {
            System.out.println("Patching item stack size...");
            ConfigLoader.loadConfig("settings.cfg");
            System.out.println("Initialized mod IncreasedStackSize with multiplier of " + IncreasedStackSize.config.getMultiplier());
        }
        stackSize = stackSize * IncreasedStackSize.config.getMultiplier();
    }
}
