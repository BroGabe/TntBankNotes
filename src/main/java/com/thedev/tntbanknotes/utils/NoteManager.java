package com.thedev.tntbanknotes.utils;

import com.thedev.tntbanknotes.TntBankNotes;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NoteManager {

    private final FileConfiguration config;

    private final Material material;

    private final String name;

    private final int data;

    private final boolean glow;

    private final String texture;

    private final List<String> lore = new ArrayList<>();

    public NoteManager(TntBankNotes plugin) {
        config = plugin.getConfig();

        name = config.getString("tnt-banknote.name");
        material = Material.valueOf(config.getString("tnt-banknote.material"));
        data = config.getInt("tnt-banknote.data");
        glow = config.getBoolean("tnt-banknote.glow");
        texture = config.getString("tnt-banknote.texture");

        config.getStringList("tnt-banknote.lore").forEach(s -> lore.add(ColorUtil.color(s)));
    }

    public ItemStack getNoteItem(int amount) {
        ItemBuilder itemBuilder = new ItemBuilder(material, name, 1, data, texture, lore);

        itemBuilder.updateName(getFunction(amount));
        itemBuilder.updateLore(getFunction(amount));

        if(glow) {
            itemBuilder.setGlow();
        }

        ItemStack itemStack = itemBuilder.getItem();

        NBTItem nbtItem = new NBTItem(itemStack);

        NBTCompound compound = nbtItem.getOrCreateCompound("TntBanknotes");

        compound.setInteger("StoredTnt", amount);

        return nbtItem.getItem();
    }

    private Function<String, String> getFunction(int amount) {

        return (s1 -> {
            return s1.replace("%amount%", String.valueOf(amount));
        });
    }
}
