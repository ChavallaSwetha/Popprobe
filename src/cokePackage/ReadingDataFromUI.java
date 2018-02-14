package cokePackage;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cokePackage.ComparingStoreLevelDataAndWritingXL.TraditionalKPIs;
import cokePackage.ComparingStoreLevelPremiseDataAndWritingXL.PremiseKPIs;

public class ReadingDataFromUI {
	
	/*
	 * After selecting date, country and channel go to Datagrid. Using function
	 * readingDataFromUI() read datagrid data.
	 */
	public UIData readingDataFromUI(WebDriver driver, String channel) throws InterruptedException {

		Float totalUIAfterConvertingTofloat;
		
		Float mpaUI;
		
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='sidebar-panel']/ul/li[3]/a/img")).click(); // Datagrid
		Thread.sleep(4000);
		WebElement customerData = driver.findElement(By.xpath(
				".//*[@id='shell-view']/section/section/div/div/div/div[3]/div/div[1]/div/div[1]/div/div/table/tbody"));
		Thread.sleep(3000);
		List<WebElement> tableRows = customerData.findElements(By.tagName("tr"));
		Thread.sleep(3000);
		int rowsCount = tableRows.size();
		System.out.println("No of rows in UI" + "      " + rowsCount);
		UIData dataUI = new UIData();

		for (int i = 0; i < rowsCount; i++) {
			StringBuffer str = new StringBuffer();
			List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
			str.append("[");
			for (int j = 0; j < columns.size(); j++) {
				if (j != 0) {
					str.append(",");
				}
				str.append(columns.get(j).getText());

			}
			str.append("]");
			// System.out.println(str);

			String storeName = columns.get(1).getText();
			String custSK = columns.get(2).getText();
			/*dataUI.setCustUI(storeName, custSK);
			String railing = columns.get(4).getText();
			dataUI.setRailUI(storeName, railing);
			String cooler = columns.get(3).getText();
			dataUI.setCoolUI(storeName, cooler);*/
			if (channel.equals("traditional")) {
		
				for (TraditionalKPIs kpiEnum : TraditionalKPIs.values()) {
					int columnValue = ComparingStoreLevelDataAndWritingXL.traditionalProperties.get(kpiEnum)[4];
					String mpa = columns.get(columnValue).getText();
					if (mpa.contains("NA")) {
						totalUIAfterConvertingTofloat = 0.0f;
						mpaUI = totalUIAfterConvertingTofloat;
					} else {
						totalUIAfterConvertingTofloat = Float.parseFloat(mpa);
						mpaUI = totalUIAfterConvertingTofloat;
					}
                 //  dataUI.setKPIUI(storeName, kpiEnum,  mpaUI);
				}
			} else {
				for (PremiseKPIs kpiEnum : PremiseKPIs.values()) {
					int columnValue = ComparingStoreLevelPremiseDataAndWritingXL.premiseProperties.get(kpiEnum)[4];
					String mpa = columns.get(columnValue).getText();
					
					if (mpa.contains("NA")) {
						totalUIAfterConvertingTofloat = 0.0f;
						mpaUI = totalUIAfterConvertingTofloat;
					} else {
						totalUIAfterConvertingTofloat = Float.parseFloat(mpa);
						mpaUI = totalUIAfterConvertingTofloat;
					}
					System.out.println(kpiEnum+" KPI Value from UI"+" "+ mpaUI);
				//	dataUI.setNewKPIUI(storeName, kpiEnum,  mpaUI);
				}

			}

		}
		System.out.println("Reading Data from UI");
		return dataUI;
	}

}
