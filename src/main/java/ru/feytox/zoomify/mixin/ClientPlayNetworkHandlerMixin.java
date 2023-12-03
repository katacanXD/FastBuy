package ru.feytox.zoomify.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.feytox.zoomify.FastBuy;
import ru.feytox.zoomify.command.Command;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow
    public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onOpenScreen", at = @At("HEAD"), cancellable = true)
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo ci) {
        if (FastBuy.windowOneInt != -1) {
            ci.cancel();
            FastBuy.windowOne = packet.getName();
            sendPacket(new ClickSlotC2SPacket(packet.getSyncId(), 0, 8, 0, SlotActionType.PICKUP, ItemStack.EMPTY,
                    Int2ObjectMaps.emptyMap()));
            FastBuy.windowOneInt = -1;
            FastBuy.boostType = Command.boostType;

        } else if (!(packet.getName().equals(FastBuy.windowOne)) && FastBuy.boostType != -1) {
            FastBuy.windowOne = null;
            ci.cancel();
            FastBuy.windowTwo = packet.getName();
            sendPacket(new ClickSlotC2SPacket(packet.getSyncId(), 0, FastBuy.boostType-1, 0, SlotActionType.PICKUP, ItemStack.EMPTY,
                    Int2ObjectMaps.emptyMap()));
            FastBuy.boostType = -1;

        } else if (!(packet.getName().equals(FastBuy.windowTwo)) && FastBuy.volume != -1) {
            FastBuy.windowTwo = null;
            FastBuy.windowOne = packet.getName();
            for (int i = 0; i < (FastBuy.volume-1); i++) {
                sendPacket(new ClickSlotC2SPacket(packet.getSyncId(), 0, 4, 0, SlotActionType.PICKUP, ItemStack.EMPTY,
                        Int2ObjectMaps.emptyMap()));
            }
            FastBuy.volume = -1;

        }
        else if ((packet.getName().equals(FastBuy.windowOne))) {
            ci.cancel();
            FastBuy.windowOne = null;
            sendPacket(new ClickSlotC2SPacket(packet.getSyncId(), 0, 1, 0, SlotActionType.PICKUP, ItemStack.EMPTY,
                    Int2ObjectMaps.emptyMap()));
        }
    }
}
