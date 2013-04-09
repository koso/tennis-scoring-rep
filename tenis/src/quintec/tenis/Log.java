package quintec.tenis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Log {
	private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static void LogToFile(String text) {
		File logFile = new File("sdcard/log.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			String logTest = GetUTCdatetimeAsString() + ": " +  text;
			buf.append(logTest);
			buf.newLine();
			buf.flush();
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Date GetUTCdatetimeAsDate() {
		// note: doesn't check for null
		return StringDateToDate(GetUTCdatetimeAsString());
	}

	public static String GetUTCdatetimeAsString() {
		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String utcTime = sdf.format(new Date());

		return utcTime;
	}

	public static Date StringDateToDate(String StrDate) {
		Date dateToReturn = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

		try {
			dateToReturn = (Date) dateFormat.parse(StrDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateToReturn;
	}
}
