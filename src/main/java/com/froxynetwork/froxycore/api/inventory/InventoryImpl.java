package com.froxynetwork.froxycore.api.inventory;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.froxynetwork.froxyapi.inventory.ClickableItem;
import com.froxynetwork.froxyapi.inventory.Inventory;
import com.froxynetwork.froxyapi.inventory.InventoryProvider;

import lombok.Getter;
import lombok.Setter;

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
public class InventoryImpl implements Inventory {

	private HashMap<String, Object> values;
	private Player player;
	private InventoryProvider inventoryProvider;
	@Getter
	private int size;
	private ClickableItem[] items;
	@Getter
	@Setter
	private org.bukkit.inventory.Inventory bukkitInventory;

	public InventoryImpl(Player player, InventoryProvider inventoryProvider) {
		this.values = new HashMap<>();
		this.player = player;
		this.inventoryProvider = inventoryProvider;
		this.size = inventoryProvider.rows(this);
		this.items = new ClickableItem[9 * size];
		save(TICK, 0);
	}

	public void initBukkitInventory() {
		this.bukkitInventory = Bukkit.createInventory(player, size * 9, inventoryProvider.title(this));
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public InventoryProvider getInventoryProvider() {
		return inventoryProvider;
	}

	@Override
	public int getRows() {
		return size;
	}

	public ClickableItem get(int pos) {
		return items[pos];
	}

	@Override
	public void set(int pos, ClickableItem item) {
		if (pos < 0 || pos > size * 9 - 1)
			throw new IllegalArgumentException("pos must be between 0 and " + (size * 9 - 1) + ", but is " + pos);
		items[pos] = item;
		bukkitInventory.setItem(pos, item.getItem());
	}

	@Override
	public void fill(ClickableItem item) {
		for (int row = 0; row < size; row++)
			for (int col = 0; col < 9; col++)
				set(row * 9 + col, item);
	}

	@Override
	public void rectangle(int col, int row, int width, int height, ClickableItem item) {
		if (col < 1 || col > 9)
			throw new IllegalArgumentException("col must be between 1 and 9, but is " + col);
		if (row < 1 || row > size)
			throw new IllegalArgumentException("row must be between 1 and " + size + ", but is " + row);
		// 10 - col because width starts at 1 and not 0
		if (width < 1 || width > 10 - col)
			throw new IllegalArgumentException("The width must be between 1 and " + (10 - col) + ", but is " + width);
		if (height < 1 || height > size + 1 - row)
			throw new IllegalArgumentException(
					"The height must be between 1 and " + (size + 1 - row) + ", but is " + height);
		for (int i = col; i < col + width; i++)
			for (int j = row; j < row + height; j++)
				// Around
				if (i == col || i == col + width - 1 || j == row || j == row + height - 1)
					set(i, j, item);
	}

	@Override
	public void rectangle(int pos, int width, int height, ClickableItem item) {
		if (pos < 0 || pos > size * 9)
			throw new IllegalArgumentException("pos must be between 0 and " + (size * 9) + ", but is " + pos);
		int[] rowCol = posToLoc(pos);
		rectangle(rowCol[0], rowCol[1], width, height, item);
	}

	@Override
	public void fillRectangle(int col, int row, int width, int height, ClickableItem item) {
		if (col < 1 || col > 9)
			throw new IllegalArgumentException("col must be between 1 and 9, but is " + col);
		if (row < 1 || row > size)
			throw new IllegalArgumentException("row must be between 1 and " + size + ", but is " + row);
		// 10 - col because width starts at 1 and not 0
		if (width < 1 || width > 10 - col)
			throw new IllegalArgumentException("The width must be between 1 and " + (10 - col) + ", but is " + width);
		if (height < 1 || height > size + 1 - row)
			throw new IllegalArgumentException(
					"The height must be between 1 and " + (size + 1 - row) + ", but is " + height);
		for (int i = col; i < col + width; i++)
			for (int j = row; j < row + height; j++)
				set(i, j, item);
	}

	@Override
	public void fillRectangle(int pos, int width, int height, ClickableItem item) {
		if (pos < 0 || pos > size * 9)
			throw new IllegalArgumentException("pos must be between 0 and " + (size * 9) + ", but is " + pos);
		int[] rowCol = posToLoc(pos);
		fillRectangle(rowCol[0], rowCol[1], width, height, item);
	}

	public void open() {
		player.openInventory(bukkitInventory);
	}

	public void handler(InventoryClickEvent e) {
		int pos = e.getSlot();
		if (pos < 0 || pos > items.length)
			return;
		ClickableItem item = items[pos];
		if (item == null) {
			// Nothing to do
			return;
		}
		item.run(e);
	}

	@Override
	public void save(String key, Object value) {
		values.put(key, value);
	}

	@Override
	public Object get(String key) {
		return values.get(key);
	}
}
