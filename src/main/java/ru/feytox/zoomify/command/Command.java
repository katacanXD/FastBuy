package ru.feytox.zoomify.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import ru.feytox.zoomify.FastBuy;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Command {
    public static int boostType;
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("fastbuy")
                .then(ClientCommandManager.argument("volume", IntegerArgumentType.integer(0))
                        .then(ClientCommandManager.argument("boostType", IntegerArgumentType.integer(0))
                                .executes(c -> {
                                    FastBuy.buy(c.getArgument("volume", int.class));
                                    boostType = c.getArgument("boostType", int.class);
                                    FastBuy.windowOneInt = 1;
                                    return 1;
                                }))
                ))

        );
    }
}