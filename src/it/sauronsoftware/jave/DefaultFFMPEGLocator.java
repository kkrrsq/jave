package it.sauronsoftware.jave;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class DefaultFFMPEGLocator extends FFMPEGLocator {
	private static final int myEXEversion = 1;
	private String path;

	public DefaultFFMPEGLocator() {
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows;
		String fileName;
		if (os.indexOf("windows") != -1) {
			isWindows = true;
			String arch = System.getenv("PROCESSOR_ARCHITECTURE");
			String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
			if (arch.endsWith("64") || wow64Arch != null && wow64Arch.endsWith("64")) {
				fileName = "ffmpeg_win64.exe";
			} else {
				fileName = "ffmpeg_win32.exe";
			}
		} else {
			isWindows = false;
			try {
				Process process = Runtime.getRuntime().exec("getconf LONG_BIT");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String s = bufferedReader.readLine();
				if (s.contains("64")) {
					fileName = "ffmpeg_linux64";
				} else {
					fileName = "ffmpeg_linux64";
				}
			} catch (IOException e) {
				throw new RuntimeException("jave init fail!", e);
			}
		}

		File temp = new File(System.getProperty("java.io.tmpdir"), "jave-1");

		if (!temp.exists()) {
			temp.mkdirs();
			temp.deleteOnExit();
		}

		String suffix = isWindows ? ".exe" : "";
		File exe = new File(temp, "ffmpeg" + suffix);
		if (!exe.exists()) {
			copyFile(fileName, exe);
		}

		if (!isWindows) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(new String[] { "/bin/chmod", "755", exe.getAbsolutePath() });
			} catch (IOException e) {
				throw new RuntimeException("jave init fail!", e);
			}
		}

		this.path = exe.getAbsolutePath();
	}

	protected String getFFMPEGExecutablePath() {
		return this.path;
	}

	private void copyFile(String path, File dest) throws RuntimeException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = getClass().getResourceAsStream(path);
			output = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int l;
			while ((l = input.read(buffer)) != -1) {
				output.write(buffer, 0, l);
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot write file " + dest.getAbsolutePath());
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (Throwable localThrowable) {
				}
			if (input != null)
				try {
					input.close();
				} catch (Throwable localThrowable1) {
				}
		}
	}
}