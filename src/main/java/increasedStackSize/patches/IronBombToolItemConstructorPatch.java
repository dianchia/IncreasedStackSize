package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.IronBombToolItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = IronBombToolItem.class, arguments = {})
public class IronBombToolItemConstructorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This IronBombToolItem ironBombToolItem, @Advice.FieldValue(value = "stackSize", readOnly = false)int stackSize) {
        stackSize = stackSize * IncreasedStackSize.config.getMultiplier();
    }
}
