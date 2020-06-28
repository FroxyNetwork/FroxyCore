package com.froxynetwork.froxycore.api.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.froxynetwork.froxyapi.inventory.ClickableItem;
import com.froxynetwork.froxyapi.inventory.InventoryProvider;

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
@RunWith(MockitoJUnitRunner.class)
public class InventoryImplTest {

	@Test
	public void setTest() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(3);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		// Set
		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.set(1, item);

		assertEquals(item, inv.get(1));
		for (int i = 0; i < 27; i++) {
			// Check if null
			if (i == 1)
				continue;
			assertNull(inv.get(i));
		}
	}

	@Test
	public void fillTest() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(3);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.fill(item);

		// Check
		for (int i = 0; i < 27; i++)
			assertEquals(item, inv.get(i));
	}

	@Test
	public void testRectangle() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.rectangle(2, 2, 7, 3, item);

		String[] repr = new String[] {
				"         ",
				" XXXXXXX ",
				" X     X ",
				" XXXXXXX ",
				"         "
		};
		for (int row = 1; row <= repr.length; row++) {
			for (int col = 1; col <= repr[row - 1].length(); col++) {
				if (repr[row - 1].charAt(col - 1) == ' ')
					assertNull("Item at col = " + col + ", row = " + row + " should be null but is not null !",
							inv.get(inv.locToPos(col, row)));
				else
					assertEquals("Item at col = " + col + ", row = " + row + " should not be null but is null !", item,
							inv.get(inv.locToPos(col, row)));
			}
		}
	}

	@Test
	public void testRectangle2() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.rectangle(2, 2, 3, 2, item);

		String[] repr = new String[] {
				"         ",
				" XXX     ",
				" XXX     ",
				"         ",
				"         "
		};
		for (int row = 1; row <= repr.length; row++) {
			for (int col = 1; col <= repr[row - 1].length(); col++) {
				if (repr[row - 1].charAt(col - 1) == ' ')
					assertNull("Item at col = " + col + ", row = " + row + " should be null but is not null !",
							inv.get(inv.locToPos(col, row)));
				else
					assertEquals("Item at col = " + col + ", row = " + row + " should not be null but is null !", item,
							inv.get(inv.locToPos(col, row)));
			}
		}
	}

	@Test
	public void testFillRectangle() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.fillRectangle(2, 2, 7, 3, item);

		String[] repr = new String[] {
				"         ",
				" XXXXXXX ",
				" XXXXXXX ",
				" XXXXXXX ",
				"         "
		};
		for (int row = 1; row <= repr.length; row++) {
			for (int col = 1; col <= repr[row - 1].length(); col++) {
				if (repr[row - 1].charAt(col - 1) == ' ')
					assertNull("Item at col = " + col + ", row = " + row + " should be null but is not null !",
							inv.get(inv.locToPos(col, row)));
				else
					assertEquals("Item at col = " + col + ", row = " + row + " should not be null but is null !", item,
							inv.get(inv.locToPos(col, row)));
			}
		}
	}

	@Test
	public void testFillRectangle2() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);

		ClickableItem item = ClickableItem.of(new ItemStack(Material.STONE));
		inv.fillRectangle(2, 2, 3, 2, item);

		String[] repr = new String[] {
				"         ",
				" XXX     ",
				" XXX     ",
				"         ",
				"         "
		};
		for (int row = 1; row <= repr.length; row++) {
			for (int col = 1; col <= repr[row - 1].length(); col++) {
				if (repr[row - 1].charAt(col - 1) == ' ')
					assertNull("Item at col = " + col + ", row = " + row + " should be null but is not null !",
							inv.get(inv.locToPos(col, row)));
				else
					assertEquals("Item at col = " + col + ", row = " + row + " should not be null but is null !", item,
							inv.get(inv.locToPos(col, row)));
			}
		}
	}
	
	@Test
	public void testHandler() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);
		Inventory bukkitInv = mock(Inventory.class);

		InventoryImpl inv = new InventoryImpl(p, ip);
		inv.setBukkitInventory(bukkitInv);
		
		ClickableItem item = mock(ClickableItem.class);
		ItemStack is = new ItemStack(Material.STONE);
		when(item.getItem()).thenReturn(is);
		
		ClickableItem item2 = mock(ClickableItem.class);
		ItemStack is2 = new ItemStack(Material.STONE);
		when(item2.getItem()).thenReturn(is2);
		
		inv.fill(item);
		inv.set(10, item2);
		
		// Test click on top left corner
		InventoryClickEvent e1 = mock(InventoryClickEvent.class);
		when(e1.getSlot()).thenReturn(0);
		
		inv.handler(e1);
		
		// Should call item
		verify(item).run(e1);
		verify(item2, never()).run(any());
		
		// Test click on item2
		InventoryClickEvent e2 = mock(InventoryClickEvent.class);
		when(e2.getSlot()).thenReturn(10);
		
		inv.handler(e2);
		
		// Should call item2
		verify(item2).run(e2);
	}
	
	@Test
	public void testSave() {
		Player p = mock(Player.class);
		InventoryProvider ip = mock(InventoryProvider.class);
		when(ip.rows(any(InventoryImpl.class))).thenReturn(5);

		InventoryImpl inv = new InventoryImpl(p, ip);
		// Save
		inv.save("TEST", inv);
		inv.save("TEST2", 2);
		inv.save("TEST3", "TARATATA");
		
		// Test
		assertEquals(inv, inv.get("TEST"));
		assertEquals(2, inv.get("TEST2"));
		assertEquals("TARATATA", inv.get("TEST3"));
	}
}
