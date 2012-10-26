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
