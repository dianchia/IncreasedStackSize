package increasedStackSize.patches;

import increasedStackSize.Config;
import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Item.class, name = "getStackSize", arguments = {})
public class ItemGetStackSizePatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter() {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.FieldValue(value = "stackSize") int originalStackSize, @Advice.Return(readOnly = false) int stackSize) {
        if (!Config.isBlacklisted(thisItem)) {
            stackSize = originalStackSize * IncreasedStackSize.stackSizeMultiplier;
        }
    }
}
