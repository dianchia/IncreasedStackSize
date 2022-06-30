package increasedStackSize.patches;

import increasedStackSize.IncreasedStackSize;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.level.gameObject.GameObject;
import net.bytebuddy.asm.Advice;

import java.awt.*;

@ModConstructorPatch(target = GameObject.class, arguments = {Rectangle.class})
public class GameObjectConstructorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This GameObject thisGameObject, @Advice.FieldValue(value = "stackSize", readOnly = false)int stackSize) {
        if (IncreasedStackSize.config.isBlacklisted(thisGameObject)){
            System.out.println("Skipping " + thisGameObject + " as it is a " + thisGameObject.getClass());
        } else {
            stackSize = stackSize * IncreasedStackSize.config.getMultiplier();
        }
    }
}
