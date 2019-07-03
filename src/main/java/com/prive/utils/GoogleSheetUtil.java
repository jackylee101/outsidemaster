package com.prive.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Append;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.InsertDimensionRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetUtil {
	private static Log logger = LogFactory.getLog(GoogleSheetUtil.class);

	public static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = GoogleSheetUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public static ValueRange makeValueRange(String[][] ss1) {

		List values = new ArrayList();

		for (int i = 0; i < ss1.length; i++) {
			List<String> list1 = new ArrayList<String>(Arrays.asList(ss1[i]));
			values.add(list1);
		}

		ValueRange VRange = new ValueRange();

		VRange.setValues(values);
		return VRange;
	}

	public static void AppendRow(String spreadsheetId, String sheetName, String startCell, String[][] ssValue) {
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, GoogleSheetUtil.JSON_FACTORY,
					GoogleSheetUtil.getCredentials(HTTP_TRANSPORT)).setApplicationName(GoogleSheetUtil.APPLICATION_NAME)
							.build();

			String range = sheetName + "!" + startCell;

			ValueRange VRange = makeValueRange(ssValue);

//			VRange.setRange(range);

			Append upd = service.spreadsheets().values().append(spreadsheetId, range, VRange);
			upd.setValueInputOption("RAW");// "USER_ENTERED");
			AppendValuesResponse responses = upd.execute();
			logger.info(responses.getUpdates().getUpdatedRange());
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static void AppendFormat(String spreadsheetId) {
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, GoogleSheetUtil.JSON_FACTORY,
					GoogleSheetUtil.getCredentials(HTTP_TRANSPORT)).setApplicationName(GoogleSheetUtil.APPLICATION_NAME)
							.build();

			List<Request> requests = new ArrayList<>();

			List<CellData> values = new ArrayList<>();

			values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("Hello World!")));

			requests.add(new Request()
					.setAddSheet(new AddSheetRequest().setProperties(new SheetProperties().setTitle("scstc"))));
			requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
					.setStart(new GridCoordinate().setSheetId(753673215).setRowIndex(0).setColumnIndex(0))
					.setRows(Arrays.asList(new RowData().setValues(values)))
					.setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

			BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);

			BatchUpdateSpreadsheetResponse response = service.spreadsheets().batchUpdate(spreadsheetId, body).execute();

//			service.spreadsheets().batchUpdate(spreadsheetId, content).execute();
//			upd.setValueInputOption("RAW");//"USER_ENTERED");
//			AppendValuesResponse responses = upd.execute();
//			logger.info(responses.getUpdates().getUpdatedRange());
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	// Creates a new row at row 2 then writes data to it...
	public static void writeTest(Report r, String sheetID, String range) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		ValueRange requestBody = requestBuilder(r, range);
		Sheets sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		insertRow(sheetsService, sheetID);
		Sheets.Spreadsheets.Values.Append request = sheetsService.spreadsheets().values().append(sheetID, range,
				requestBody);
		request.setValueInputOption("RAW");
		// request.setInsertDataOption("INSERT_ROWS");
		AppendValuesResponse resp = request.execute();

		System.out.println(resp);
	}

	// Inserts row at index, this is hardcoded to row 2 (0-indexed)
	private static void insertRow(Sheets sheets, String sheetID) throws IOException {
		InsertDimensionRequest insertRow = new InsertDimensionRequest();
		insertRow.setRange(new DimensionRange().setDimension("ROWS").setStartIndex(1).setEndIndex(2).setSheetId(0));

		BatchUpdateSpreadsheetRequest r = new BatchUpdateSpreadsheetRequest()
				.setRequests(Arrays.asList(new Request().setInsertDimension(insertRow)));
		sheets.spreadsheets().batchUpdate(sheetID, r).execute();
	}

	// Populate ValueRange
	static ValueRange requestBuilder(Report r, String range) {
		ValueRange v = new ValueRange()
				.setValues(Arrays.asList(Arrays.asList(r.value1), Arrays.asList(r.value2), Arrays.asList(r.value3),
						Arrays.asList(r.value4), Arrays.asList(r.value5)))// .setMajorDimension("ROWS")
				.setRange(range);
		return v;
	}

	// Creates a new row at row 2 then writes data to it...
	public static void deleteTest(String spreadsheetId) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		deleteRow(sheets, spreadsheetId);
	}

	private static void deleteRow(Sheets sheets, String spreadsheetId) {
		List<Request> requests = new ArrayList<>();

		DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();

		DimensionRange dimensionRange = new DimensionRange();
		dimensionRange.setStartIndex(0);
		dimensionRange.setEndIndex(1);
		dimensionRange.setDimension("ROWS");

		deleteDimensionRequest.setRange(dimensionRange);

		requests.add(new Request().setDeleteDimension(deleteDimensionRequest));

		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);

		try {
			sheets.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}