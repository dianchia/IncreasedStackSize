package increasedStackSize.commands;

import increasedStackSize.IncreasedStackSize;
import increasedStackSize.SettingType;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.StringParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.*;

public class GetStackSizeCommand extends ModularChatCommand {
    public GetStackSizeCommand() {
        super("getstacksize", "Show the stack size multipliers", PermissionLevel.USER, false,
                new CmdParameter("classname", new StringParameterHandler(), true)
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] strings, CommandLog log) {
        log.add("§#8AADF4========================");
        log.add("§#8AADF4= Increased Stack Size =");
        log.add("§#8AADF4========================");
        log.add("§#EED49Fname: world (global)");

        if (args[0] != null) {
            String name = (String) args[0];
            if (IncreasedStackSize.settings.resolveClass(name) == null) {
                log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
                return;
            }

            int worldValue = IncreasedStackSize.settings.getMultipliers(SettingType.WORLD).getOrDefault(name, 1);
            int globalValue = IncreasedStackSize.settings.getMultipliers(SettingType.GLOBAL).getOrDefault(name, 1);
            log.add("§#8AADF4" + name + ": " + worldValue + "(" + globalValue + ")");
        } else {
            Map<String, Integer> globalMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.GLOBAL);
            Map<String, Integer> worldMultipliers = IncreasedStackSize.settings.getMultipliers(SettingType.WORLD);

            Set<String> names = new TreeSet<>();
            names.addAll(globalMultipliers.keySet());
            names.addAll(worldMultipliers.keySet());
            List<String> sorted = new ArrayList<>(names);
            Collections.sort(sorted);

            for (String name : sorted) {
                int worldValue = worldMultipliers.getOrDefault(name, 1);
                int globalValue = globalMultipliers.getOrDefault(name, 1);
                log.add("§#8AADF4" + name + ": " + worldValue + "(" + globalValue + ")");
            }
        }
    }
}
