package com.froxynetwork.froxycore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.froxynetwork.froxyapi.Froxy;

/**
 * FroxyCore
 * 
 * Copyright (C) 2020 FroxyNetwork
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
public class PlayerEvent implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player bp = e.getPlayer();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			bp.teleport(e.getFrom());
			return;
		}
	}

	@EventHandler
	public void onPlayerTalk(AsyncPlayerChatEvent e) {
		Player bp = e.getPlayer();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player bp = e.getPlayer();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		Player bp = e.getPlayer();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player bp = e.getPlayer();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerPickupItem(EntityPickupItemEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player bp = (Player) e.getEntity();
		com.froxynetwork.froxyapi.player.Player p = Froxy.getPlayer(bp.getUniqueId());
		if (p == null) {
			e.setCancelled(true);
			return;
		}
	}
}
