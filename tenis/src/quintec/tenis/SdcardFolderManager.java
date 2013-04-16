package quintec.tenis;

import java.io.File;

public class SdcardFolderManager {
	
	private static String MAIN_FOLDER_PATH = "sdcard/Tenis Scoring/";

	private static final void createFolder() {
		File mainFolder = new File(MAIN_FOLDER_PATH);
		if (!mainFolder.exists()) {
			mainFolder.mkdir();
		}
	}
	
	public static String getMainFolderPath() {
		createFolder();
		return MAIN_FOLDER_PATH;
	}
}
