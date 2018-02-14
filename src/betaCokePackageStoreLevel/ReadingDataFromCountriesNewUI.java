package betaCokePackageStoreLevel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cokePackage.UIData;

public class ReadingDataFromCountriesNewUI {
	
	public UIData readingDataFromCountries(WebDriver driver,String country, String givenChannel) throws InterruptedException{
		UIData dataFromUI = new UIData();
		driver.findElement(By.id("menu-item-2")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("COUNTRIES")).click();
		Thread.sleep(3000);
		//String channel = "HOME MARKET TRADITIONAL";
		WebElement countryData ;	
		
		//WebElement countryData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content[2]/ui-view/ui-view/section/md-content/div[2]"));
		/*if (givenChannel.equals(channel)){
		// countryData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/section/md-content/div[3]")); // For Traditional
			countryData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/section/md-content[1]/div[2]/md-card/md-card-content"));
		}else {
		 countryData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/section/md-content/div[4]/md-card/md-card-content")); // For Premise
		}*/
		countryData = driver.findElement(By.xpath("html/body/layout/div/md-content/md-content/ui-view/ui-view/section/md-content[1]/div[2]/md-card/md-card-content"));
		WebElement countryTable = countryData.findElement(By.id("stores-table"));
		List<WebElement> storeData = countryTable.findElements(By.tagName("tbody"));
		for (int r=0;r<1;r++){
		List<WebElement> tableRows =  storeData.get(r).findElements(By.tagName("tr"));
		int rowscount = tableRows.size();
		System.out.println("No of rows"+"   "+rowscount);
		List<WebElement> columns = tableRows.get(0).findElements(By.tagName("td")); 
		int strCount;
		String count = columns.get(2).getText();
		System.out.println(count);
			int  storCount =Integer.parseInt(count);
			strCount = storCount;
			System.out.println("Store Count"+"  "+strCount);
			dataFromUI.setStoreCount(strCount);
		}
			dataFromUI.getStoreCount();
		return dataFromUI;
		
	}


}
