package jp.nemi.hardcore.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jp.nemi.hardcore.object.entities.HCVillagerTrades;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Villager.class)
@SuppressWarnings("all")
public abstract class VillagerMixin extends AbstractVillager {
        @Shadow public abstract VillagerData getVillagerData();

        public VillagerMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
                super(p_35267_, p_35268_);
        }

        /***
         * @author Nemi 1230
         * @reason To change transaction details.
         */
        @Overwrite
        protected void updateTrades() {
                VillagerData villagerdata = this.getVillagerData();
                Int2ObjectMap<VillagerTrades.ItemListing[]> int2objectmap = HCVillagerTrades.HC_TRADES.get(villagerdata.getProfession());

                if (int2objectmap != null && !int2objectmap.isEmpty()) {
                        VillagerTrades.ItemListing[] avillagertrades$itemlisting = int2objectmap.get(villagerdata.getLevel());

                        if (avillagertrades$itemlisting != null) {
                                MerchantOffers merchantoffers = this.getOffers();
                                this.addOffersFromItemListings(merchantoffers, avillagertrades$itemlisting, 2);
                        }
                }
        }
}
