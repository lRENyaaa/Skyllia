package fr.euphyllia.skyllia.commands.common.subcommands;

import fr.euphyllia.skyllia.Main;
import fr.euphyllia.skyllia.api.entity.PlayerFolia;
import fr.euphyllia.skyllia.api.skyblock.Island;
import fr.euphyllia.skyllia.api.skyblock.model.WarpIsland;
import fr.euphyllia.skyllia.api.utils.helper.RegionHelper;
import fr.euphyllia.skyllia.commands.SubCommandInterface;
import fr.euphyllia.skyllia.configuration.ConfigToml;
import fr.euphyllia.skyllia.configuration.LanguageToml;
import fr.euphyllia.skyllia.managers.skyblock.SkyblockManager;
import fr.euphyllia.skyllia.utils.WorldUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeSubCommand implements SubCommandInterface {

    private final Logger logger = LogManager.getLogger(HomeSubCommand.class);


    @Override
    public boolean onCommand(@NotNull Main plugin, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (!player.hasPermission("skyllia.island.command.home")) {
            LanguageToml.sendMessage(plugin, player, LanguageToml.messagePlayerPermissionDenied);
            return true;
        }

        try {
            SkyblockManager skyblockManager = plugin.getInterneAPI().getSkyblockManager();
            Island island = skyblockManager.getIslandByPlayerId(player.getUniqueId()).join();
            if (island == null) {
                LanguageToml.sendMessage(plugin, player, LanguageToml.messagePlayerHasNotIsland);
                return true;
            }
            if (ConfigToml.changeGameModeWhenTeleportIsland) PlayerFolia.setGameMode(player, GameMode.SPECTATOR);

            WarpIsland warpIsland = island.getWarpByName("home");
            double rayon = island.getSize();

            player.getScheduler().execute(plugin, () -> {
                Location loc;
                if (warpIsland == null) {
                    loc = RegionHelper.getCenterRegion(Bukkit.getWorld(WorldUtils.getWorldConfigs().getFirst().name()), island.getPosition().x(), island.getPosition().z());
                } else {
                    loc = warpIsland.location();
                }
                player.teleportAsync(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                if (ConfigToml.changeGameModeWhenTeleportIsland) PlayerFolia.setGameMode(player, GameMode.SURVIVAL);
                plugin.getInterneAPI().getPlayerNMS().setOwnWorldBorder(plugin, player, RegionHelper.getCenterRegion(loc.getWorld(), island.getPosition().x(), island.getPosition().z()), rayon, 0, 0);
                LanguageToml.sendMessage(plugin, player, LanguageToml.messageHomeIslandSuccess);
            }, null, 0L);
        } catch (Exception exception) {
            logger.log(Level.FATAL, exception.getMessage(), exception);
            LanguageToml.sendMessage(plugin, player, LanguageToml.messageError);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull Main plugin, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }

}
