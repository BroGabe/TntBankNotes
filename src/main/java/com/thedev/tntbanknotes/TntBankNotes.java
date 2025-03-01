package com.thedev.tntbanknotes;

import co.aikar.commands.PaperCommandManager;
import com.thedev.tntbanknotes.commands.NoteCMDS;
import com.thedev.tntbanknotes.listeners.RedeemListener;
import com.thedev.tntbanknotes.utils.MessageManager;
import com.thedev.tntbanknotes.utils.NoteManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TntBankNotes extends JavaPlugin {

    private static TntBankNotes instance;

    private NoteManager noteManager;

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        noteManager = new NoteManager(this);
        messageManager = new MessageManager(this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");
        manager.registerCommand(new NoteCMDS(this));

        Bukkit.getPluginManager().registerEvents(new RedeemListener(this), this);

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public TntBankNotes getInst() {
        return instance;
    }

    public NoteManager getNoteManager() {
        return noteManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
