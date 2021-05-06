package excelLabelingMain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FoodLabel {
	
	public void addCLabel() {
		this.readTargets();
		int columnIndex = 9-6;
		try {
            FileInputStream file = new FileInputStream("companyAllerzyLabeledFood.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            int rowindex=0;
            int columnindex=0;
            XSSFSheet sheet=workbook.getSheetAt(0); //��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�) ���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
            int rows=sheet.getPhysicalNumberOfRows(); //���� �� (������)
            for(rowindex=0;rowindex<rows;rowindex++){
                XSSFRow row=sheet.getRow(rowindex); //�����д´�
                if(row !=null){
                        XSSFCell cell=row.getCell(columnIndex); //������ �д´�
                        String value="";
                        if(cell==null){ //���� ���ϰ�츦 ���� ��üũ
                            continue;
                        }else{ //Ÿ�Ժ��� ���� �б�
                            switch (cell.getCellType()){
                            case FORMULA: value=cell.getCellFormula(); break;
                            case NUMERIC: value=cell.getNumericCellValue()+""; break;
                            case STRING: value=cell.getStringCellValue()+""; break;
                            case BLANK: value=cell.getBooleanCellValue()+""; break;
                            case ERROR: value=cell.getErrorCellValue()+""; break;
                            }
                        }
                        String label = this.getLabel(value);
                        if(label==null) {
                        	System.err.println("null label: "+value);
                        }else {
//                        	System.out.println(label+"_"+value);
                        	cell.setCellValue(label+"_"+value);
                        }
//                        if(value.equals("_")) {
//                        	cell.setCellValue("err "+value);
//                        	System.out.println("ok");
//                        }
                }
            }
            
            FileOutputStream fos = new FileOutputStream("AllLabeledFood.xlsx");
            workbook.write(fos);
            fos.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
		System.out.println("��");
	}

	private String getLabel(String value) {
		for(String target: this.labelingRuleVector) {
			Scanner sc = new Scanner(target);
			if(value.contains(sc.next())) {
				return sc.next();
			}
		}
		return null;
	}

	private Vector<String> labelingRuleVector = new Vector<String>();
	private void readTargets() {
		try {
			FileReader rw = new FileReader("foodTypeLabelingRule.txt"); 
			BufferedReader br = new BufferedReader(rw);
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				this.labelingRuleVector.add(readLine);
//				System.out.println(readLine);
			}
			br.close();
		} catch (IOException e) {System.out.println(e);}
	}
}
