package increasedStackSize;

import increasedStackSize.commands.ChangeStackSizeCommand;
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
        System.out.println("Checking for backward compatibility...");
        Compat.backwardCompatLoad();
    }

    public void init() {
//        ItemRegistry.registerItem("stacksizecontroller", new StackSizeControllerItem(1), 10, true);
//        STACKSIZE_CONTROLLER_CONTAINER = ContainerRegistry.registerContainer(
//                (client, uniqueSeed, content) -> new StackSizeControllerContainerForm<>(client, new StackSizeControllerContainer(client.getClient(), uniqueSeed, content)),
//                (client, uniqueSeed, content, serverObj) -> new StackSizeControllerContainer(client, uniqueSeed, content));
//
//        Recipes.registerModRecipe(new Recipe("stacksizecontroller", 1, RecipeTechRegistry.NONE, new Ingredient[]{
//                new Ingredient("anylog", 1)
//        }));
    }

    public void initResources() {
    }

    public void postInit() {
        CommandsManager.registerServerCommand(new ChangeStackSizeCommand());
        System.out.println("Loaded Increased Stack Size mod... Enjoy!");
    }

}
