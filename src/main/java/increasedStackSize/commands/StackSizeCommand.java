package increasedStackSize.commands;

import increasedStackSize.ClassIndex;
import increasedStackSize.IncreasedStackSize;
import increasedStackSize.SettingType;
import increasedStackSize.packets.PacketReadServerMultipliers;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.EnumParameterHandler;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.commands.parameterHandlers.StringParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StackSizeCommand extends ModularChatCommand {
    public StackSizeCommand() {
        super("stacksize", "Commands for configuring stack size multiplier", PermissionLevel.OWNER, false,
                new CmdParameter("get/set/unset", new PresetStringParameterHandler("get", "set", "unset"), false),
                new CmdParameter("world/global", new EnumParameterHandler<>(SettingType.WORLD, SettingType.values()), true),
                new CmdParameter("name", new StringParameterHandler(), true, new CmdParameter("multiplier", new IntParameterHandler(null), true))
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog log) {
        switch ((String) args[0]) {
            case "get":
                this.getStackSize(args, log);
                break;
            case "set":
                this.setStackSize(server, args, log);
                break;
            case "unset":
                this.unsetStackSize(server, args, log);
                break;
        }
    }

    private void getStackSize(Object[] args, CommandLog log) {
        log.add("§#8AADF4========================");
        log.add("§#8AADF4= Increased Stack Size =");
        log.add("§#8AADF4========================");
        log.add("§#EED49F[name: world (global)]");

        if (args[2] != null) {
            String name = (String) args[2];
            Class<?> cls = ClassIndex.resolveClass(name);
            if (cls == null) {
                log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
                return;
            }

            int worldValue = IncreasedStackSize.settings.getMultiplier(cls, SettingType.WORLD);
            int globalValue = IncreasedStackSize.settings.getMultiplier(cls, SettingType.GLOBAL);
            log.add("§#8AADF4" + name + ": " + worldValue + " (" + globalValue + ")");
        } else {
            Map<String, Integer> globalMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.GLOBAL);
            Map<String, Integer> worldMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.WORLD);

            Set<String> names = new TreeSet<>();
            names.addAll(globalMultipliers.keySet());
            names.addAll(worldMultipliers.keySet());
            List<String> sorted = names.stream().sorted().collect(Collectors.toList());

            for (String name : sorted) {
                Integer worldValue = worldMultipliers.get(name);
                Integer globalValue = globalMultipliers.get(name);

                String shortName = name.substring(name.lastIndexOf('.') + 1);
                String worldValueStr = worldValue != null ? String.valueOf(worldValue) : "unset";
                String globalValueStr = globalValue != null ? String.valueOf(globalValue) : "unset";
                log.add("§#8AADF4" + shortName + ": " + worldValueStr + " (" + globalValueStr + ")");
            }
        }
    }

    private void setStackSize(Server server, Object[] args, CommandLog log) {
        if (args[3] == null) {
            log.add(Localization.translate("increasedstacksize", "nomultiplier"));
            return;
        }
        int multiplier = (int) args[3];
        if (multiplier < 1) {
            log.add(Localization.translate("increasedstacksize", "errorsmaller"));
            return;
        }

        String name = args[2] == null ? "Item" : (String) args[2];
        Class<?> cls = ClassIndex.resolveClass(name);
        if (cls == null) {
            log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
            return;
        }

        SettingType type = (SettingType) args[1];
        IncreasedStackSize.settings.setMultiplier(cls, multiplier, type);

        server.network.sendToAllClients(new PacketChatMessage(Localization.translate("increasedstacksize", "multiplierschanged", "name", name, "multiplier", multiplier)));
        server.network.sendToAllClients(new PacketReadServerMultipliers(false));
    }

    private void unsetStackSize(Server server, Object[] args, CommandLog log) {
        if (args[2] == null) {
            log.add(Localization.translate("increasedstacksize", "noitemname"));
            return;
        }

        String name = (String) args[2];
        Class<?> cls = ClassIndex.resolveClass(name);
        if (cls == null) {
            log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
            return;
        }

        SettingType type = (SettingType) args[1];
        IncreasedStackSize.settings.unsetMultiplier(cls, type);
        log.add(Localization.translate("increasedstacksize", "multipliersunset", "name", name));
        server.network.sendToAllClients(new PacketReadServerMultipliers(false));
    }
}
