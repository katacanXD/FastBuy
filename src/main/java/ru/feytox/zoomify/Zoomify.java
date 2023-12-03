package ru.feytox.zoomify;


import net.fabricmc.api.ModInitializer;
import ru.feytox.zoomify.command.Command;

public class Zoomify implements ModInitializer {
    public static final String MOD_ID = "zoomify";

    @Override
    public void onInitialize() {
        Command.init();
    }
}
