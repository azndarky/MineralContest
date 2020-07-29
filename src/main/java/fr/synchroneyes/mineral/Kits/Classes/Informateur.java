package fr.synchroneyes.mineral.Kits.Classes;

import fr.synchroneyes.custom_events.MCAirDropSpawnEvent;
import fr.synchroneyes.custom_events.MCAirDropTickEvent;
import fr.synchroneyes.custom_events.MCArenaChestTickEvent;
import fr.synchroneyes.groups.Core.Groupe;
import fr.synchroneyes.mineral.Kits.KitAbstract;
import fr.synchroneyes.mineral.mineralcontest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

/**
 * L'informateur recoit des informations sur les prochains coffre & largage
 * Il ne peut pas les ouvrir
 */
public class Informateur extends KitAbstract {

    private int timeLeftBeforeArenaWarn = 30;
    private int timeLeftBeforeDropWarn = 60;


    @Override
    public String getNom() {
        return "Informateur";
    }

    @Override
    public String getDescription() {
        return "Il recoit des informations cruciale sur les largages et les coffres ... Il ne peut pas les ouvrir";
    }

    @Override
    public Material getRepresentationMaterialForSelectionMenu() {
        return Material.BOOK;
    }

    /**
     * Fonction appelée à chaque TICK
     *
     * @param event
     */
    @EventHandler
    public void onArenaChestTick(MCArenaChestTickEvent event) {
        // On récupère les joueurs de la partie
        for (Player joueur : event.getGame().groupe.getPlayers())
            // On vérifie si ils ont le kit
            if (isPlayerUsingThisKit(joueur))
                // On vérfie le temps restant
                if (event.getTimeLeft() == timeLeftBeforeArenaWarn)
                    joueur.sendMessage("Le coffre va apparaitre dans 30sec");
    }

    @EventHandler
    public void onAirDropTick(MCAirDropTickEvent event) {
        // On récupère les joueurs de la partie
        for (Player joueur : event.getGame().groupe.getPlayers())
            // On vérifie si ils ont le kit
            if (isPlayerUsingThisKit(joueur))
                // On vérfie le temps restant
                if (event.getTimeLeft() == timeLeftBeforeDropWarn)
                    joueur.sendMessage("Le drop va apparaitre dans 30sec");
    }

    @EventHandler
    public void onAirdropSpawn(MCAirDropSpawnEvent event) {
        // On récupère les joueurs de la partie
        for (Player joueur : event.getGame().groupe.getPlayers())
            // On vérifie si ils ont le kit
            if (isPlayerUsingThisKit(joueur))
                joueur.sendMessage(event.getParachuteLocation().toVector().toString());
    }


    /**
     * On va bloquer l'ouverture du coffre d'arène/airdrop
     *
     * @param event
     */
    @EventHandler
    public void onChestOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player joueur = (Player) event.getPlayer();
        if (!isPlayerUsingThisKit(joueur)) return;

        Groupe playerGroup = mineralcontest.getPlayerGroupe(joueur);
        if (playerGroup == null) return;

        Inventory inventaire = event.getInventory();


        // On regarde si l'inventaire est celui d'un coffre automatique
        if (playerGroup.getAutomatedChestManager().isThisAnAnimatedInventory(inventaire)) {
            joueur.closeInventory();
            playerGroup.getAutomatedChestManager().getFromInventory(inventaire).closeInventory();
            event.setCancelled(true);
        }
    }
}
