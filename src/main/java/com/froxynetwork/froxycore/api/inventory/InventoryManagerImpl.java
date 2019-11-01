package com.froxynetwork.froxycore.api.inventory;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.froxynetwork.froxyapi.Froxy;
import com.froxynetwork.froxyapi.inventory.Inventory;
import com.froxynetwork.froxyapi.inventory.InventoryManager;
import com.froxynetwork.froxyapi.inventory.InventoryProvider;

/**
 * FroxyCore
 * Copyright (C) 2019 FroxyNetwork
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author 0ddlyoko
 */
public class InventoryManagerImpl implements InventoryManager, Listener {
	private HashMap<UUID, InventoryImpl> inventories;

	public InventoryManagerImpl() {
		this.inventories = new HashMap<>();
	}

	public void init() {
		Bukkit.getPluginManager().registerEvents(this, Froxy.getCorePlugin());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Froxy.getCorePlugin(), () -> {
			if (inventories.size() == 0)
				return;
			for (Inventory inv : inventories.values()) {
				int tick = 0;
				Object currentTick = inv.get(Inventory.TICK);
				if (currentTick != null && currentTick instanceof Integer)
					tick = Integer.parseInt(currentTick.toString());
				inv.save(Inventory.TICK, tick + 1);
				inv.getInventoryProvider().update(inv);
			}
		}, 1, 1);
	}

	@Override
	public Inventory openInventory(InventoryProvider provider, Player p) {
		InventoryImpl inv = new InventoryImpl(p, provider);
		inv.getInventoryProvider().init(inv);
		inventories.put(p.getUniqueId(), inv);
		inv.open();
		return inv;
	}

	@Override
	public InventoryImpl getInventory(Player p) {
		return inventories.get(p.getUniqueId());
	}

	@Override
	public boolean hasInventoryOpened(Player p) {
		return inventories.containsKey(p.getUniqueId());
	}

	@EventHandler
	public void onPlayerInventoryClick(InventoryClickEvent e) {
		org.bukkit.inventory.Inventory clickedInventory = e.getClickedInventory();
		if (clickedInventory == null)
			return;
		if (!inventories.containsKey(e.getWhoClicked().getUniqueId()))
			return;
		Player p = (Player) e.getWhoClicked();
		InventoryImpl inv = getInventory(p);
		if (inv == null) {
			// Impossible
			return;
		}
		e.setCancelled(true);
		if (!inv.getBukkitInventory().equals(clickedInventory)) {
			// The player doesn't click on the correct inventory
			return;
		}
		inv.handler(e);
	}

	@EventHandler
	public void onPlayerInventoryDrag(InventoryDragEvent e) {
		if (!inventories.containsKey(e.getWhoClicked().getUniqueId()))
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInventoryClose(InventoryCloseEvent e) {
		if (!inventories.containsKey(e.getPlayer().getUniqueId()))
			return;
		inventories.remove(e.getPlayer().getUniqueId());
	}
}
