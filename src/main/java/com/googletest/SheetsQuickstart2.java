package com.googletest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.prive.utils.GoogleSheetUtil;

public class SheetsQuickstart2 {

	/**
	 * Prints the names and majors of students in a sample spreadsheet:
	 * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
	 */
	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		final String spreadsheetId = "1waW7SIPy4ml8CreNHvkFjsDeaxkp6ifM-HPXxSwLvac";
		final String range = "201607!A1:E";
		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, GoogleSheetUtil.JSON_FACTORY,
				GoogleSheetUtil.getCredentials(HTTP_TRANSPORT)).setApplicationName(GoogleSheetUtil.APPLICATION_NAME)
						.build();
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.isEmpty()) {
			System.out.println("No data found.");
		} else {
			System.out.println("Name, Major: " + values.size());
			for (List row : values) {
				// Print columns A and E, which correspond to indices 0 and 4.
				System.out.printf("%s, %s\n", row.get(0), row.get(1));
			}
//			GoogleSheetUtil.AppendRow(spreadsheetId);
		}
	}
}