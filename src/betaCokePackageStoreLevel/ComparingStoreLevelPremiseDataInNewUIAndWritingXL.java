package betaCokePackageStoreLevel;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Objects;

import cokePackage.UIData;
import cokePackage.XLData;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ComparingStoreLevelPremiseDataInNewUIAndWritingXL {
	
	public static enum NewPremiseKPIs {
		TOTAL, MPA, SOVI, REF, COMM, COLDA, COMBO
	};
    
	public static HashMap<NewPremiseKPIs, Integer[]> newPremiseProperties = new HashMap<NewPremiseKPIs, Integer[]>();

	static {
		newPremiseProperties.put(NewPremiseKPIs.TOTAL, new Integer[] { 6, 7, 8, 14, 6, 8 });
		newPremiseProperties.put(NewPremiseKPIs.MPA, new Integer[] { 16, 17, 18, 4, 7, 0 });
		newPremiseProperties.put(NewPremiseKPIs.SOVI, new Integer[]  { 19, 20, 21, 5, 8, 0 });
		newPremiseProperties.put(NewPremiseKPIs.REF, new Integer[]   { 22, 23, 24, 6, 9, 0 });
		newPremiseProperties.put(NewPremiseKPIs.COMM, new Integer[]  { 25, 26, 27, 7, 10, 0 });
		newPremiseProperties.put(NewPremiseKPIs.COLDA, new Integer[] { 28, 29, 30, 8, 11, 0 });
		newPremiseProperties.put(NewPremiseKPIs.COMBO, new Integer[] { 31, 32, 33, 9, 12, 0 });

	}
	

	public void comparingAndWritingData(String writeFilePath, WritableWorkbook writeWorkBook, UIData dataUI,
			XLData xldata, String countryUI, String channelUI, int sh) throws IOException, WriteException {

		WritableSheet writeSheet = writeWorkBook.createSheet(countryUI, sh);
		int a = 0, b = 1, c = 2, d = 3, e = 4,f = 5, h = 7, k = 10, l = 11, m = 12, n = 13, o = 14,p = 15;

		String[] headings = { "COUNTRY", "DATE","SURVEY_SK_UI", "STORE_NAME_UI", "CHANNEL", "SUB_CHANNEL", "TOTAL_UI", "ICE_XL",
				"RESULT", "DIFFERENCE", "COOLER_XL", "COOLER_UI", "COOLER_RESULT", "RAILING_XL", "RAILING_UI",
				"RAILING_RESULT", "MPA_UI", "MPA_XL", "MPA_RESULT", "SOVI_UI", "SOVI_XL", "SOVI_RESULT", "REF_UI",
				"REF_XL", "REF_RESULT", "COMM_UI", "COMM_XL", "COMM_RESULT", "PRICE_UI", "PRICE_XL", "PRICE_RESULT",
				"FRESH_UI", "FRESH_XL", "FRESH_RESULT" };

		for (int i1 = 0; i1 < headings.length; i1++) {
			Label country = new Label(i1, 0, headings[i1]);
			writeSheet.addCell(country);
		}

		String[] storesFromUI = dataUI.getNamesUI();

		for (int z1 = 0; z1 < storesFromUI.length; z1++) {

			String storeName = storesFromUI[z1];

			String[] xlValues = xldata.getICEvalues(storeName);
			if (xlValues == null) {
				Label storeNameFromUI = new Label(c, z1 + 1, storeName);
				writeSheet.addCell(storeNameFromUI);
				Label result = new Label(h, z1 + 1, "Missing in XL");
				writeSheet.addCell(result);
			} else {
				for (NewPremiseKPIs kpiEnum : NewPremiseKPIs.values()) {

					compareAndWriteForKPI(storeName, kpiEnum, z1, dataUI, xldata, writeSheet);

				}
				Label countryFromXL = new Label(a, z1 + 1, xlValues[1]);
				writeSheet.addCell(countryFromXL);
				Label dateFromXL = new Label(b, z1 + 1, xlValues[11]);
				writeSheet.addCell(dateFromXL);
				Label storeNameFromUI = new Label(c, z1 + 1, xlValues[3]);
				writeSheet.addCell(storeNameFromUI);
				Label channelFromXL = new Label(e, z1 + 1, xlValues[2]);
				writeSheet.addCell(channelFromXL);
				
				Label storeFromUI = new Label(d, z1 + 1, dataUI.getStoreUI(storeName));
				writeSheet.addCell(storeFromUI);
				
				Label subChannelFromXL = new Label(f, z1 + 1, xlValues[12]);
				writeSheet.addCell(subChannelFromXL);
				Label cooleRXL = new Label(k, z1 + 1, xlValues[10]);
				writeSheet.addCell(cooleRXL);
				Label railUI = new Label(o, z1 + 1, dataUI.getRailUI(storeName).toLowerCase());
				writeSheet.addCell(railUI);
				Label railXL = new Label(n, z1 + 1, xlValues[13].toLowerCase());
				writeSheet.addCell(railXL);
				Label coolUI = new Label(l, z1 + 1, dataUI.getCoolUI(storeName));
				writeSheet.addCell(coolUI);

				String coolerFromUI = dataUI.getCoolUI(storeName);
				String coolerFromXL = xlValues[10];
				Boolean resultOfCooler = Objects.equal(coolerFromXL, coolerFromUI);
				String coolerResult;
				if (resultOfCooler) {
					coolerResult = "Match";
				} else {
					coolerResult = "Mismatch";
				}
				Label coolResult = new Label(m, z1 + 1, coolerResult);
				writeSheet.addCell(coolResult);

				String railFromUI = dataUI.getRailUI(storeName);
				String railingXL = xlValues[13];
				String railInVisible = "INV";
				if (railFromUI.contains(railInVisible)) {

					railFromUI = railFromUI.replaceAll(railInVisible, "NOV");
				}
				Boolean resultOfRail = Objects.equal(railingXL, railFromUI);
				String railResult;
				if (resultOfRail) {
					railResult = "Match";
				} else {
					railResult = "Mismatch";
				}
				Label railingResult = new Label(p, z1 + 1, railResult);
				writeSheet.addCell(railingResult);

			}

		}

		String[] storesXL = xldata.getXLStore();
		for (int x1 = 0; x1 < storesXL.length; x1++) {
			String namesFromXL = storesXL[x1];
			boolean found = false;
			for (int y1 = 0; y1 < storesFromUI.length; y1++) {
				if (namesFromXL.equals(storesFromUI[y1])) {
					found = true;
				}
			}
			if (!found) {
				System.out.println("Store missing in UI" + namesFromXL);
			}
		}
		System.out.println("Comparing store level data and writing to XL");
	}
	
	private void compareAndWriteForKPI(String storeName, NewPremiseKPIs kpi, int z1, UIData dataUI, XLData xldata,
			WritableSheet writeSheet) throws RowsExceededException, WriteException {
		Label result;
		Float valueFromUI = null;
       
		String[] xlValues = xldata.getICEvalues(storeName);

		String valueFromXl = xlValues[newPremiseProperties.get(kpi)[3]];
		valueFromUI = dataUI.getNewPremiseKPI(kpi, storeName);
        System.out.println("valueFromUI"+" "+valueFromUI);
		
		String kPI = "TOTAL";

		if (kpi.toString().equals(kPI)) {
			Label totalFromUI = new Label(newPremiseProperties.get(kpi)[0], z1 + 1, Float.toString(valueFromUI));
			writeSheet.addCell(totalFromUI);

			Label iceFromXL = new Label(newPremiseProperties.get(kpi)[1], z1 + 1, valueFromXl);
			writeSheet.addCell(iceFromXL);
			
			float diffBetweentotalUIAndIceValueXL = Math.abs(valueFromUI - Float.parseFloat(valueFromXl));
			
			System.out.println("diffBetweentotalUIAndIceValueXL total" + " " + diffBetweentotalUIAndIceValueXL);
			
			Label diffResult = new Label(newPremiseProperties.get(kpi)[5], z1 + 1,
					String.valueOf(diffBetweentotalUIAndIceValueXL));
			writeSheet.addCell(diffResult);
			if (diffBetweentotalUIAndIceValueXL >= 0.5) {
				result = new Label(newPremiseProperties.get(kpi)[2], z1 + 1, "Mismatch");

			} else {
				result = new Label(newPremiseProperties.get(kpi)[2], z1 + 1, "Match");
			}
			writeSheet.addCell(result);
		} else {
			Label totalFromUI = new Label(newPremiseProperties.get(kpi)[0], z1 + 1, Float.toString(valueFromUI));
			writeSheet.addCell(totalFromUI);

			Label iceFromXL = new Label(newPremiseProperties.get(kpi)[1], z1 + 1, valueFromXl);
			writeSheet.addCell(iceFromXL);
			if (valueFromXl == null) {
				result = new Label(newPremiseProperties.get(kpi)[2], z1 + 1, "Missing Data in XL");
			} else {
				float diffBetweentotalUIAndIceValueXL = Math.abs(valueFromUI - Float.parseFloat(valueFromXl));

				if (diffBetweentotalUIAndIceValueXL >= 0.5) {
					result = new Label(newPremiseProperties.get(kpi)[2], z1 + 1, "Mismatch");

				} else {
					result = new Label(newPremiseProperties.get(kpi)[2], z1 + 1, "Match");
				}
			}
			writeSheet.addCell(result);
		}

	}

}
