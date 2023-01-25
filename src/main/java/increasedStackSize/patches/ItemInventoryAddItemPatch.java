package increasedStackSize.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Item.class, name = "inventoryAddItem", arguments = {Level.class, PlayerMob.class, InventoryItem.class, InventoryItem.class, String.class, boolean.class, int.class, boolean.class})
public class ItemInventoryAddItemPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter() {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.AllArguments Object[] args, @Advice.Return(readOnly = false) boolean success) {
        success = (boolean) args[5]
                && thisItem.canCombineItem((Level) args[0], (PlayerMob) args[1], (InventoryItem) args[2], (InventoryItem) args[3], (String) args[4])
                && thisItem.onCombine((Level) args[0], (PlayerMob) args[1], null, -1, (InventoryItem) args[2], (InventoryItem) args[3], (int) args[6], ((InventoryItem) args[3]).getAmount(), (boolean)args[7], (String) args[4], null);
    }
}
