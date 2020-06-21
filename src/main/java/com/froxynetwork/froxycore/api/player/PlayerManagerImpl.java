package com.froxynetwork.froxycore.api.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froxynetwork.froxyapi.events.PlayerLoadEvent;
import com.froxynetwork.froxyapi.events.PlayerUnloadEvent;
import com.froxynetwork.froxyapi.player.PlayerManager;
import com.froxynetwork.froxycore.Froxy;
import com.froxynetwork.froxynetwork.network.output.Callback;
import com.froxynetwork.froxynetwork.network.output.RestException;
import com.froxynetwork.froxynetwork.network.output.data.PlayerDataOutput;

/**
 * FroxyCore
 * 
 * Copyright (C) 2019 FroxyNetwork
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author 0ddlyoko
 */
public class PlayerManagerImpl implements PlayerManager, Listener {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private HashMap<UUID, PlayerImpl> players;
	private int killTime;
	private int assistTime;

	public PlayerManagerImpl() {
		players = new HashMap<>();
		this.killTime = 7;
		this.assistTime = 7;
	}

	@Override
	public PlayerImpl getPlayer(String name) {
		// Loop over players
		for (PlayerImpl p : players.values())
			if (p.getRealName().equalsIgnoreCase(name))
				return p;
		return null;
	}

	@Override
	public PlayerImpl getPlayer(UUID uuid) {
		return players.get(uuid);
	}

	@Override
	public List<PlayerImpl> getPlayers() {
		return new ArrayList<>(players.values());
	}

	@Override
	public void setKillTime(int time) {
		this.killTime = time;
	}

	@Override
	public int getKillTime() {
		return killTime;
	}

	@Override
	public void setAssistTime(int time) {
		this.assistTime = time;
	}

	@Override
	public int getAssistTime() {
		return assistTime;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player bukkitPlayer = e.getPlayer();
		// TODO Remove this message
		bukkitPlayer.sendMessage("Loading player, please wait ...");
		// Get informations about the player
		Froxy.getNetworkManager().getNetwork().getPlayerService().asyncGetPlayer(bukkitPlayer.getUniqueId().toString(),
				new Callback<PlayerDataOutput.Player>() {

					@Override
					public void onResponse(PlayerDataOutput.Player pp) {
						// All is ok
						LOG.info("Player {} ({}) has been retrieved, loading him ...", pp.getUuid(), pp.getNickname());
						// Test if player is still connected
						// If player is not connected, do not call the event
						if (!bukkitPlayer.isOnline()) {
							// Nothing to do here
							return;
						}
						PlayerImpl p = new PlayerImpl(bukkitPlayer, pp);
						players.put(p.getUUID(), p);
						// Call event
						Bukkit.getScheduler().runTask(Froxy.getCorePlugin(), () -> {
							Bukkit.getPluginManager().callEvent(new PlayerLoadEvent(p));
						});
					}

					@Override
					public void onFailure(RestException ex) {
						// TODO Maybe ask again rest server
						LOG.error("Error while retrieving player {} ({})", bukkitPlayer.getUniqueId(),
								bukkitPlayer.getName());
						LOG.error("", ex);
						Bukkit.getScheduler().runTask(Froxy.getCorePlugin(), () -> {
							bukkitPlayer.kickPlayer("An error has occured, please contact an administrator");
						});
					}

					@Override
					public void onFatalFailure(Throwable t) {
						// TODO Maybe ask again rest server
						LOG.error("Fatal Error while retrieving player {} ({})", bukkitPlayer.getUniqueId(),
								bukkitPlayer.getName());
						LOG.error("", t);
						Bukkit.getScheduler().runTask(Froxy.getCorePlugin(), () -> {
							bukkitPlayer.kickPlayer("An error has occured, please contact an administrator");
						});
					}
				});
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		PlayerImpl playerImpl = getPlayer(p.getUniqueId());
		if (playerImpl == null)
			// Nothing to do, this player hasn't been loaded
			return;
		// Call event
		Bukkit.getPluginManager().callEvent(new PlayerUnloadEvent(playerImpl));
		// Remove player from the list
		players.remove(p.getUniqueId());
	}
}
