package increasedStackSize.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Item.class, name = "getStackSize", arguments = {})
public class ItemPatches {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This Item item) {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item item, @Advice.FieldValue("stackSize") int originalStackSize, @Advice.Return(readOnly = false) int stackSize) {
        stackSize = originalStackSize * 5;
//        System.out.println("Modified stack size of " + item + " from " + originalStackSize + " to " + stackSize);
    }
}
