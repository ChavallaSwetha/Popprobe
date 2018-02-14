package betaCokePackageStoreLevel;

import java.io.IOException;

import cokePackage.PopprobeNewLogin;
import cokePackage.UIData;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import betaCokePackageStoreLevel.ComparingStoreLevelDataInNewUIAndWritingXL.NewTraditionalKPIs;
import betaCokePackageStoreLevel.ComparingStoreLevelPremiseDataInNewUIAndWritingXL.NewPremiseKPIs;


public class ReadingDataFromNewUI {
	public UIData readingDataFromUI(WebDriver driver,String date, String country, String channel) throws InterruptedException, IOException {
		UIData dataUI = new UIData();
		//String storeName = null;
		Float totalUIAfterConvertingTofloat;
		//Float totalUI = null;
		Thread.sleep(10000);
		driver.findElement(By.id("menu-item-2")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("STORES")).click();
		PopprobeNewLogin filters = new PopprobeNewLogin();
		Thread.sleep(5000);
		filters.selectDropDowns(driver, date, country, channel);
		//filters.selectDropDowns(driver, date, country); // For Belize
		ReadingDataFromCountriesNewUI strCount = new ReadingDataFromCountriesNewUI();
		Thread.sleep(4000);
		UIData storesFromCountry = strCount.readingDataFromCountries(driver,country,channel);
		Thread.sleep(3000);
		driver.findElement(By.id("menu-item-2")).click();
		Thread.sleep(6000);
		driver.findElement(By.linkText("STORES")).click();
		Thread.sleep(3000);
		int stores = 0;
		String surveySK = null;
		//int surveySK;
		List<WebElement> columns = null;
		while(true){
			Thread.sleep(5000);
	WebElement storeData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/md-content/md-content[1]/md-content[1]/section")); // For traditional table
	//	WebElement storeData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/md-content/md-content[1]/md-content[2]/section"));
		List<WebElement> customerData = storeData.findElements(By.tagName("tbody"));
		Thread.sleep(3000);
		System.out.println("customerData"+" "+customerData);
		List<WebElement> tableRows = customerData.get(0).findElements(By.tagName("tr"));
		int rowsCount = tableRows.size();
		System.out.println("No of rows in UI" + "      " + rowsCount);
		for (int i = 0; i < rowsCount; i++) {
			StringBuffer str = new StringBuffer();
			 columns = tableRows.get(i).findElements(By.tagName("td"));
			str.append("[");
			for (int j = 0; j < columns.size(); j++) {
				if (j != 0) {
					str.append(",");
				}
				str.append(columns.get(j).getText());
			}
			str.append("]");
			 System.out.println(str);
			 
			 surveySK = columns.get(1).getText();
			String storeNames = columns.get(2).getText();
			dataUI.setStoreUI(surveySK, storeNames);
			 System.out.println("store Name"+" "+surveySK);
				//String railing = columns.get(3).getText();
			 String railing = columns.get(4).getText();
				dataUI.setRailUI(surveySK, railing);
				//String cooler = columns.get(2).getText();
				String cooler = columns.get(3).getText();
				dataUI.setCoolUI(surveySK, cooler);
	        	// String store = columns.get(0).getText();
				String store = columns.get(0).getText();
	        	
			 int noOfStores = Integer.parseInt(store);
		      stores = noOfStores;
		      System.out.println("Store count"+" "+stores);
		      Float mpaUI;
		      String empty = "";
		      if (channel.equals("HOME MARKET TRADITIONAL")) {
		    		for (NewTraditionalKPIs kpiEnum : NewTraditionalKPIs.values()) {
		    			int columnValue = ComparingStoreLevelDataInNewUIAndWritingXL.newTraditionalProperties.get(kpiEnum)[4];
		    			String mpa = columns.get(columnValue).getText();
		    			String total = mpa.replaceAll("[^\\d.]", "");
		    			if (total.equals(empty) || total.contains("NA")) {
		    				totalUIAfterConvertingTofloat = 0.0f;
		    				mpaUI = totalUIAfterConvertingTofloat;
		    				System.out.println("if kpi value is 0"+" "+mpaUI);
		    			}
		    			else {
		    			    totalUIAfterConvertingTofloat = Float.parseFloat(total);
		    				mpaUI = totalUIAfterConvertingTofloat;
		    				System.out.println("if kpi value is not 0"+" "+mpaUI);
		    			}
		    			//System.out.println("KPI value from UI"+" "+kpiEnum+" "+mpaUI);
		            dataUI.setKPIUI(surveySK, kpiEnum,  mpaUI);
		    		}
		    	} else {
		    		for (NewPremiseKPIs kpiEnum : NewPremiseKPIs.values()) {
		    			int columnValue = ComparingStoreLevelPremiseDataInNewUIAndWritingXL.newPremiseProperties.get(kpiEnum)[4];
		    			String mpa = columns.get(columnValue).getText();
		    			String total = mpa.replaceAll("[^\\d.]", "");
		    			
		    			if (total.equals(empty) || total.contains("NA")) {
		    				totalUIAfterConvertingTofloat = 0.0f;
		    				mpaUI = totalUIAfterConvertingTofloat;
		    			} else {
		    				totalUIAfterConvertingTofloat = Float.parseFloat(total);
		    				mpaUI = totalUIAfterConvertingTofloat;
		    			}
		    			dataUI.setNewKPIUI(surveySK, kpiEnum,  mpaUI);
		    		}

		    	}
		   
		}
		if (stores == storesFromCountry.getStoreCount()){
			break;
		}
		
	//	driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/section/md-content/div[2]/md-card/md-card-content/md-toolbar/div/div[2]/md-grid-list/md-grid-tile[5]/figure/button")).click();// next button
		driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/md-content/md-content[1]/md-content[1]/paginator/md-content/section[3]/button[4]")).click();
		}
		
		
		
		
		//dataUI.getTotalUI(storeName);
		//dataUI.getRowsCountUI();
		//dataUI.getRailUI(storeName);
		//dataUI.getCoolUI(storeName);
		//Thread.sleep(3000);
		//System.out.println("Reading Data from UI");
		return dataUI;
		
	}

}
