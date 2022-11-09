package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JXlKeywords extends CommonFrameworkKeywords {
	public int testCaseDataRow =0;
	public ArrayList<String> testCaseNames = new ArrayList<String>();
	public int dataRowNo=0;
	public String currentTestCaseName="";
	
	public static int rowCountNo,colCountNo;
	public  int rowCount = 0, colCount = 0, maxRows = 100;
			
	public  String wkbook = "";

	public  File nf;
	public  Workbook w;
	public int tempNo=1;
	public  Sheet s;
	public int testDataSheetNo=0;
	public  String currentExcelBook, currentExcelSheet;
	public List<Integer> iterationCount = new ArrayList<Integer>();
	public void useExcelSheet(String wkbook,int sheetNo) {
		currentExcelBook = wkbook;
		nf = new File(currentExcelBook);
		if (nf.exists()) {
			try {
				w = Workbook.getWorkbook(nf);
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				s = w.getSheet(sheetNo);
				updateTestRowCount();
				updateTestColCount();
		}
	}
	public String getData(int row, int col) {
		Cell c;
		c = s.getCell(col - 1, row - 1);
		return c.getContents();
	}

	public void loadTestData() 
	{
		useExcelSheet(getFrameworkConfigProperty("InputDataFile"),(testDataSheetNo-1));
		Sheet readsheet = w.getSheet((testDataSheetNo-1));
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(0, i).getContents();
			testCaseNames.add(testCaseName);
			
		}
	}
	public int getTestDataRowNo(String testCaseName)
	{
		int rowNo=0;
		loadTestData();
		for(String a: testCaseNames)
        {
			
			if(a.trim().equalsIgnoreCase(testCaseName.trim()))
			{
				
				rowNo=rowNo+1;
				break;
			}
			rowNo++;
        }
		return rowNo;
		
	}
	public  String getTextData(String Label)
	{
		if(getTextData(dataRowNo, Label).toLowerCase().contains("current date"))
		{

			String temp="";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String month[]=dateFormat.format(date).toString().split("-");
			//System.out.println(month[0]);
			switch (month[1]) {
		    case "01": 
		    	temp= month[1].replaceAll("01", "Jan");
		        break;

		    case "02": 
		    	temp= month[1].replaceAll("02", "Feb");
		        break;
		    case "03": 
		    	temp= month[1].replaceAll("03", "Mar");
		        break;

		    case "04": 
		    	temp= month[1].replaceAll("04", "Apr");
		        break;
		    case "05": 
		    	temp= month[1].replaceAll("05", "May");
		        break;

		    case "06": 
		    	temp= month[1].replaceAll("06", "Jun");
		        break;
		    case "07": 
		    	temp= month[1].replaceAll("07", "Jul");
		        break;

		    case "08": 
		    	temp= month[1].replaceAll("08", "Aug");
		        break;
		    case "09": 
		    	temp= month[1].replaceAll("09", "Sep");
		        break;

		    case "10": 
		    	temp= month[1].replaceAll("10", "Oct");
		        break;
		    case "11": 
		    	temp= month[1].replaceAll("11", "Nov");
		        break;

		    case "12": 
		    	temp= month[1].replaceAll("12", "Dec");
		        break;
		    

		    default: 
		        System.out.println("Invalid month.");
		        break;
		}
			return (month[2]+"-"+temp+"-"+month[0]);
		
		}
		else
		{
			return getTextData(dataRowNo, Label);
		}
	}

public  String getTextData(int datasetNo, String colLabel) {  
		
            return getData(datasetNo, returnColNo(getTestDataRowNo(currentTestCaseName),colLabel));
            
      }
      public  int returnColNo(int datasetNo,String colLabel) {
            boolean flag = true;
            int temp = 0;
            while (flag) 
            {
                  
                  try 
                  {
                	  	temp++;
                        if (getData(datasetNo, temp).trim().equalsIgnoreCase(colLabel.trim())) 
                        {
                              flag = false;
                              return temp;
                        }
                        
                  } catch (Exception e)
                  {
                     CommonFrameworkKeywords.testCaseFailed();
                	  break;
                  }
            }
            return 0;
      }

	public void updateTestRowCount() {
		boolean flag = true;
		int temp = 0;
		while (flag) {
			temp++;
			try {
				if (getData(temp, 1).trim().length() == 0) {
					flag = false;
				}
			} catch (Exception e) {
				rowCountNo = temp - 2;
				break;
			}
		}

	}

	public void updateTestColCount() {
		boolean flag = true;
		int temp = 0;
		while (flag) {
			temp++;
			try {
				if (getData(1, temp).trim().length() == 0) {
					flag = false;
				}
			} catch (Exception e) {
				colCountNo = temp - 1;
				break;
			}
		}
	}

	

	public void closeExcelSheet() {
		currentExcelBook = "";
		w.close();
		nf = null;
	}
	public void textCaseDataRowCount()
	  {
	    try
	    {
	    	testCaseDataRow=getTestDataRowNo(currentTestCaseName);
	      for (int i = 1;; i++) {
	        if (getData(testCaseDataRow + i, 2).equalsIgnoreCase("yes")) {
	          iterationCount.add(Integer.valueOf(testCaseDataRow + i));
	        } else {
	          if (getData(testCaseDataRow + i, 2).equalsIgnoreCase("")) {
	            break;
	          }
	        }
	      }
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.toString());
	    }
	  }
	public void testDataRowNo(int no)
	{
		testCaseDataRow=no;
	}
}
