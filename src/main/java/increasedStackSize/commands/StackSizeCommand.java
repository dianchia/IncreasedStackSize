package increasedStackSize.commands;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.Objects;

public class StackSizeCommand extends ModularChatCommand {
    public StackSizeCommand() {
        super(
                "stacksize",
                "Commands for configuring stack size multiplier",
                PermissionLevel.OWNER,
                false,
                new CmdParameter("command", new PresetStringParameterHandler("get", "set"), false),
                new CmdParameter("size", new IntParameterHandler(), true)
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        String mode = (String) args[0];
        if (Objects.equals(mode, "get")) {
            String message = Localization.translate("increasedstacksize", "getstacksize", "stacksize", IncreasedStackSize.stackSizeMultiplier);
            commandLog.add(message);
            return;
        }

        int newStackSize = (int) args[1];
        if (newStackSize <= 0) {
            commandLog.add(Localization.translate("increasedstacksize", "errorsmaller"));
            return;
        }

        String name = serverClient == null ? "Server" : serverClient.playerMob.getDisplayName();
        String message = Localization.translate("increasedstacksize", "changedwarning", "name", name, "stacksize", newStackSize);
        server.network.sendToAllClients(new PacketChatMessage(message));
        IncreasedStackSize.setStackSizeMultiplier(newStackSize, false);
    }
}
