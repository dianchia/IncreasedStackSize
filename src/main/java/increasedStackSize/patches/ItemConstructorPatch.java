package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = Item.class, arguments = {int.class})
public class ItemConstructorPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.FieldValue(value = "stackSize", readOnly = false) int stackSize) {
        if (IncreasedStackSize.config.isBlacklisted(thisItem)){
            System.out.println("Skipping " + thisItem + " as it is a " + thisItem.getClass());
        } else {
            stackSize = stackSize * IncreasedStackSize.config.getMultiplier();
        }
    }
}
