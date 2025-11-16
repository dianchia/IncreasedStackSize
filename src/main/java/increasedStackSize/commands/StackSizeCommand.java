package increasedStackSize.commands;

import increasedStackSize.IncreasedStackSize;
import increasedStackSize.packets.PacketReadServerSettings;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.commands.parameterHandlers.StringParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.Map;

public class StackSizeCommand extends ModularChatCommand {
    public StackSizeCommand() {
        super("stacksize", "Commands for configuring stack size multiplier", PermissionLevel.OWNER, false, new CmdParameter("get/set/unset", new PresetStringParameterHandler("get", "set", "unset"), false), new CmdParameter("name", new StringParameterHandler(), true, new CmdParameter("multiplier", new IntParameterHandler(null), true)));
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
        if (args[1] != null) {
            String name = (String) args[1];
            if (IncreasedStackSize.settings.resolveClass(name) == null) {
                log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
                return;
            }

            int multiplier = IncreasedStackSize.settings.getMultiplier(name);
            log.add(name + ": " + multiplier);
        } else {
            Map<String, Integer> multipliers = IncreasedStackSize.settings.getMultipliers();
            multipliers.forEach((name, mul) -> log.add(name + ": " + mul));
        }
    }

    private void setStackSize(Server server, Object[] args, CommandLog log) {
        if (args[2] == null) {
            log.add(Localization.translate("increasedstacksize", "nomultiplier"));
            return;
        }

        String name = args[1] == null ? "Item" : (String) args[1];
        if (IncreasedStackSize.settings.resolveClass(name) == null) {
            log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
            return;
        }

        int multiplier = (int) args[2];
        if (multiplier <= 0) {
            log.add(Localization.translate("increasedstacksize", "errorsmaller"));
            return;
        }
        IncreasedStackSize.settings.setMultiplier(name, multiplier);

        server.network.sendToAllClients(new PacketChatMessage(Localization.translate("increasedstacksize", "changedwarning", "name", name, "multiplier", multiplier)));
        server.network.sendToAllClients(new PacketReadServerSettings(false));
    }

    private void unsetStackSize(Server server, Object[] args, CommandLog log) {
        if (args[1] == null)
            return;

        String name = (String) args[1];
        if (IncreasedStackSize.settings.resolveClass(name) == null) {
            log.add(Localization.translate("increasedstacksize", "itemnotfound", "name", name));
            return;
        }

        IncreasedStackSize.settings.unsetMultiplier(name);
        server.network.sendToAllClients(new PacketReadServerSettings(false));
    }
}
