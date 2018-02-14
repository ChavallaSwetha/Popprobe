package cokePackage;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class PopprobeComparingStoreLevelPremiseData {
	
	public static void main(String[] args) throws InterruptedException, BiffException, IOException, WriteException {

		String[] countriesFromUI = {"BAHAMAS","GUADELOUPE", "BARBADOS","FRENCH GUIANA","GUYANA","GUADELOUPE","JAMAICA","MARTINIQUE","SURINAME","TRINIDAD Y TOBAGO"};
		String[] countriesFromExcel = { "Bahamas","Guadeloupe","Barbados","French Guiana","Guyana","Guadeloupe","Jamaica","Martinique","Suriname","Trinidad & Tobago"};
		//String date = "2017 - 8";
		String channelFromXL = "On Premise";
		String channel = "ON PREMISE";
		String channelLoop = "premise";
		String mpaChannelXL = "Portafolio Prioritario";
		String soviChannelXL = "SOVI";
		String refregiratioChannelXL = "Refrigeración";
		String commuNionChannelXL = "Comunicación";
		String colDAvChannelXL = "Disponibilidad en Frío";
		String comBoChannelXL = "Combos";

		String readFilePath = "C:/Users/Mona Lisa/Downloads/Caribbean ICE Results August 2017.xls";
		String writeFilePath = "C:/Users/Mona Lisa/Downloads/Data_of_Popprobe/All_Stores_Premise_Data_Aug.xls";
		FileOutputStream fileOutput = new FileOutputStream(writeFilePath);
		WritableWorkbook writeWorkBook = Workbook.createWorkbook(fileOutput);
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/Mona Lisa/Downloads/chromedriver_win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		for (int i = 0; i < countriesFromUI.length; i++) {
			PopprobeLogin login = new PopprobeLogin();
			login.logIn(driver);
			new SelectFilters(driver,"2017 - 8",countriesFromUI[i],"ON PREMISE");
			//login.selectDropDowns(driver, date, countriesFromUI[i], channel);
			ComparingStoreLevelPremiseDataAndWritingXL compare = new ComparingStoreLevelPremiseDataAndWritingXL();
			ReadingDataFromUI namesAndTotal = new ReadingDataFromUI();
			UIData dataUI = namesAndTotal.readingDataFromUI(driver, channelLoop);
			ReadingDataFromxl storeAndIce = new ReadingDataFromxl();
			XLData xldata = storeAndIce.readingDataFromXL(readFilePath, countriesFromExcel[i], channelFromXL,
					mpaChannelXL, soviChannelXL, refregiratioChannelXL, commuNionChannelXL,   colDAvChannelXL, comBoChannelXL
					);
			
			compare.comparingAndWritingData(writeFilePath, writeWorkBook, dataUI, xldata, countriesFromUI[i], channel,
					i);
			login.logout(driver);
		}
		writeWorkBook.write();
		writeWorkBook.close();
	}


}
