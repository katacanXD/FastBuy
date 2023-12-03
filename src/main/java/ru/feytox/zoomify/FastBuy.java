package ru.feytox.zoomify;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;


public class FastBuy {
    public static int volume = -1;
    public static int windowOneInt = -1;
    public static int boostType = -1;
    public static Text windowOne;
    public static Text windowTwo;

    public static void buy(int volume) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        MinecraftClient mc = MinecraftClient.getInstance();

        assert mc.player != null;
        player.networkHandler.sendChatCommand("donate");
        FastBuy.volume = volume;
    }
}
