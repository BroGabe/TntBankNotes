package com.thedev.tntbanknotes.utils;

import com.thedev.tntbanknotes.TntBankNotes;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MessageManager {

    private final FileConfiguration config;

    public MessageManager(TntBankNotes plugin) {
        config = plugin.getConfig();
    }

    public void sendNoFacMsg(Player player) {
        player.sendMessage(ColorUtil.color(config.getString("messages.no-faction")));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 6, 6);
    }

    public void sendExceedMsg(Player player) {
        player.sendMessage(ColorUtil.color(config.getString("messages.cannot-exceed-limit")));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 6, 6);
    }

    public void redeemedMsg(Player player, int amount) {
        player.sendMessage(ColorUtil.color(config.getString("messages.redeemed-note").replace("%amount%", String.valueOf(amount))));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 7, 6);
    }
}
