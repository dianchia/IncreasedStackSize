package increasedStackSize;

import necesse.engine.GameLog;
import necesse.engine.GlobalData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public final class ClassIndex {
    private static final String[] COMMON_PACKAGES = {"necesse.inventory.item", "necesse.inventory.item.armorItem.agedChampion", "necesse.inventory.item.armorItem.ancestor", "necesse.inventory.item.armorItem.ancientFossil", "necesse.inventory.item.armorItem.arachnid", "necesse.inventory.item.armorItem.arcanic", "necesse.inventory.item.armorItem.battlechef", "necesse.inventory.item.armorItem.bloodplate", "necesse.inventory.item.armorItem.cloth", "necesse.inventory.item.armorItem.copper", "necesse.inventory.item.armorItem.cosmetics.alien", "necesse.inventory.item.armorItem.cosmetics.animalKeeper", "necesse.inventory.item.armorItem.cosmetics.chicken", "necesse.inventory.item.armorItem.cosmetics.crimson", "necesse.inventory.item.armorItem.cosmetics.elder", "necesse.inventory.item.armorItem.cosmetics.exotic", "necesse.inventory.item.armorItem.cosmetics.farmer", "necesse.inventory.item.armorItem.cosmetics.frog", "necesse.inventory.item.armorItem.cosmetics.horse", "necesse.inventory.item.armorItem.cosmetics.hula", "necesse.inventory.item.armorItem.cosmetics.hunter", "necesse.inventory.item.armorItem.cosmetics.jester", "necesse.inventory.item.armorItem.cosmetics.lab", "necesse.inventory.item.armorItem.cosmetics.mage", "necesse.inventory.item.armorItem.cosmetics.merchant", "necesse.inventory.item.armorItem.cosmetics.miner", "necesse.inventory.item.armorItem.cosmetics.misc", "necesse.inventory.item.armorItem.cosmetics.pawnBroker", "necesse.inventory.item.armorItem.cosmetics.pirate", "necesse.inventory.item.armorItem.cosmetics.plague", "necesse.inventory.item.armorItem.cosmetics.rain", "necesse.inventory.item.armorItem.cosmetics.safari", "necesse.inventory.item.armorItem.cosmetics.sailor", "necesse.inventory.item.armorItem.cosmetics.smithing", "necesse.inventory.item.armorItem.cosmetics.snow", "necesse.inventory.item.armorItem.cosmetics.space", "necesse.inventory.item.armorItem.cosmetics.stylist", "necesse.inventory.item.armorItem.cosmetics.swim", "necesse.inventory.item.armorItem.cryowitch", "necesse.inventory.item.armorItem.crystal", "necesse.inventory.item.armorItem.dawn", "necesse.inventory.item.armorItem.deepfrost", "necesse.inventory.item.armorItem.demonic", "necesse.inventory.item.armorItem.dryad", "necesse.inventory.item.armorItem.dusk", "necesse.inventory.item.armorItem.frost", "necesse.inventory.item.armorItem.glacial", "necesse.inventory.item.armorItem.gold", "necesse.inventory.item.armorItem.gunslinger", "necesse.inventory.item.armorItem.iron", "necesse.inventory.item.armorItem.ivy", "necesse.inventory.item.armorItem.leather", "necesse.inventory.item.armorItem.mycelium", "necesse.inventory.item.armorItem.nightsteel", "necesse.inventory.item.armorItem.ninja", "necesse.inventory.item.armorItem.pharaoh", "necesse.inventory.item.armorItem.quartz", "necesse.inventory.item.armorItem.ravenlords", "necesse.inventory.item.armorItem.runebound", "necesse.inventory.item.armorItem.runestone", "necesse.inventory.item.armorItem.shadow", "necesse.inventory.item.armorItem.sharpshooter", "necesse.inventory.item.armorItem.slime", "necesse.inventory.item.armorItem.soldier", "necesse.inventory.item.armorItem.soulseed", "necesse.inventory.item.armorItem.spider", "necesse.inventory.item.armorItem.spiderite", "necesse.inventory.item.armorItem.supporter", "necesse.inventory.item.armorItem.thief", "necesse.inventory.item.armorItem.tungsten", "necesse.inventory.item.armorItem.voixd", "necesse.inventory.item.armorItem.widow", "necesse.inventory.item.armorItem.witch", "necesse.inventory.item.arrowItem", "necesse.inventory.item.baitItem", "necesse.inventory.item.bulletItem", "necesse.inventory.item.matItem", "necesse.inventory.item.miscItem", "necesse.inventory.item.mountItem", "necesse.inventory.item.placeableItem", "necesse.inventory.item.placeableItem.bucketItem", "necesse.inventory.item.placeableItem.consumableItem", "necesse.inventory.item.placeableItem.consumableItem.food", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.buffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.greaterBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.resourcePotions", "necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.utilityBuffPotions", "necesse.inventory.item.placeableItem.consumableItem.spawnItems", "necesse.inventory.item.placeableItem.fishingRodItem", "necesse.inventory.item.placeableItem.followerSummonItem.petFollowerPlaceableItem", "necesse.inventory.item.placeableItem.mapItem", "necesse.inventory.item.placeableItem.objectItem", "necesse.inventory.item.placeableItem.tileItem", "necesse.inventory.item.questItem", "necesse.inventory.item.toolItem", "necesse.inventory.item.toolItem.axeToolItem", "necesse.inventory.item.toolItem.glaiveToolItem", "necesse.inventory.item.toolItem.miscToolItem", "necesse.inventory.item.toolItem.pickaxeToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.greatbowProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.meleeProjectileToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem", "necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem", "necesse.inventory.item.toolItem.shovelToolItem", "necesse.inventory.item.toolItem.spearToolItem", "necesse.inventory.item.toolItem.summonToolItem", "necesse.inventory.item.toolItem.swordToolItem", "necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem", "necesse.inventory.item.trinketItem"};
    private static final Map<String, String> SIMPLE_TO_FQN = new HashMap<>(); // Simple name -> Fully qualified name
    private static final Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();
    private static boolean INITIALIZED = false;

    public static synchronized void initialize() {
        if (INITIALIZED) return;
        INITIALIZED = true;

        try {
            scanJar();
        } catch (Exception e) {
            GameLog.warn.println("[IncreasedStackSize] Failed to build class index: " + e.getMessage());
        }
    }

    private static void scanJar() throws IOException {
        Path jarPath = Paths.get(GlobalData.rootPath() + "Necesse.jar");
        GameLog.debug.println("Scanning for class from jar file located at " + jarPath);
        try (JarInputStream jarFile = new JarInputStream(Files.newInputStream(jarPath))) {
            JarEntry entry;

            while ((entry = jarFile.getNextJarEntry()) != null) {
                if (entry.getName().startsWith("necesse/inventory/item") && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.').replace(".class", "");
                    indexClassName(className);
                }
            }
        }
    }

    private static void indexClassName(String fqn) {
        String simple = fqn.substring(fqn.lastIndexOf('.') + 1);
        SIMPLE_TO_FQN.put(simple, fqn);
    }

    public static Class<?> resolveClass(String name) {
        initialize();

        // Check for exact match (fully qualified name)
        if (CLASS_CACHE.containsKey(name)) return CLASS_CACHE.get(name);

        // Fully qualified name, but not yet cached
        try {
            Class<?> cls = Class.forName(name);
            CLASS_CACHE.put(name, cls);
            return cls;
        } catch (ClassNotFoundException ignore) {
        }

        // Resolve simple name to FQN
        String fqn = SIMPLE_TO_FQN.get(name);
        if (fqn == null) return null;
        return CLASS_CACHE.computeIfAbsent(fqn, key -> {
            try {
                return Class.forName(key);
            } catch (ClassNotFoundException e) {
                return null;
            }
        });
    }
}
