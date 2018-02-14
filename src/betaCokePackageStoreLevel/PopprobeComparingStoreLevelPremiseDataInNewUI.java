package betaCokePackageStoreLevel;

import java.awt.AWTException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cokePackage.PopprobeNewLogin;
import cokePackage.ReadingDataFromxl;
import cokePackage.UIData;
import cokePackage.XLData;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class PopprobeComparingStoreLevelPremiseDataInNewUI {
	
	public static void main(String[] args)
			throws InterruptedException, BiffException, IOException, WriteException, AWTException {
		//String dateTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		//System.out.println("Current date and Time: "+ dateTime);
		String[] countriesFromUI = { "FRENCH GUIANA" };//,"BAHAMAS","FRENCH GUIANA","GUYANA","GUADELOUPE","JAMAICA","MARTINIQUE","SURINAME","TRINIDAD Y TOBAGO" };
		String[] countriesFromExcel = { "French Guiana"};//,"Bahamas","French Guiana","Guyana","Guadeloupe","Jamaica","Martinique","Suriname","Trinidad & Tobago" };
		String date = "2017 - 6";
		String channelFromXL = "On Premise";
		String channel = "ON PREMISE";
		String mpaChannelXL = "Portafolio Prioritario";
		String soviChannelXL = "SOVI";
		String refregiratioChannelXL = "Refrigeración";
		String commuNionChannelXL = "Comunicación";
		String colDAvChannelXL = "Disponibilidad en Frío";
		String comBoChannelXL = "Combos";
		String readFilePath = "C:/Users/User/Downloads/Caribbean ICE Results June 2017.xls";
		String writeFilePath = "C:/Users/User/Downloads/January_2018_Store_level_data.xls";
		FileOutputStream fileOutput = new FileOutputStream(writeFilePath);
		WritableWorkbook writeWorkBook = Workbook.createWorkbook(fileOutput);
		System.setProperty("webdriver.chrome.driver","C:/Users/User/Downloads/chromedriver_win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		PopprobeNewLogin login = new PopprobeNewLogin();
		login.logIn(driver);
		for (int i = 0; i < countriesFromUI.length; i++) {
			
			ComparingStoreLevelPremiseDataInNewUIAndWritingXL compare = new ComparingStoreLevelPremiseDataInNewUIAndWritingXL();
			ReadingDataFromNewUI namesAndTotal = new ReadingDataFromNewUI();
			UIData dataUI = namesAndTotal.readingDataFromUI(driver, date, countriesFromUI[i], channel);
			ReadingDataFromxl storeAndIce = new ReadingDataFromxl();
			XLData xldata = storeAndIce.readingDataFromXL(readFilePath, countriesFromExcel[i], channelFromXL,
					mpaChannelXL, soviChannelXL, refregiratioChannelXL, commuNionChannelXL, colDAvChannelXL,
					comBoChannelXL);
			compare.comparingAndWritingData(writeFilePath, writeWorkBook, dataUI, xldata, countriesFromUI[i], channel,
					i);
			// login.logout(driver);
		}
		writeWorkBook.write();
		writeWorkBook.close();
	}

}
