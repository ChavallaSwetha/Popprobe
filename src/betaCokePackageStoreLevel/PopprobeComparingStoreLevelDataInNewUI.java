package betaCokePackageStoreLevel;

import java.awt.AWTException;
import java.io.FileOutputStream;
import java.io.IOException;

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

public class PopprobeComparingStoreLevelDataInNewUI {
	
	public static void main(String[] args)
			throws InterruptedException, BiffException, IOException, WriteException, AWTException {
		String[] countriesFromUI = {"JAMAICA"};//,"BAHAMAS","BARBADOS","FRENCH GUIANA","GUYANA","GUADELOUPE","MARTINIQUE","SURINAME","TRINIDAD Y TOBAGO" };
		String[] countriesFromExcel = {"Jamaica"};//,"Bahamas","Barbados","French Guiana","Guyana","Guadeloupe","Martinique","Suriname","Trinidad & Tobago" };
//	String[] countriesFromUI = {"HAITI", "BELIZE" };
//		String[] countriesFromExcel = {"Haiti", "Belize" };
		String date = "2018 - 1";
		String channelFromXL = "Tradicional";
		String channel = "HOME MARKET TRADITIONAL";
		//String channelLoop = "traditional";
		String mpaChannelXL = "Portafolio Prioritario";
		String soviChannelXL = "SOVI";
		String refregiratioChannelXL = "Refrigeración";
		String commuNionChannelXL = "Comunicación y exhibición";
		String priCeChannelXL = "Respeto a Precio";
		String freshNESsChannelXL = "Frescura de Producto";
		String readFilePath = "C:/Users/User/Downloads/Caribbean ICE Results January 2018.xls";
		String writeFilePath = "C:/Users/User/Downloads/January_2018_Store_Level_Jamaica.xls";
		FileOutputStream fileOutput = new FileOutputStream(writeFilePath);
		WritableWorkbook writeWorkBook = Workbook.createWorkbook(fileOutput);
		System.setProperty("webdriver.chrome.driver","C:/Users/User/Downloads/chromedriver_win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		PopprobeNewLogin login = new PopprobeNewLogin();
		login.logIn(driver);
		for (int i = 0; i < countriesFromUI.length; i++) {
			
			ComparingStoreLevelDataInNewUIAndWritingXL compare = new ComparingStoreLevelDataInNewUIAndWritingXL();
			ReadingDataFromNewUI namesAndTotal = new ReadingDataFromNewUI();
			UIData dataUI = namesAndTotal.readingDataFromUI(driver, date, countriesFromUI[i], channel);
			ReadingDataFromxl storeAndIce = new ReadingDataFromxl();
			XLData xldata = storeAndIce.readingDataFromXL(readFilePath, countriesFromExcel[i], channelFromXL,
					mpaChannelXL, soviChannelXL, refregiratioChannelXL, commuNionChannelXL, priCeChannelXL,
					freshNESsChannelXL);
			compare.comparingAndWritingData(writeFilePath, writeWorkBook, dataUI, xldata, countriesFromUI[i], channel,
					i);
			// login.logout(driver);
		}
		writeWorkBook.write();
		writeWorkBook.close();
	}

}
