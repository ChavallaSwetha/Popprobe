package cokePackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.awt.AWTException;

import java.io.FileOutputStream;
import java.io.IOException;


import jxl.Workbook;
import jxl.read.biff.BiffException;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class PopprobeComparingStoreLevelData {
	
public static void main(String[] args) throws InterruptedException, BiffException, IOException, WriteException {
		
		
		/*String[] countriesFromUI = {"BAHAMAS","BARBADOS","FRENCH GUIANA","GUYANA","GUADELOUPE","JAMAICA","MARTINIQUE","SURINAME","TRINIDAD Y TOBAGO"};
		String[] countriesFromExcel = {"Bahamas","Barbados","French Guiana","Guyana","Guadeloupe","Jamaica","Martinique","Suriname","Trinidad & Tobago"};*/
		String[] countriesFromUI = {"BELIZE","HAITI"};
		String[] countriesFromExcel = {"Belize","Haiti"};
		String date = "2017 - 8";
		String channelFromXL ="Tradicional";
		String channel = "HOME MARKET TRADITIONAL";
		String channelLoop = "traditional";
		String mpaChannelXL = "Portafolio Prioritario";
		String soviChannelXL = "SOVI";
		String refregiratioChannelXL = "Refrigeración";
		String commuNionChannelXL = "Comunicación y exhibición";
		String priCeChannelXL = "Respeto a Precio";
		String freshNESsChannelXL = "Frescura de Producto";
		String readFilePath = "C:/Users/Mona Lisa/Downloads/Caribbean ICE Results August 2017.xls";
		String writeFilePath = "C:/Users/Mona Lisa/Downloads/Data_of_Popprobe/Belize_And_Haiti_Data_Aug.xls";
		FileOutputStream fileOutput = new FileOutputStream(writeFilePath);
		WritableWorkbook writeWorkBook = Workbook.createWorkbook(fileOutput);
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/Mona Lisa/Downloads/chromedriver_win32/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			
			for(int i=0; i<countriesFromUI.length; i++){
				PopprobeLogin login = new PopprobeLogin();
				login.logIn(driver);
				//new SelectFilters(driver,"2017 - 8",countriesFromUI[i],"HOME MARKET TRADITIONAL");
				login.selectDropDowns(driver,date, countriesFromUI[i]);
				ComparingStoreLevelDataAndWritingXL compare = new ComparingStoreLevelDataAndWritingXL();
				ReadingDataFromUI namesAndTotal = new ReadingDataFromUI();
				UIData dataUI = namesAndTotal.readingDataFromUI(driver, channelLoop);
				ReadingDataFromxl storeAndIce = new ReadingDataFromxl();
				XLData xldata = storeAndIce.readingDataFromXL(readFilePath, countriesFromExcel[i], channelFromXL,  mpaChannelXL, soviChannelXL,refregiratioChannelXL, commuNionChannelXL, priCeChannelXL, freshNESsChannelXL );
				compare.comparingAndWritingData(writeFilePath,writeWorkBook,dataUI, xldata,countriesFromUI[i],channel,i);
				login.logout(driver);
			}
		writeWorkBook.write();
		writeWorkBook.close();	
	}


}
