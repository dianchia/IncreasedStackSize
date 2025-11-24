package increasedStackSize;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.inventory.item.Item;

import java.util.*;
import java.util.stream.Collectors;

public class StackSizeSetting extends ModSettings {

    private static final int DEFAULT_MULTIPLIER = 1;
    private final EnumMap<SettingType, Map<String, Integer>> multipliers = new EnumMap<>(SettingType.class);

    public StackSizeSetting() {
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
        if (multipliers == null) return "";

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
        if (data.trim().isEmpty()) return Collections.emptyMap();
        return Arrays.stream(data.split(";")).map(pair -> pair.split(":", 2)).filter(parts -> parts.length == 2).collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
    }

    /**
     * Get the multiplier from the given map with class name. If class name does not exist, it will recursively
     * search using the parent class name.
     *
     * @param itemClass The item class
     * @param type      Whether world or global multipliers map to search from
     * @return The multiplier if found, and null if no match is found
     */
    private Integer getMultiplierFromMap(Class<?> itemClass, SettingType type) {
        Map<String, Integer> multipliers = this.multipliers.get(type);
        if (multipliers == null) return null;

        String name = itemClass.getTypeName();

        // Quick check for exact match
        Integer multiplier = multipliers.get(name);
        if (multiplier != null) return multiplier;

        // Try to find in class hierarchy
        Class<?> currentClass = ClassIndex.resolveClass(name);
        while (currentClass != null && currentClass != Object.class) {
            String currentName = currentClass.getTypeName();
            multiplier = multipliers.get(currentName);
            if (multiplier != null) return multiplier;

            // Move up to parent class
            currentClass = currentClass.getSuperclass();
        }

        return null;
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
     * @param itemClass The item class
     * @return The resolved multiplier, or {@link #DEFAULT_MULTIPLIER} if none is found
     */
    public int getMultiplier(Class<?> itemClass) {
        // Check in world multipliers first
        Integer multiplier = getMultiplierFromMap(itemClass, SettingType.WORLD);
        if (multiplier != null) return multiplier;

        // Check in global multipliers if not exists
        multiplier = getMultiplierFromMap(itemClass, SettingType.GLOBAL);
        if (multiplier != null) return multiplier;

        // Return default multiplier
        return DEFAULT_MULTIPLIER;
    }

    public int getMultiplier(Class<?> itemClass, SettingType type) {
        Map<String, Integer> inner = this.multipliers.get(type);
        if (inner == null) return DEFAULT_MULTIPLIER;

        return inner.getOrDefault(itemClass.getTypeName(), DEFAULT_MULTIPLIER);
    }

    /**
     * Get the {@code unmodifiable view} of the map determined by {@code type}
     *
     * @param type The map to get
     * @return An unmodifiable view of the map
     */
    public Map<String, Integer> getMultipliers(SettingType type) {
        Map<String, Integer> inner = multipliers.get(type);
        if (inner == null) return Collections.emptyMap();

        return Collections.unmodifiableMap(inner);
    }

    /**
     * Set the multiplier for a given class on either world or global multipliers map determined by {@code type}
     *
     * @param itemClass  The item class to set the multiplier for
     * @param multiplier The new multiplier to be set
     * @param type       Which multiplier map to set the multiplier at. Refer to {@link SettingType}
     */
    public void setMultiplier(Class<?> itemClass, int multiplier, SettingType type) {
        Map<String, Integer> inner = this.multipliers.computeIfAbsent(type, t -> new HashMap<>());
        inner.put(itemClass.getTypeName(), multiplier);
    }

    /**
     * Overwrite the multipliers map determined by {@code type}
     *
     * @param multipliers The new multipliers map
     */
    public void setMultipliers(Map<String, Integer> multipliers, SettingType type) {
        this.multipliers.put(type, new HashMap<>(multipliers));
    }

    /**
     * Unset the multiplier for a given class on either world or global multipliers map determined by {@code type}
     *
     * @param itemClass The item class to unset the multiplier for
     * @param type      Which multiplier map to unset the multiplier at. Refer to {@link SettingType}
     */
    public void unsetMultiplier(Class<?> itemClass, SettingType type) {
        Map<String, Integer> inner = this.multipliers.get(type);
        if (inner != null) inner.remove(itemClass.getTypeName());
    }

    public void addWorldSaveData(SaveData save) {
        save.addSafeString("IncreasedStackSize", mapToString(getMultipliers(SettingType.WORLD)));
    }

    public void applyWorldLoadData(LoadData save) {
        // Compatible load from version 1.x
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            setMultiplier(Item.class, save.getInt("stackSizeMultiplier"), SettingType.WORLD);
        } else if (save.hasLoadDataByName("IncreasedStackSize")) {
            Map<String, Integer> multipliers = mapFromString(save.getSafeString("IncreasedStackSize"));
            multipliers.forEach((name, multiplier) -> {
                Class<?> cls = ClassIndex.resolveClass(name);
                if (cls != null) setMultiplier(cls, multiplier, SettingType.WORLD);
            });
        }
    }

    @Override
    public void addSaveData(SaveData save) {
        save.addSafeString("IncreasedStackSize", mapToString(getMultipliers(SettingType.GLOBAL)));
    }

    @Override
    public void applyLoadData(LoadData save) {
        // Compatible load from version 1.x
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            setMultiplier(Item.class, save.getInt("stackSizeMultiplier"), SettingType.GLOBAL);
        } else if (save.hasLoadDataByName("IncreasedStackSize")) {
            if (!getMultipliers(SettingType.GLOBAL).isEmpty())
                return;

            Map<String, Integer> multipliers = mapFromString(save.getSafeString("IncreasedStackSize"));
            multipliers.forEach((name, multiplier) -> {
                Class<?> cls = ClassIndex.resolveClass(name);
                if (cls != null) setMultiplier(cls, multiplier, SettingType.GLOBAL);
            });
        }
    }
}