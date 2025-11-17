package increasedStackSize.commands;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

public class ChangeStackSizeCommand extends ModularChatCommand {
    public ChangeStackSizeCommand() {
        super("setstacksizemultiplier", "Set multiplier for stack size", PermissionLevel.OWNER, false, new CmdParameter("stacksize", new IntParameterHandler()));
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        commandLog.add(Localization.translate("increasedstacksize", "newcommand"));
        int newStackSize = (int) args[0];
        if (newStackSize > 0) {
            String name = serverClient == null ? "Server" : serverClient.playerMob.getDisplayName();
            String message = Localization.translate("increasedstacksize", "changedwarning", "name", name, "stacksize", newStackSize);
            server.network.sendToAllClients(new PacketChatMessage(message));
//            IncreasedStackSize.setStackSizeMultiplier(newStackSize, false);
        } else {
            commandLog.add(Localization.translate("increasedstacksize", "errorsmaller"));
        }
    }
}
