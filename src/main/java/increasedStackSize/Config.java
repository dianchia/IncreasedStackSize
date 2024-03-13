package increasedStackSize;

import necesse.inventory.item.miscItem.CloudInventoryOpenItem;
import necesse.inventory.item.miscItem.CoinPouch;
import necesse.inventory.item.miscItem.PouchItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static List<Class<?>> blacklist = Arrays.asList(PouchItem.class, CoinPouch.class, CloudInventoryOpenItem.class, BoomerangToolItem.class);

    public static boolean isBlacklisted(Object item) {
        return blacklist.stream().anyMatch((c) -> c.isInstance(item));
    }
}
