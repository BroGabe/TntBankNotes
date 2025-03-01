package com.thedev.tntbanknotes.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.thedev.tntbanknotes.TntBankNotes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RedeemListener implements Listener {

    private final TntBankNotes plugin;

    public RedeemListener(TntBankNotes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRedeem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) return;

        ItemStack handItem = player.getItemInHand();

        NBTItem nbtItem = new NBTItem(handItem);

        if(nbtItem.getCompound("TntBanknotes") == null) return;
        if(!nbtItem.getCompound("TntBanknotes").hasTag("StoredTnt")) return;

        int amount = nbtItem.getCompound("TntBanknotes").getInteger("StoredTnt");

        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        if(!fPlayer.hasFaction()) {
            plugin.getMessageManager().sendNoFacMsg(player);
            return;
        }

        Faction faction = fPlayer.getFaction();

        if(faction.getTNTBank() >= faction.getMaxTNT()) {
            plugin.getMessageManager().sendExceedMsg(player);
            return;
        }

        event.setCancelled(true);

        int finalAmount = Math.min((faction.getTNTBank() + amount), faction.getMaxTNT());

        faction.setTNTBank(finalAmount);

        plugin.getMessageManager().redeemedMsg(player, amount);

        if(handItem.getAmount() <= 1) {
            player.setItemInHand(null);
            return;
        }

        player.getItemInHand().setAmount(handItem.getAmount() - 1);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) return;

        ItemStack handItem = player.getItemInHand();

        NBTItem nbtItem = new NBTItem(handItem);

        if(nbtItem.getCompound("TntBanknotes") == null) return;
        if(!nbtItem.getCompound("TntBanknotes").hasTag("StoredTnt")) return;

        event.setCancelled(true);
    }
}
