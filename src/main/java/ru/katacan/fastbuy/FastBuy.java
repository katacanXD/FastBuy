package ru.katacan.fastbuy;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import ru.katacan.fastbuy.command.Command;


public class FastBuy {
    public static int volume = -1;
    public static int windowOneInt = -1;
    public static int boostType = -1;
    public static Text windowTwo;
    public static Text windowAllBoosters;
    public static Text windowDonate;
    public static Text windowBuy;
    public static int boostCount;
    public static int done = -1;


    public static void buy(int volume) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        MinecraftClient mc = MinecraftClient.getInstance();

        FastBuy.volume = volume;
        assert mc.player != null;
        if (FastBuy.volume > 0 && Command.boostType > 0 && Command.boostType < 4) {
            player.networkHandler.sendChatCommand("donate");
            FastBuy.windowOneInt = 1;
        }
        else if (volume == 0) {
            FastBuy.volume = -1;
            FastBuy.windowOneInt =  -1;
            Command.boostType = -1;
            player.sendMessage(Text.of("Значение 'count' (кол-во бустов) не может быть меньше 1"));
        } else if (Command.boostType == 0 || Command.boostType > 3) {
            FastBuy.volume = -1;
            FastBuy.windowOneInt =  -1;
            Command.boostType = -1;
            player.sendMessage(Text.of("""
                    Значение 'boostType' (тип буста) не может иметь значения кроме:\s
                    1 - Бустер монет
                    2 - Бустер шардов
                    3 - Бустер опыта ремесел"""));
        }
    }
}
