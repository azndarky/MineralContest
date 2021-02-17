package fr.synchroneyes.mineral.Events;

import fr.synchroneyes.custom_events.MCPlayerLeavePluginEvent;
import fr.synchroneyes.mineral.Core.Game.Game;
import fr.synchroneyes.mineral.Core.House;
import fr.synchroneyes.mineral.Teams.Equipe;
import fr.synchroneyes.mineral.Utils.ErrorReporting.Error;
import fr.synchroneyes.mineral.Utils.Log.GameLogger;
import fr.synchroneyes.mineral.Utils.Log.Log;
import fr.synchroneyes.mineral.mineralcontest;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) throws Exception {
        World worldEvent = event.getPlayer().getWorld();
        // Quand un joueur se déconnecte, on appelle l'event PlayerLeavePlugin
        Player joueur = event.getPlayer();

        // ON vérifie que c'est un joueur du plugin
        if(mineralcontest.plugin.getMCPlayer(joueur) != null) {
            // On appelle l'event
            MCPlayerLeavePluginEvent event1 = new MCPlayerLeavePluginEvent(event.getPlayer(), mineralcontest.plugin.getMCPlayer(event.getPlayer()));
            Bukkit.getPluginManager().callEvent(event1);
            return;
        }
        /*if (mineralcontest.isAMineralContestWorld(worldEvent)) {
            Player joueur = event.getPlayer();
            Game partie = mineralcontest.getWorldGame(worldEvent);


            GameLogger.addLog(new Log("PlayerDisconnect", "Player " + joueur.getDisplayName() + " disconnected", "player_disconnect"));

            if (partie == null && mineralcontest.communityVersion) return;

            if (!mineralcontest.communityVersion) {

                if (partie == null) return;
                partie.groupe.addDisconnectedPlayer(joueur);

                mineralcontest.plugin.getNonCommunityGroup().retirerJoueur(joueur);
                return;
            }

            partie.groupe.addDisconnectedPlayer(joueur);
            partie.groupe.retirerJoueur(joueur);




            Equipe team = partie.getPlayerTeam(joueur);
            House house = partie.getPlayerHouse(joueur);


            if (partie.isPlayerReady(joueur)) partie.removePlayerReady(joueur);

            if (joueur.isOp())
                if (partie.isReferee(joueur)) partie.removeReferee(joueur, false);


            if ((partie.isGameStarted() || partie.isPreGame())) {
                //partie.pauseGame();

                if (house != null) house.getPorte().forceCloseDoor();
            }


            if (team != null)
                team.removePlayer(joueur);

            // On supprime le joueur du plugin
            // On ajoute le joueur au plugin
            mineralcontest.plugin.removePlayer(joueur);



            Bukkit.getScheduler().runTaskLater(mineralcontest.plugin, () -> {
                int number_of_player_online = partie.groupe.getPlayers().size();
                if (number_of_player_online == 0) {
                    partie.resetMap();
                    if (partie.isGameStarted()) {
                        try {
                            partie.terminerPartie();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Error.Report(e, partie);
                        }
                    }
                }
            }, 20 * 5);

            event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4d);


        }*/

    }
}
