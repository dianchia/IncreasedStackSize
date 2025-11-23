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
