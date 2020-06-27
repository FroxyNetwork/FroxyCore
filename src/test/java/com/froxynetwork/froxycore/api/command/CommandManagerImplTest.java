package com.froxynetwork.froxycore.api.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.froxynetwork.froxyapi.command.Command;

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
public class CommandManagerImplTest {

	@Test
	public void testRegisterCommand() {
		CommandManagerImpl com = new CommandManagerImpl();
		Command c = mock(Command.class);
		com.registerCommand(c);

		assertEquals(1, com.getCommands().size());
	}

	@Test
	public void testGetCommand() {
		CommandManagerImpl com = new CommandManagerImpl();
		Command c = mock(Command.class);
		when(c.getCommand()).thenReturn("command");
		when(c.getAliases()).thenReturn(new String[] { "alias1", "alias2" });
		com.registerCommand(c);

		assertEquals(c, com.getCommand("command"));

		// Test aliases
		assertEquals(c, com.getCommand("alias1"));
		assertEquals(c, com.getCommand("alias2"));
		assertNull(com.getCommand("alias3"));
	}

	@Test
	public void testOnPlayerChat() {
		CommandManagerImpl com = new CommandManagerImpl();
		Command c = mock(Command.class);
		com.registerCommand(c);

		PlayerCommandPreprocessEvent e = mock(PlayerCommandPreprocessEvent.class);
		Player p = mock(Player.class);

		when(e.getPlayer()).thenReturn(p);

		com.onPlayerCommand(e);

		// Should NOT be cancelled because it's not a command but a message
		verify(e, never()).setCancelled(true);
	}

	@Test
	public void testOnPlayerCommandNotRegistered() {
		CommandManagerImpl com = new CommandManagerImpl();
		Command c = mock(Command.class);
		when(c.getCommand()).thenReturn("command");
		when(c.getAliases()).thenReturn(new String[] { "alias1", "alias2" });
		com.registerCommand(c);

		PlayerCommandPreprocessEvent e = mock(PlayerCommandPreprocessEvent.class);
		Player p = mock(Player.class);

		when(e.getPlayer()).thenReturn(p);
		when(e.getMessage()).thenReturn("/unregisteredCommand a b c");

		com.onPlayerCommand(e);

		// Should be cancelled because it's a command
		verify(e, never()).setCancelled(true);
	}

	@Test
	public void testOnPlayerCommand() {
		CommandManagerImpl com = new CommandManagerImpl();

		PlayerCommandPreprocessEvent e = mock(PlayerCommandPreprocessEvent.class);
		Player p = mock(Player.class);

		when(e.getPlayer()).thenReturn(p);
		when(e.getMessage()).thenReturn("/command a b c");

		Command c = mock(Command.class);
		when(c.getCommand()).thenReturn("command");
		when(c.execute("command", p, new String[] { "a", "b", "c" })).thenReturn(true);
		// Register command
		com.registerCommand(c);

		// Execute command
		com.onPlayerCommand(e);

		// Should be cancelled because it's a command and the player has permission to
		// execute this command
		verify(e).setCancelled(true);
		verify(c).execute("command", p, new String[] { "a", "b", "c" });
	}

	@Test
	public void testOnPlayerCommandNoPermission() {
		CommandManagerImpl com = new CommandManagerImpl();

		PlayerCommandPreprocessEvent e = mock(PlayerCommandPreprocessEvent.class);
		Player p = mock(Player.class);

		when(e.getPlayer()).thenReturn(p);
		when(e.getMessage()).thenReturn("/command a b c");

		Command c = mock(Command.class);
		when(c.getCommand()).thenReturn("command");
		when(c.getPermission()).thenReturn("permission.command");
		// Register command
		com.registerCommand(c);

		// Execute command
		com.onPlayerCommand(e);

		// Should be cancelled because it's a command and the player has permission to
		// execute this command
		verify(e).setCancelled(true);
		verify(c, never()).execute("command", p, new String[] { "a", "b", "c" });
	}

	@Test
	public void testOnPlayerCommandHasPermission() {
		CommandManagerImpl com = new CommandManagerImpl();

		PlayerCommandPreprocessEvent e = mock(PlayerCommandPreprocessEvent.class);
		Player p = mock(Player.class);
		when(p.hasPermission("permission.command")).thenReturn(true);

		when(e.getPlayer()).thenReturn(p);
		when(e.getMessage()).thenReturn("/command a b c");

		Command c = mock(Command.class);
		when(c.getCommand()).thenReturn("command");
		when(c.getPermission()).thenReturn("permission.command");
		when(c.execute("command", p, new String[] { "a", "b", "c" })).thenReturn(true);
		// Register command
		com.registerCommand(c);

		// Execute command
		com.onPlayerCommand(e);

		// Should be cancelled because it's a command and the player has permission to
		// execute this command
		verify(e).setCancelled(true);
		verify(c).execute("command", p, new String[] { "a", "b", "c" });
	}
}
