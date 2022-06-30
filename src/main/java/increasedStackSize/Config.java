package increasedStackSize;

import necesse.inventory.item.miscItem.CloudInventoryOpenItem;
import necesse.inventory.item.miscItem.CoinPouch;
import necesse.inventory.item.miscItem.PouchItem;

import java.util.Arrays;
import java.util.List;

public class Config {
    private int multiplier;

    public List<Class<?>> blacklist = Arrays.asList(PouchItem.class, CoinPouch.class, CloudInventoryOpenItem.class);

    public Config() {
        this(5);
    }
    public Config(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int newValue) {
        multiplier = Math.max(newValue, 1);
    }

    public boolean isBlacklisted(Object item) {
        return blacklist.stream().anyMatch((c) -> c.isInstance(item));
    }
}
