package jp.nemi.hardcore.client.event;

import jp.nemi.hardcore.init.HCItems;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvent {


    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void setupItemColors(final ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();
            colors.register((stack, color) ->
                            color > 0 ? -1 :((IDyeableArmorItem) stack.getItem()).getColor(stack),
                    HCItems.N_LEATHER_HELMET.get(), HCItems.N_LEATHER_CHESTPLATE.get(), HCItems.N_LEATHER_LEGGINGS.get(), HCItems.N_LEATHER_BOOTS.get());
        }
    }
}
