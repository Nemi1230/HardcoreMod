package jp.nemi.hardcore.mixin;

import jp.nemi.hardcore.object.entities.HCVillagerTrades;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin extends AbstractVillager {
        public WanderingTraderMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
                super(p_35267_, p_35268_);
        }

        /***
         * @author Nemi 1230
         * @reason To change transaction details.
         */
        @Overwrite
        protected void updateTrades() {
                VillagerTrades.ItemListing[] avillagertrades$itemlisting = HCVillagerTrades.HC_WANDERING_TRADER_TRADES.get(1);
                VillagerTrades.ItemListing[] avillagertrades$itemlisting1 = HCVillagerTrades.HC_WANDERING_TRADER_TRADES.get(2);

                if (avillagertrades$itemlisting != null && avillagertrades$itemlisting1 != null) {
                        MerchantOffers merchantoffers = this.getOffers();
                        this.addOffersFromItemListings(merchantoffers, avillagertrades$itemlisting, 5);
                        int i = this.random.nextInt(avillagertrades$itemlisting1.length);
                        VillagerTrades.ItemListing villagertrades$itemlisting = avillagertrades$itemlisting1[i];
                        MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);

                        if (merchantoffer != null) {
                                merchantoffers .add(merchantoffer);
                        }
                }
        }
}
