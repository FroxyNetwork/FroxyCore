package com.froxynetwork.froxycore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

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
public class FroxyCoreTest {

	@Test
	public void testReadAuthFileNotFound() {
		assertEquals(0, FroxyCore.readAuthFile(new File("src/test/resources/auth/fileNotFound")).length);
	}

	@Test
	public void testReadAuthFileEmpty() {
		String[] strs = FroxyCore.readAuthFile(new File("src/test/resources/auth/file1"));
		assertEquals(2, strs.length);
		assertNull(strs[0]);
		assertNull(strs[1]);
	}

	@Test
	public void testReadAuthFileNotFull() {
		String[] strs = FroxyCore.readAuthFile(new File("src/test/resources/auth/file2"));
		assertEquals(2, strs.length);
		assertEquals("id", strs[0]);
		assertNull(strs[1]);
	}

	@Test
	public void testReadAuthFile() {
		String[] strs = FroxyCore.readAuthFile(new File("src/test/resources/auth/file3"));
		assertEquals(2, strs.length);
		assertEquals("id", strs[0]);
		assertEquals("secret", strs[1]);
	}

	@Test
	public void testReadAuthFileMore() {
		String[] strs = FroxyCore.readAuthFile(new File("src/test/resources/auth/file4"));
		assertEquals(2, strs.length);
		assertEquals("id", strs[0]);
		assertEquals("secret", strs[1]);
	}

	@Test
	public void testIsNotNullOrEmptyNull() {
		assertFalse(FroxyCore.isNotNullOrEmpty(null));
	}

	@Test
	public void testIsNotNullOrEmptyEmpty() {
		assertTrue(FroxyCore.isNotNullOrEmpty(new String[] {}));
	}

	@Test
	public void testIsNotNullOrEmptyOneNull() {
		assertFalse(FroxyCore.isNotNullOrEmpty(new String[] { "1", "2", null, "4", "5" }));
	}

	@Test
	public void testIsNotNullOrEmptyAllNull() {
		assertFalse(FroxyCore.isNotNullOrEmpty(new String[] { null, null, null, null, null }));
	}
}
