package increasedStackSize;

import increasedStackSize.commands.ChangeStackSizeCommand;
import increasedStackSize.commands.GetStackSizeCommand;
import increasedStackSize.commands.StackSizeCommand;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class IncreasedStackSize {

    public static int stackSizeMultiplier = 5;
    public static int newStackSizeMultiplier = 5;

    public static void setStackSizeMultiplier(int value, boolean overwrite) {
        int newValue;
        if (value <= 0) newValue = 1;
        else newValue = value;
        if (overwrite) {
            stackSizeMultiplier = newValue;
        }
        newStackSizeMultiplier = newValue;
    }

    public void preInit() {
        Compat.backwardCompatLoad();
    }

    public void init() {
    }

    public void initResources() {
    }

    public void postInit() {
        CommandsManager.registerServerCommand(new ChangeStackSizeCommand());
        CommandsManager.registerServerCommand(new GetStackSizeCommand());
        CommandsManager.registerServerCommand(new StackSizeCommand());
    }

}
