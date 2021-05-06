package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelTool {
	
	public static void main(String[] args) { // Example. 알아서 변형해 사용
		try {
            FileInputStream file = new FileInputStream("target.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            int rowindex=0;
            int columnindex=0;
            XSSFSheet sheet=workbook.getSheetAt(0); //시트 수 (첫번째에만 존재하므로 0을 준다) 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
            int rows=sheet.getPhysicalNumberOfRows(); //행의 수 (데이터)
            rows=10;
            for(rowindex=0;rowindex<rows;rowindex++){
                XSSFRow row=sheet.getRow(rowindex); //행을읽는다
                if(row !=null){
                    int cells=row.getPhysicalNumberOfCells();  //셀의 수 (컬럼)
                    for(columnindex=0; columnindex<=cells; columnindex++){
                        XSSFCell cell=row.getCell(columnindex); //셀값을 읽는다
                        String value="";
                        if(cell==null){ //셀이 빈값일경우를 위한 널체크
                            continue;
                        }else{ //타입별로 내용 읽기
                            switch (cell.getCellType()){
                            case FORMULA: value=cell.getCellFormula(); break;
                            case NUMERIC: value=cell.getNumericCellValue()+""; break;
                            case STRING: value=cell.getStringCellValue()+""; break;
                            case BLANK: value=cell.getBooleanCellValue()+""; break;
                            case ERROR: value=cell.getErrorCellValue()+""; break;
                            }
                        }
                        System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
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
