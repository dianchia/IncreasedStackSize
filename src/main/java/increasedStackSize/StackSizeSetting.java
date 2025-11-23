package increasedStackSize;

import necesse.engine.GameLog;
import necesse.engine.GlobalData;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StackSizeSetting extends ModSettings {

    private static final String CONFIG_PATH = GlobalData.rootPath() + "settings/IncreasedStackSize.cfg";
    private static final String[] COMMON_PACKAGES = {"necesse.inventory.item", "necesse.inventory.item.armorItem.agedChampion", "necesse.inventory.item.armorItem.ancestor", "necesse.inventory.item.armorItem.ancientFossil", "necesse.inventory.item.armorItem.arachnid", "necesse.inventory.item.armorItem.arcanic", "necesse.inventory.item.armorItem.battlechef", "necesse.inventory.item.armorItem.bloodplate", "necesse.inventory.item.armorItem.cloth", "necesse.inventory.item.armorItem.copper", "necesse.inventory.item.armorItem.cosmetics.alien", "necesse.inventory.item.armorItem.cosmetics.animalKeeper", "necesse.inventory.item.armorItem.cosmetics.chicken", "necesse.inventory.item.armorItem.cosmetics.crimson", "necesse.inventory.item.armorItem.cosmetics.elder", "necesse.inventory.item.armorItem.cosmetics.exotic", "necesse.inventory.item.armorItem.cosmetics.farmer", "necesse.inventory.item.armorItem.cosmetics.frog", "necesse.inventory.item.armorItem.cosmetics.horse", "necesse.inventory.item.armorItem.cosmetics.hula", "necesse.inventory.item.armorItem.cosmetics.hunter", "necesse.inventory.item.armorItem.cosmetics.jester", "necesse.inventory.item.armorItem.cosmetics.lab", "necesse.inventory.item.armorItem.cosmetics.mage", "necesse.inventory.item.armorItem.cosmetics.merchant", "necesse.inventory.item.armorItem.cosmetics.miner", "necesse.inventory.item.armorItem.cosmetics.misc", "necesse.inventory.item.armorItem.cosmetics.pawnBroker", "necesse.inventory.item.armorItem.cosmetics.pirate", "necesse.inventory.item.armorItem.cosmetics.plague", "necesse.inventory.item.armorItem.cosmetics.rain", "necesse.inventory.item.armorItem.cosmetics.safari", "necesse.inventory.item.armorItem.cosmetics.sailor", "necesse.inventory.item.armorItem.cosmetics.smithing", "necesse.inventory.item.armorItem.cosmetics.snow", "necesse.inventory.item.armorItem.cosmetics.space", "necesse.inventory.item.armorItem.cosmetics.stylist", "necesse.inventory.item.armorItem.cosmetics.swim", "necesse.inventory.item.armorItem.cryowitch", "necesse.inventory.item.armorItem.crystal", "necesse.inventory.item.armorItem.dawn", "necesse.inventory.item.armorItem.deepfrost", "necesse.inventory.item.armorItem.demonic", "necesse.inventory.item.armorItem.dryad", "necesse.inventory.item.armorItem.dusk", "necesse.inventory.item.armorItem.frost", "necesse.inventory.item.armorItem.glacial", "necesse.inventory.item.armorItem.gold", "necesse.inventory.item.armorItem.gunslinger", "necesse.inventory.item.armorItem.iron", "necesse.inventory.item.armorItem.ivy", "necesse.inventory.item.armorItem.leather", "necesse.inventory.item.armorItem.mycelium", "necesse.inventory.item.armorItem.nightsteel", "necesse.inventory.item.armorItem.ninja", "necesse.inventory.item.armorItem.pharaoh", "necesse.inventory.item.armorItem.quartz", "necesse.inventory.item.armorItem.ravenlords", "necesse.inventory.item.armorItem.runebound", "necesse.inventory.item.armorItem.runestone", "necesse.inventory.item.armorItem.shadow", "necesse.inventory.item.armorItem.sharpshooter", "necesse.inventory.item.armorItem.slime", "necesse.inventory.item.armorItem.soldier", "necesse.inventory.item.armorItem.soulseed", "necesse.inventory.item.armorItem.spider", "necesse.inventory.item.armorItem.spiderite", "necesse.inventory.item.armorItem.supporter", "necesse.inventory.item.armorItem.thief", "necesse.inventory.item.armorItem.tungsten", "necesse.inventory.item.armorItem.voixd", "necesse.inventory.item.armorItem.widow", "necesse.inventory.item.armorItem.witch", "necesse.inventory.item.arrowItem", "necesse.inventory.item.baitItem", "necesse.inventory.item.bulletItem", "necesse.inventory.item.matItem", "necesse.inventory.item.miscItem", "necesse.inventory.item.mountItem", "necesse.inventory.item.placeableItem", "necesse.inventory.item.placeableItem.bucketItem", "necesse.inventory.item.placeableItem.consumableItem", "necesse.inventory.item.placeableItem.consumableItem.food", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.buffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.greaterBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.resourcePotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.utilityBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.spawnItems", "necesse.inventory.item.placeableItem.fishingRodItem", "necesse.inventory.item.placeableItem.followerSummonItem.petFollowerPlaceableItem", "necesse.inventory.item.placeableItem.mapItem", "necesse.inventory.item.placeableItem.objectItem", "necesse.inventory.item.placeableItem.tileItem", "necesse.inventory.item.questItem", "necesse.inventory.item.toolItem", "necesse.inventory.item.toolItem.axeToolItem", "necesse.inventory.item.toolItem.glaiveToolItem", "necesse.inventory.item.toolItem.miscToolItem", "necesse.inventory.item.toolItem.pickaxeToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.greatbowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem", "necesse.inventory.item.toolItem.shovelToolItem", "necesse.inventory.item.toolItem.spearToolItem", "necesse.inventory.item.toolItem.summonToolItem", "necesse.inventory.item.toolItem.swordToolItem", "necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem", "necesse.inventory.item.trinketItem"};
    private static final int DEFAULT_MULTIPLIER = 1;
    private final Map<String, Integer> worldMultipliers;
    private final Map<String, Integer> globalMultipliers;

    public StackSizeSetting() {
        this.worldMultipliers = new HashMap<>();
        this.globalMultipliers = new HashMap<>();
        ClassIndex.initialize();
//        this.loadGlobalSettings();
    }

    /**
     * Serializes a multiplier map into a compact string representation.
     * The format is:
     * key:value;key:value;...
     * This method does not perform escaping. Keys and values must not contain the ':' or ';' characters.
     *
     * @param multipliers The multiplier map to be converted
     * @return The map formatted as key:value;key:value;...
     */
    public static String mapToString(Map<String, Integer> multipliers) {
        return multipliers.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(";"));
    }

    /**
     * Deserializes a string created by {@link #mapToString(Map)} back into a
     * {@code Map<String, Integer>}. Entries that cannot be parsed are skipped.
     *
     * @param data The string to be converted into a map
     * @return A map converted from the string
     */
    public static Map<String, Integer> mapFromString(String data) {
        return Arrays.stream(data.split(";")).map(pair -> pair.split(":", 2)).filter(parts -> parts.length == 2).collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
    }

    /**
     * Get the multiplier from the given map with class name. If class name does not exist, it will recursively
     * search using the parent class name.
     *
     * @param className   The name of the class to be searched with
     * @param multipliers The map containing multipliers
     * @return The multiplier if found, and null if no match is found
     */
    private Integer getMultiplierFromMap(String className, Map<String, Integer> multipliers) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);

        // Quick check for exact match
        Integer multiplier = multipliers.get(simpleName);
        if (multiplier != null) return multiplier;

        // Try to find in class hierarchy
        Class<?> currentClass = resolveClass(className);
        while (currentClass != null && currentClass != Object.class) {
            String currentClassName = currentClass.getTypeName();
            String currentSimpleName = currentClassName.substring(currentClassName.lastIndexOf('.') + 1);
            multiplier = multipliers.get(currentSimpleName);
            if (multiplier != null) return multiplier;

            // Move up to parent class
            currentClass = currentClass.getSuperclass();
        }

        return null;
    }

    /**
     * Resolve a class name by prepending known package name onto it
     *
     * @param className The name of the class to be resolved
     * @return The resolved class, null if not found
     */
    public Class<?> resolveClass(String className) {
        return ClassIndex.resolveClass(className);
    }

    /**
     * Returns the effective stack size multiplier for the given class.
     * Lookup order:
     * <ol>
     *     <li>World-specific multipliers</li>
     *     <li>Global multipliers</li>
     *     <li>Fallback to {@link #DEFAULT_MULTIPLIER}</li>
     * </ol>
     * Class resolution attempts both exact name matches and superclass-based matches.
     *
     * @param className The name of the class to search for
     * @return The resolved multiplier, or {@link #DEFAULT_MULTIPLIER} if none if found
     */
    public int getMultiplier(String className) {
        // Check in world multipliers first
        Integer multiplier = getMultiplierFromMap(className, this.worldMultipliers);
        if (multiplier != null) return multiplier;

        // Check in global multipliers if not exists
        multiplier = getMultiplierFromMap(className, this.globalMultipliers);
        if (multiplier != null) return multiplier;

        // Return default multiplier
        return DEFAULT_MULTIPLIER;
    }

    /**
     * Set the multiplier for a given class on either world or global multipliers map determined by {@code type}
     *
     * @param className  The name of the class to set the multiplier for
     * @param multiplier The new multiplier to be set
     * @param type       Which multiplier map to set the multiplier at. Refer to {@link SettingType}
     */
    public void setMultiplier(String className, int multiplier, SettingType type) {
        if (type == SettingType.GLOBAL) globalMultipliers.put(className, multiplier);
        else if (type == SettingType.WORLD) worldMultipliers.put(className, multiplier);
    }

    /**
     * Unset the multiplier for a given class on either world or global multipliers map determined by {@code type}
     *
     * @param className The name of the class to set the multiplier for
     * @param type      Which multiplier map to unset the multiplier at. Refer to {@link SettingType}
     */
    public void unsetMultiplier(String className, SettingType type) {
        if (type == SettingType.GLOBAL) this.globalMultipliers.remove(className);
        else if (type == SettingType.WORLD) this.worldMultipliers.remove(className);
    }

    /**
     * Get the {@code unmodifiable view} of the map determined by {@code type}
     *
     * @param type The map to get
     * @return An unmodifiable view of the map
     */
    public Map<String, Integer> getMultipliers(SettingType type) {
        switch (type) {
            case GLOBAL:
                return Collections.unmodifiableMap(this.globalMultipliers);
            case WORLD:
                return Collections.unmodifiableMap(this.worldMultipliers);
            default:
                Map<String, Integer> multipliers = new HashMap<>(globalMultipliers);
                multipliers.putAll(worldMultipliers);
                return Collections.unmodifiableMap(multipliers);
        }
    }

    /**
     * Set the multipliers determined by {@code type}
     *
     * @param multipliers The new multipliers map
     */
    public void setMultipliers(Map<String, Integer> multipliers, SettingType type) {
        if (type == SettingType.WORLD)
            this.worldMultipliers.putAll(multipliers);
        else if (type == SettingType.GLOBAL)
            this.globalMultipliers.putAll(multipliers);
    }

    public void addWorldSaveData(SaveData save) {
        GameLog.debug.println("[IncreasedStackSize] Adding data to world save");
        save.addSafeString("IncreasedStackSize", mapToString(this.worldMultipliers));
    }

    public void applyWorldLoadData(LoadData save) {
        GameLog.debug.println("[IncreasedStackSize] Loading data from world save");
        // Compatible load from version 1.x
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            this.worldMultipliers.put("Item", save.getInt("stackSizeMultiplier"));
        } else if (save.hasLoadDataByName("IncreasedStackSize")) {
            Map<String, Integer> multipliers = mapFromString(save.getSafeString("IncreasedStackSize"));
            this.worldMultipliers.putAll(multipliers);
        }
    }

    @Override
    public void addSaveData(SaveData save) {
        GameLog.debug.println("[IncreasedStackSize] Adding data to global save");
        save.addSafeString("IncreasedStackSize", mapToString(this.globalMultipliers));
    }

    @Override
    public void applyLoadData(LoadData save) {
        GameLog.debug.println("[IncreasedStackSize] Loading data from global save");
        // Compatible load from version 1.x
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            this.globalMultipliers.put("Item", save.getInt("stackSizeMultiplier"));
        } else if (save.hasLoadDataByName("IncreasedStackSize")) {
            Map<String, Integer> multipliers = mapFromString(save.getSafeString("IncreasedStackSize"));
            this.globalMultipliers.putAll(multipliers);
        }
    }
}