
package com.ebizprise.das.poi;

import java.util.Map;

/**
 * Callback for processing a single row from excel file. Map keys are same as first row header
 * columns.
 * 
 * 
 */
public interface ExcelRowContentCallback {

  void processRow(int rowNum, Map<String, String> map) throws Exception;
  
  
  

}
