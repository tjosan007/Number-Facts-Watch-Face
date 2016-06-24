package com.tarwinderjosan.numberfactswatchface.facts;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.tarwinderjosan.numberfactswatchface.util.DBHelper;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a single Spreadsheet which is able to load different sheets.
 */
public class Spreadsheet {


    // Index 0, first row, minute 1
    // Index 1, second row, minute 2
    private List<List<String>> listHolder;
    private AssetManager mAssets;
    private Context mContext;

    // Name of the Excel file
    private String mName;
    // Number of columns in the specific sheet
    private final int COLUMNS = 60;

    // Name of the sheet in the Excel file
    private String mSheetName;

    private int MAX_LENGTH;


    /**
     * Construct a Spreadsheet.
     * @param name Name of the file in the assets directory
     */
    public Spreadsheet(String name) {
        mName = name;
        mContext = Utils.CONTEXT;
        mAssets = mContext.getAssets();
    }

    /**
     * Get the most number of facts located in any column.
     * This becomes the standard size of each column.
     */
    private int getMaxFacts(HSSFWorkbook loaderWorkbook, String sheetName) {
        HSSFSheet sheet  = loaderWorkbook.getSheet(sheetName);

        int max = 0;
        for(int i = 0; i < 60; i++) {
            int cells = sheet.getRow(i).getPhysicalNumberOfCells();
            if(sheet.getRow(i).getPhysicalNumberOfCells() > max) {
                max = cells;
            }

        }

        return max;
    }

    /**
     * Read the entire content of a row and return an ArrayList containing it.
     * Rows are indexed  0 - 59, where 0 is the first row being minute 1 and
     * 59 is the last row, being minute 60 or :00
     * @param loaderWorkbook HSSFWorkbook object linked to the excel sheet
     * @param rowIndex The index of the row
     * @param sheetName The name of the sheet contained in
     * @param length The mandatory size (the size of the column with most facts)
     * @return ArrayList containg data for the row
     */
    private List<String> getRowContent(HSSFWorkbook loaderWorkbook, int rowIndex, String sheetName
    , int length) {

        List<String> tempList = new ArrayList<>();
        HSSFSheet sheet = loaderWorkbook.getSheet(sheetName);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getRowNum() > 0) { // Skip header
                if(row.getRowNum() == rowIndex) { // Row data to get
                    // Fill ArrayList with the row data
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while(cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch(cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                tempList.add(cell.getStringCellValue());
                                break;
                        }
                    }

                }
            }
        }

        while(tempList.size() < length) {
            tempList.add("N/A");
        }
        return tempList;
    }

    public void setSheetName(String sheetName) {
        mSheetName = sheetName;
    }


    /**
     * Loads the sub-sheet from the Spreadsheet and fills ArrayList.
     * @return true if successful, false if not
     */
    public boolean load() {
        if(listHolder == null)
            listHolder = new ArrayList<>(60);

        try {
            InputStream is = mAssets.open(mName);
            HSSFWorkbook loaderWorkbook = new HSSFWorkbook(is);
            // Add each column of data to main ArrayList holder
            // Skip first row (header)
            int length = getMaxFacts(loaderWorkbook, mSheetName);
            MAX_LENGTH = length;
            /*
            Ability to improve this algorithm.
            Instead of looping through and calling getRowContent() 60 times,
            only call it one time and have it add to list holder after each row it comes
            across.
            Because each call to getRowContent() skips over
             */
            for(int i=1;i<=60; i++) {
                listHolder.add(getRowContent(loaderWorkbook, i, mSheetName, length));
            }
            loaderWorkbook.close();
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String get(int col, int row) {
        // Get column content
        return listHolder.get(col).get(row);
    }

    /**
     * Resets values to prepare for reading a new sheet.
     */
    public void reset() {
        mSheetName = "";
        listHolder = null;
    }

    public void storeInDatabase(SQLiteDatabase db, String tableName) {
        DBHelper.storeInDatabase(db, tableName, listHolder, this);
    }

    public int getMax() {
        // Return maximum size of the individuals ArrayLists
        return MAX_LENGTH;
    }

}
