package increasedStackSize;

import necesse.inventory.item.miscItem.CloudInventoryOpenItem;
import necesse.inventory.item.miscItem.CoinPouch;
import necesse.inventory.item.miscItem.PouchItem;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static List<Class<?>> blacklist = Arrays.asList(PouchItem.class, CoinPouch.class, CloudInventoryOpenItem.class);

    public static boolean isBlacklisted(Object item) {
        return blacklist.stream().anyMatch((c) -> c.isInstance(item));
    }
}
