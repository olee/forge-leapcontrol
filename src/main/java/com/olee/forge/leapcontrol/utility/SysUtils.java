package com.olee.forge.leapcontrol.utility;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public class SysUtils {

	public static void setLibraryPath(String path) {
		try {
			System.setProperty("java.library.path", path);
			Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the specified path to the java library path
	 *
	 * @param pathToAdd
	 *            the path to add
	 * @throws Exception
	 */
	public static void addLibraryPath(String pathToAdd) {
		for (String p : System.getProperty("java.library.path").split(";"))
			if (p.equals(pathToAdd))
				return;
		setLibraryPath(System.getProperty("java.library.path") + ";" + pathToAdd);
	}

	public static boolean fileExists(String filename) {
		File f = new File(filename);
		return (f.exists() && !f.isDirectory());
	}

	public static boolean fileExistsInPath(String filename) {
		String paths = System.getProperty("java.library.path") + ";" + System.getenv("PATH");
		for (String p : paths.split(";")) {
			if (p.charAt(p.length() - 1) != File.separatorChar)
				p += File.separatorChar;
			if (fileExists(p + filename))
				return true;
		}
		return fileExists(filename);
	}

}
