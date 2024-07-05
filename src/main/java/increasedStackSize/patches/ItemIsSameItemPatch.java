package increasedStackSize.patches;

import necesse.engine.GameLog;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Item.class, name = "isSameItem", arguments = {Level.class, InventoryItem.class, InventoryItem.class, String.class})
public class ItemIsSameItemPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter() {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This Item thisItem, @Advice.Argument(1) InventoryItem me, @Advice.Argument(2) InventoryItem them, @Advice.Argument(3) String purpose, @Advice.Return(readOnly = false) boolean isSame) {
        if (thisItem != them.item) {
            isSame = false;
            return;
        }

        GameLog.debug.println("Purpose: " + purpose);
        isSame = true;
        if (thisItem.isEnchantable(me) && them.item.isEnchantable(them)) {
            if (ArmorItem.class.isAssignableFrom(thisItem.getClass())) {
                isSame = ((ArmorItem) thisItem).getEnchantment(me) == ((ArmorItem) them.item).getEnchantment(them);
                GameLog.debug.println("ArmorItem: is same = " + isSame);
            } else if (ToolItem.class.isAssignableFrom(thisItem.getClass())) {
                isSame = ((ToolItem) thisItem).getEnchantment(me) == ((ToolItem) them.item).getEnchantment(them);
                GameLog.debug.println("ToolItem: is same = " + isSame);
            } else if (TrinketItem.class.isAssignableFrom(thisItem.getClass())) {
                isSame = ((TrinketItem) thisItem).getEnchantment(me) == ((TrinketItem) them.item).getEnchantment(them);
                GameLog.debug.println("TrinketItem: is same = " + isSame);
            }
        }
    }
}
