/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.powerpc.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class InputReaderTest {

	private static final String BASE_RESOURCE = "ch/zhaw/powerpc/controller/";

	@Test
	public void shoudlOmitComments() {
		String[] contents = readContents("shouldOmitComments");
		assertEquals("1010", contents[0]);
		assertEquals("0101", contents[1]);
		assertEquals("500=1010", contents[2]);
	}

	@Test
	public void shouldReadEmptyFile() {
		assertEquals(0, readContents("shouldReadEmptyFile").length);
	}

	@Test
	public void shouldOmitEmptyLines() {
		String[] contents = readContents("shouldOmitEmptyLines");
		assertEquals("1010", contents[0]);
		assertEquals("0101", contents[1]);
	}

	@Test
	public void shouldReadInputSequentially() {
		String[] contents = readContents("shouldReadInputSequentially");
		assertEquals("500=1111", contents[0]);
		assertEquals("1010", contents[1]);
		assertEquals("0101", contents[2]);
		assertEquals("501=0000", contents[3]);
	}

	@Test
	public void shouldTrimInputOnBothSides() {
		String[] contents = readContents("shouldTrimInputOnBothSides");
		assertEquals("1010", contents[0]);
		assertEquals("0101", contents[1]);
		assertEquals("1111", contents[2]);
	}

	@Test
	public void shouldTrimInputWithCommentsOnBothSides() {
		System.out.println("Start with shouldTrimInputCommentsBothSides");
		String[] contents = readContents("shouldTrimInputWithCommentsOnBothSides");
		assertEquals("1010", contents[0]);
		assertEquals("0101", contents[1]);
		assertEquals("1111", contents[2]);
	}

	@Test
	public void shouldOmitCommentOnlyLines() {
		String[] contents = readContents("shouldOmitCommentOnlyLines");
		assertEquals("1010", contents[0]);
		assertEquals("0101", contents[1]);
	}

	private String[] readContents(String filename) {
		filename = BASE_RESOURCE + filename;
		InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
		if (is == null) {
			fail("File " + filename + " does not exist");
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			return new InputReader(br).readContents();
		} catch (Exception e) {
			fail(e.getMessage());
			return null; // never happens
		}
	}

}
