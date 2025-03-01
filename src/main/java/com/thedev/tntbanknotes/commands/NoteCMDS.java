package com.thedev.tntbanknotes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.thedev.tntbanknotes.TntBankNotes;
import com.thedev.tntbanknotes.utils.ColorUtil;
import org.bukkit.command.CommandSender;

@CommandAlias("tntnotes|tntnote")
public class NoteCMDS extends BaseCommand {

    private final TntBankNotes plugin;

    public NoteCMDS(TntBankNotes plugin) {
        this.plugin = plugin;
    }

    @Default
    @Subcommand("give")
    @Syntax("<player> <amount>")
    @CommandPermission("tntbanknotes.admin")
    @Description("gives a tnt note")
    @CommandCompletion("@players @range:1-30")
    public void onGive(CommandSender sender, OnlinePlayer player, int amount) {
        sender.sendMessage(ColorUtil.color("&4&lTNT &cPlayer has been given a note!"));

        player.getPlayer().getInventory().addItem(plugin.getNoteManager().getNoteItem(amount));
    }
}
