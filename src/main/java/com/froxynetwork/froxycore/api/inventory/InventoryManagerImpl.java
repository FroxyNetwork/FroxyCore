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
 * MIT License
 *
 * Copyright (c) 2019 FroxyNetwork
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author 0ddlyoko
 */
public class InventoryManagerImpl implements InventoryManager, Listener {
	private HashMap<UUID, InventoryImpl> inventories;

	public InventoryManagerImpl() {
		this.inventories = new HashMap<>();
		Bukkit.getPluginManager().registerEvents(this, Froxy.getCorePlugin());
	}

	@Override
	public Inventory openInventory(InventoryProvider provider, Player p) {
		InventoryImpl inv = new InventoryImpl(p, provider);
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
		if (!inventories.containsKey(e.getWhoClicked().getUniqueId()))
			return;
		Player p = (Player) e.getWhoClicked();
		InventoryImpl inv = getInventory(p);
		if (inv == null) {
			// Impossible
			return;
		}
		org.bukkit.inventory.Inventory clickedInventory = e.getClickedInventory();
		if (clickedInventory == null)
			return;
		if (inv.getBukkitInventory() != clickedInventory) {
			// The player doesn't click on the correct inventory
			e.setCancelled(true);
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
