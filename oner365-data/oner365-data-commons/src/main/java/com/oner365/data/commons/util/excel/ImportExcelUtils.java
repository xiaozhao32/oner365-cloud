package com.oner365.data.commons.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.ClassesUtil;
import com.oner365.data.commons.util.DataUtils;

/**
 * Excel导入
 *
 * @author zhaoyong
 */
public class ImportExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportExcelUtils.class);

    private static final String POINT = ".";

    private static final String NAN = "NaN";

    private static final String PREFIX_XLSX = "xlsx";

    private static final String STRING_DATE = "DATE";

    private ImportExcelUtils() {
    }

    /**
     * 获取Excel
     * @param is 输入流
     * @param extension 扩展名
     * @return Workbook
     * @throws IOException 异常
     */
    public static Workbook getWorkbook(InputStream is, String extension) throws IOException {
        // 创建工作表
        Workbook workbook;
        if (extension.contains(PREFIX_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        else {
            workbook = new HSSFWorkbook(is);
        }
        return workbook;
    }

    /***
     * 读取Excel数据
     * @param is Excel文件输入流
     * @param sheetAt Sheet所在页，默认第一页0
     * @param titleRow 标题所在行，默认首行0
     * @param extension excel后缀
     * @return ExcelData
     */
    public static <T extends Serializable> ExcelData<T> readExcel(InputStream is, Integer sheetAt, Integer titleRow,
            String extension, Class<T> clazz) {
        try (Workbook workbook = getWorkbook(is, extension)) {
            return readExcel(workbook, sheetAt, titleRow, clazz);
        }
        catch (IOException e) {
            LOGGER.error("Error readXlsExcelData: ", e);
        }
        return null;
    }

    private static <T extends Serializable> ExcelData<T> readExcel(Workbook wb, Integer sheetAt, Integer titleRow,
            Class<T> clazz) {
        ExcelData<T> excelData = new ExcelData<>();
        // 读取Sheet, 默认0
        Sheet sheet = wb.getSheetAt(sheetAt == null ? 0 : sheetAt);
        /*
         * 1、获取Sheet名称
         */
        String sheetName = sheet.getSheetName().trim();
        excelData.setSheetName(sheetName);

        /*
         * 2、获取标题行，默认首行0
         */
        Row row = sheet.getRow(titleRow);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            Object val = getCellValue(row.getCell((short) i));
            title[i] = val != null ? val.toString().trim() : PublicConstants.EMPTY;
        }
        excelData.setColumnNames(title);

        /*
         * 3、获取数据行，正文内容默认从标题行+1开始
         */
        int rowNum = sheet.getLastRowNum();
        // 由于第0行和第一行已经合并了 在这里索引从2开始
        // 正文内容应该从第二行开始,第一行为表头的标题
        List<T> dataList = new ArrayList<>();
        List<Method> methods = ClassesUtil.getSetters(clazz);
        IntStream.rangeClosed(titleRow + 1, rowNum)
            .forEach(i -> getCellDataList(dataList, sheet, methods, clazz, colNum, i, title));
        excelData.setDataList(dataList);
        return excelData;
    }

    private static <T> void getCellDataList(List<T> dataList, Sheet sheet, List<Method> methods, Class<T> clazz,
            int colNum, int i, String... title) {
        try {
            T c = clazz.getDeclaredConstructor().newInstance();
            Row row = sheet.getRow(i);
            int nullCells = checkNullCells(row);
            if (nullCells < colNum) {
                for (int j = 0; j < colNum; j++) {
                    Cell cell = row.getCell((short) j);
                    Object val = null;
                    if (cell != null) {
                        val = getCellValue(cell);
                    }
                    Object o = val != null ? val.toString().trim() : val;
                    String firstLetter = title[j].substring(0, 1).toUpperCase();
                    String setter = "set" + firstLetter + title[j].substring(1);
                    Optional<Method> setMethod = methods.stream()
                        .filter(method -> method.getName().equals(setter))
                        .findFirst();
                    if (setMethod.isPresent()) {
                        ClassesUtil.invokeMethod(c, setter, o);
                    }
                }
                dataList.add(c);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error readXlsExcelData: ", e);
        }
    }

    /***
     * 读取Cell的值
     * @param cell 单元格
     * @return Object
     */
    private static Object getCellValue(Cell cell) {
        Object value;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue().trim();
                break;
            case _NONE:
            case BLANK:
                value = PublicConstants.EMPTY;
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    value = com.oner365.data.commons.util.DateUtil.dateToString(date,
                            com.oner365.data.commons.util.DateUtil.FULL_TIME_FORMAT);
                }
                else if (String.valueOf(cell.getNumericCellValue()).contains(POINT)) {
                    DecimalFormat df = new DecimalFormat("#");
                    value = df.format(cell.getNumericCellValue());
                }
                else {
                    value = (cell + PublicConstants.EMPTY).trim();
                }
                break;
            case FORMULA:
                // 读公式计算值
                value = cell.getCellFormula();
                if (!DataUtils.isEmpty(value) && value.toString().toUpperCase().contains(STRING_DATE)) {
                    Date date = cell.getDateCellValue();
                    value = com.oner365.data.commons.util.DateUtil.dateToString(date,
                            com.oner365.data.commons.util.DateUtil.FULL_TIME_FORMAT);
                }
                else {
                    value = String.valueOf(cell.getNumericCellValue());
                    // 如果获取的数据值为非法值,则转换为获取字符串
                    if (NAN.equals(value)) {
                        value = cell.getRichStringCellValue().toString();
                    }
                }
                break;
            default:
                value = cell.toString();
        }
        return value;
    }

    /***
     * 判断空单元格数
     * @param row 行
     * @return int
     */
    private static int checkNullCells(Row row) {
        int num = 0;
        for (Cell c : row) {
            if (c.getCellType() == CellType.BLANK) {
                num++;
            }
        }
        return num;
    }

}
