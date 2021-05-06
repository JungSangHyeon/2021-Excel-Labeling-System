package excelLabelingMain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AlerzyLabel {
	
	public void addAllergz() {
		int columnIndex = 9;
		try {
            FileInputStream file = new FileInputStream("companyLabeledFood.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            int rowindex=0;
            int columnindex=0;
            XSSFSheet sheet=workbook.getSheetAt(0); //시트 수 (첫번째에만 존재하므로 0을 준다) 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
            int rows=sheet.getPhysicalNumberOfRows(); //행의 수 (데이터)
            for(rowindex=0;rowindex<rows;rowindex++){
                XSSFRow row=sheet.getRow(rowindex); //행을읽는다
                if(row !=null){
                        XSSFCell cell=row.getCell(columnIndex); //셀값을 읽는다
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
                        String label = this.getLabel(value);
                        if(label==null) {
                        	System.err.println("null label: "+value);
                        }else {
                        	System.out.println(label+"_"+value);
                        	cell.setCellValue(label+"_"+value);
                        }
//                        if(value.equals("_")) {
//                        	cell.setCellValue("err "+value);
//                        	System.out.println("ok");
//                        }
                }
            }
            
            FileOutputStream fos = new FileOutputStream("companyAllerzyLabeledFood.xlsx");
            workbook.write(fos);
            fos.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
		System.out.println("끝");
	}

	private String getLabel(String value) {
		String[] allerzy = { "아몬드", "우유", "대두", "밀", "닭고기", "쇠고기", "새우", "오징어", "잣", "소고기", "돼지고기", "메추리알", "토마토", "조개류",
				"난류", "호두", "복숭아", "땅콩", "게", "아황산류", "메밀", "계란" };
		String result = "";
		for(String aller: allerzy) {
			if(value.contains(aller)) {
				result+=(aller+" ");
			}
		}
		if(result.length()==0) {
			result+="null";
		}
		result+="_";
		return result;
	}

}
