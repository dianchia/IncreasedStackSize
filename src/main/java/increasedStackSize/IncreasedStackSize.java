package increasedStackSize;

import increasedStackSize.commands.ChangeStackSizeCommand;
import increasedStackSize.commands.GetStackSizeCommand;
import increasedStackSize.commands.StackSizeCommand;
import increasedStackSize.packets.PacketReadServerSettings;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.PacketRegistry;

@ModEntry
public class IncreasedStackSize {

    public static final StackSizeSetting settings = new StackSizeSetting();

    public static ModSettings initSettings() {
        return settings;
    }

    public void preInit() {
    }

    public void init() {
        PacketRegistry.registerPacket(PacketReadServerSettings.class);
    }

    public void initResources() {
    }

    public void postInit() {
        CommandsManager.registerServerCommand(new ChangeStackSizeCommand());
        CommandsManager.registerServerCommand(new GetStackSizeCommand());
        CommandsManager.registerServerCommand(new StackSizeCommand());

//        Set<String> items = new HashSet<>();
//        for (Item item : ItemRegistry.getItems()) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(ItemRegistry.getLocalization(item.getID()).translate()).append(",");
//
//            String className = item.getClass().getTypeName();
//            Class<?> currentClass = settings.resolveClass(className);
//
//            while (currentClass != null && currentClass != Object.class) {
//                String currentClassName = currentClass.getSimpleName();
//                if (!currentClassName.isEmpty())
//                    sb.append(currentClassName).append(";");
//                currentClass = currentClass.getSuperclass();
//            }
//
//            items.add(sb.toString());
//        }
//
//        try {
//            Files.write(Paths.get("docs/items.csv"), items, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
