package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelTool {
	
	public static void main(String[] args) { // Example. �˾Ƽ� ������ ���
		try {
            FileInputStream file = new FileInputStream("target.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            int rowindex=0;
            int columnindex=0;
            XSSFSheet sheet=workbook.getSheetAt(0); //��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�) ���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
            int rows=sheet.getPhysicalNumberOfRows(); //���� �� (������)
            rows=10;
            for(rowindex=0;rowindex<rows;rowindex++){
                XSSFRow row=sheet.getRow(rowindex); //�����д´�
                if(row !=null){
                    int cells=row.getPhysicalNumberOfCells();  //���� �� (�÷�)
                    for(columnindex=0; columnindex<=cells; columnindex++){
                        XSSFCell cell=row.getCell(columnindex); //������ �д´�
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
                        System.out.println(rowindex+"�� �� : "+columnindex+"�� �� ����: "+value);
            			cell.setCellValue(":) " + value);
                    }
                }
            }
			FileOutputStream fos = new FileOutputStream("result.xlsx");
			workbook.write(fos);
			fos.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
	}
}
