package com.starshootercity;

import com.starshootercity.packetsenders.OriginsRebornResourcePackInfo;
import com.viaversion.viaversion.api.Via;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PackApplier implements Listener {
    private static final Map<Class<? extends OriginsAddon>, OriginsRebornResourcePackInfo> addonPacks = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (OriginsReborn.getInstance().getConfig().getBoolean("resource-pack.enabled")) {
            if (ShortcutUtils.isBedrockPlayer(event.getPlayer().getUniqueId())) return;
            Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsReborn.getInstance(), () -> sendPacks(event.getPlayer()), 120);
        }
    }

    public static void sendPacks(Player player) {
        OriginsReborn.getNMSInvoker().sendResourcePacks(player, getPackURL(player), addonPacks);
    }

    public static String getPackURL(Player player) {
        String ver = getVersion(player);
        return switch (ver) {
            case "1.20.3", "1.20.4", "1.20.3-1.20.4" -> "https://github.com/AdamMekush/ZhabnykOriginsReborn/raw/refs/heads/master/packs/1.20.3-1.20.4.zip";
            default -> "https://github.com/AdamMekush/ZhabnykOriginsReborn/raw/refs/heads/master/src/main/Origins%20Pack.zip";
        };
    }

    public static String getVersion(Player player) {
        try {
            return Via.getAPI().getPlayerProtocolVersion(player.getUniqueId()).getName();
        } catch (NoClassDefFoundError e) {
            return Bukkit.getBukkitVersion().split("-")[0];
        }
    }

/*
    @Subscribe
    public void onGeyserLoadResourcePacks(GeyserLoadResourcePacksEvent event) {
        event.resourcePacks().add(new File(OriginsReborn.getInstance().getDataFolder(), "bedrock-packs/bedrock.mcpack").toPath());
    }

    public PackApplier() {
        OriginsReborn.getInstance().saveResource("bedrock.mcpack", false);
    }

 */

    public static void addResourcePack(OriginsAddon addon, @NotNull OriginsRebornResourcePackInfo info) {
        addonPacks.put(addon.getClass(), info);
    }
}
