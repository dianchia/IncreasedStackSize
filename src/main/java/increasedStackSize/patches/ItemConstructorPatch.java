package increasedStackSize.patches;

import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = Item.class, arguments = {int.class})
public class ItemConstructorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.FieldValue(value = "stackSize", readOnly = false) int stackSize) {
        stackSize = stackSize * 5;
    }
}
