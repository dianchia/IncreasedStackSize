package increasedStackSize;

import necesse.engine.GameLog;
import necesse.engine.GlobalData;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackSizeSetting extends ModSettings {
    private static final String CONFIG_PATH = GlobalData.rootPath() + "settings/IncreasedStackSize.cfg";
    private static final String[] COMMON_PACKAGES = {"necesse.inventory.item", "necesse.inventory.item.armorItem.agedChampion", "necesse.inventory.item.armorItem.ancestor", "necesse.inventory.item.armorItem.ancientFossil", "necesse.inventory.item.armorItem.arachnid", "necesse.inventory.item.armorItem.arcanic", "necesse.inventory.item.armorItem.battlechef", "necesse.inventory.item.armorItem.bloodplate", "necesse.inventory.item.armorItem.cloth", "necesse.inventory.item.armorItem.copper", "necesse.inventory.item.armorItem.cosmetics.alien", "necesse.inventory.item.armorItem.cosmetics.animalKeeper", "necesse.inventory.item.armorItem.cosmetics.chicken", "necesse.inventory.item.armorItem.cosmetics.crimson", "necesse.inventory.item.armorItem.cosmetics.elder", "necesse.inventory.item.armorItem.cosmetics.exotic", "necesse.inventory.item.armorItem.cosmetics.farmer", "necesse.inventory.item.armorItem.cosmetics.frog", "necesse.inventory.item.armorItem.cosmetics.horse", "necesse.inventory.item.armorItem.cosmetics.hula", "necesse.inventory.item.armorItem.cosmetics.hunter", "necesse.inventory.item.armorItem.cosmetics.jester", "necesse.inventory.item.armorItem.cosmetics.lab", "necesse.inventory.item.armorItem.cosmetics.mage", "necesse.inventory.item.armorItem.cosmetics.merchant", "necesse.inventory.item.armorItem.cosmetics.miner", "necesse.inventory.item.armorItem.cosmetics.misc", "necesse.inventory.item.armorItem.cosmetics.pawnBroker", "necesse.inventory.item.armorItem.cosmetics.pirate", "necesse.inventory.item.armorItem.cosmetics.plague", "necesse.inventory.item.armorItem.cosmetics.rain", "necesse.inventory.item.armorItem.cosmetics.safari", "necesse.inventory.item.armorItem.cosmetics.sailor", "necesse.inventory.item.armorItem.cosmetics.smithing", "necesse.inventory.item.armorItem.cosmetics.snow", "necesse.inventory.item.armorItem.cosmetics.space", "necesse.inventory.item.armorItem.cosmetics.stylist", "necesse.inventory.item.armorItem.cosmetics.swim", "necesse.inventory.item.armorItem.cryowitch", "necesse.inventory.item.armorItem.crystal", "necesse.inventory.item.armorItem.dawn", "necesse.inventory.item.armorItem.deepfrost", "necesse.inventory.item.armorItem.demonic", "necesse.inventory.item.armorItem.dryad", "necesse.inventory.item.armorItem.dusk", "necesse.inventory.item.armorItem.frost", "necesse.inventory.item.armorItem.glacial", "necesse.inventory.item.armorItem.gold", "necesse.inventory.item.armorItem.gunslinger", "necesse.inventory.item.armorItem.iron", "necesse.inventory.item.armorItem.ivy", "necesse.inventory.item.armorItem.leather", "necesse.inventory.item.armorItem.mycelium", "necesse.inventory.item.armorItem.nightsteel", "necesse.inventory.item.armorItem.ninja", "necesse.inventory.item.armorItem.pharaoh", "necesse.inventory.item.armorItem.quartz", "necesse.inventory.item.armorItem.ravenlords", "necesse.inventory.item.armorItem.runebound", "necesse.inventory.item.armorItem.runestone", "necesse.inventory.item.armorItem.shadow", "necesse.inventory.item.armorItem.sharpshooter", "necesse.inventory.item.armorItem.slime", "necesse.inventory.item.armorItem.soldier", "necesse.inventory.item.armorItem.soulseed", "necesse.inventory.item.armorItem.spider", "necesse.inventory.item.armorItem.spiderite", "necesse.inventory.item.armorItem.supporter", "necesse.inventory.item.armorItem.thief", "necesse.inventory.item.armorItem.tungsten", "necesse.inventory.item.armorItem.voixd", "necesse.inventory.item.armorItem.widow", "necesse.inventory.item.armorItem.witch", "necesse.inventory.item.arrowItem", "necesse.inventory.item.baitItem", "necesse.inventory.item.bulletItem", "necesse.inventory.item.matItem", "necesse.inventory.item.miscItem", "necesse.inventory.item.mountItem", "necesse.inventory.item.placeableItem", "necesse.inventory.item.placeableItem.bucketItem", "necesse.inventory.item.placeableItem.consumableItem", "necesse.inventory.item.placeableItem.consumableItem.food", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.buffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.greaterBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.resourcePotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.utilityBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.spawnItems", "necesse.inventory.item.placeableItem.fishingRodItem", "necesse.inventory.item.placeableItem.followerSummonItem.petFollowerPlaceableItem", "necesse.inventory.item.placeableItem.mapItem", "necesse.inventory.item.placeableItem.objectItem", "necesse.inventory.item.placeableItem.tileItem", "necesse.inventory.item.questItem", "necesse.inventory.item.toolItem", "necesse.inventory.item.toolItem.axeToolItem", "necesse.inventory.item.toolItem.glaiveToolItem", "necesse.inventory.item.toolItem.miscToolItem", "necesse.inventory.item.toolItem.pickaxeToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.greatbowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem", "necesse.inventory.item.toolItem.shovelToolItem", "necesse.inventory.item.toolItem.spearToolItem", "necesse.inventory.item.toolItem.summonToolItem", "necesse.inventory.item.toolItem.swordToolItem", "necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem", "necesse.inventory.item.trinketItem"};
    private static final String DEFAULT_KEY = "Item";
    private static final int DEFAULT_MULTIPLIER = 1;
    private Map<String, Integer> multipliers;

    public StackSizeSetting() {
        this.multipliers = new HashMap<>();
        this.multipliers.put(DEFAULT_KEY, DEFAULT_MULTIPLIER);
        this.loadSettings();
    }

    public static String mapToString(Map<String, Integer> multipliers) {
        return multipliers.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(";"));
    }

    public static Map<String, Integer> mapFromString(String data) {
        return Arrays.stream(data.split(";")).map(pair -> pair.split(":", 2)).filter(parts -> parts.length == 2).collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
    }

    private void loadSettings() {
        Path configPath = Paths.get(CONFIG_PATH);
        GameLog.debug.println("Loading config from " + configPath);

        // Check if file exists before trying to read
        if (!Files.exists(configPath)) {
            GameLog.out.println("[IncreasedStackSize] No config file found. Default to a multiplier of " + DEFAULT_MULTIPLIER + " for all items");
            return;
        }

        try (Stream<String> lines = Files.lines(configPath)) {
            lines.forEach(this::processLine);
        } catch (IOException e) {
            GameLog.warn.println("[IncreasedStackSize] Error reading config file: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        // Skip empty lines and comments
        if (line.trim().isEmpty() || line.trim().startsWith("#")) return;

        String[] comps = line.split("=", 2);
        if (comps.length != 2) {
            GameLog.warn.println("[IncreasedStackSize] Invalid config line format: " + line);
            return;
        }

        String className = comps[0].trim();
        String multiplierStr = comps[1].trim();
        try {
            if (resolveClass(className) == null) {
                GameLog.warn.println("Item `" + className + "` not found.");
                return;
            }

            int multiplier = Integer.parseInt(multiplierStr);
            multipliers.put(className, multiplier);
        } catch (NumberFormatException e) {
            GameLog.warn.println("Error parsing multiplier '" + multiplierStr + "' for class " + className + ": " + e.getMessage());
            multipliers.put(className, DEFAULT_MULTIPLIER);
        }
    }

    public Class<?> resolveClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // Fall through to try with package name
        }

        for (String packageName : COMMON_PACKAGES) {
            try {
                String fullName = packageName + "." + className;
                return Class.forName(fullName);
            } catch (ClassNotFoundException e) {
                // Try the next package name
            }
        }

        // Not found in known packages
        return null;
    }

    public int getMultiplier(String className) {
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

        // Fallback to default multiplier
        return multipliers.getOrDefault(DEFAULT_KEY, DEFAULT_MULTIPLIER);
    }

    public void setMultiplier(String className, int multiplier) {
        if (multiplier <= 0) {
            GameLog.warn.println("[IncreasedStackSize] Invalid multiplier of " + multiplier + " for item `" + className + "`");
            multiplier = 1;
        }
        multipliers.put(className, multiplier);
    }

    public void unsetMultiplier(String className) {
        this.multipliers.remove(className);
    }

    public Map<String, Integer> getMultipliers() {
        return Collections.unmodifiableMap(multipliers);
    }

    public void setMultipliers(Map<String, Integer> multipliers) {
        this.multipliers.putAll(multipliers);
    }

    @Override
    public void addSaveData(SaveData save) {
        save.addSafeString("IncreasedStackSize", mapToString(this.multipliers));
    }

    @Override
    public void applyLoadData(LoadData save) {
        // Compatible load from version 1.x
        if (save.hasLoadDataByName("stackSizeMultiplier")) {
            this.multipliers.put("Item", save.getInt("stackSizeMultiplier"));
        } else if (save.hasLoadDataByName("IncreasedStackSize")) {
            this.multipliers = mapFromString(save.getSafeString("IncreasedStackSize"));
        }
    }

    @Override
    public String toString() {
        return StackSizeSetting.mapToString(this.multipliers);
    }
}
