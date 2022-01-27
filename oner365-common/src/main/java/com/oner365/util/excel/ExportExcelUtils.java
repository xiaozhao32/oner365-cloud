package com.oner365.util.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.util.ClassesUtil;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * Excel 导出工具
 * @author zhaoyong
 *
 */
public class ExportExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelUtils.class);

    private static final String NAME = "get";

    /**
   * 中位数
   */
  private static final int MIDDLE = 2;

    private ExportExcelUtils() {
    }

    /**
     * 创建Excel
     * @param extension 扩展名
     * @param fileSuffix 默认扩展名
     * @return Workbook
     */
    public static Workbook createWorkbook(String extension, String fileSuffix) {
        Workbook workbook;
        if(extension.contains(fileSuffix)) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    /***
     * Excel导出
     *
     * @param fileName        文件名称含后缀
     * @param columnName    表格列名
     * @param titleKey        字段名称
     * @param dataList        列表数据 List<Object>
     * @param filePath        文件目录路径
     * @param fileSuffix    默认扩展名
     */
    public static void exportExcel(String fileName, String[] columnName,
            String[] titleKey, List<?> dataList, String filePath, String fileSuffix) {
        try (Workbook workbook = exportExcel(fileName, DataUtils.getFileName(fileName), columnName, titleKey, dataList, fileSuffix);
                FileOutputStream fileOut = DataUtils.getFileOutputStream(filePath, fileName)) {
            workbook.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            LOGGER.error("IOException: ", e);
        } catch (Exception e) {
            LOGGER.error("exportExcel Exception: ", e);
        }
    }

    /***
     * Excel导出
     *
     * @param fileName        文件名称含后缀
     * @param columnName    表格列名
     * @param titleKey        字段名称
     * @param mapList        列表数据格式 {"sheetName":List<Object>}
     * @param filePath        文件目录路径
     * @param fileSuffix    默认扩展名
     */
    public static void exportExcel(String fileName, String[] columnName,
            String[] titleKey, Map<String, List<?>> mapList, String filePath, String fileSuffix) {

        String extension = DataUtils.getExtension(fileName);
        try (Workbook workbook = createWorkbook(extension, fileSuffix);
                FileOutputStream fileOut = DataUtils.getFileOutputStream(filePath, fileName)) {
            for (Map.Entry<String, List<?>> map : mapList.entrySet()) {
                String sheetName = map.getKey();
                List<?> dataList = map.getValue();
                export(workbook, sheetName, columnName, titleKey, dataList);
            }

            workbook.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            LOGGER.error("IOException: ", e);
        } catch (Exception e) {
            LOGGER.error("exportExcel Exception: ", e);
        }
    }

    private static Workbook exportExcel(String fileName, String sheetName, String[] columnName,
            String[] titleKey, List<?> objectList, String fileSuffix) {
        String extension = DataUtils.getExtension(fileName);

        // 创建工作表
        Workbook workbook;
        if(extension.contains(fileSuffix)) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        export(workbook, sheetName, columnName, titleKey, objectList);
        return workbook;
    }

    private static void adaptor(Sheet sheet, int columnNum) {
        // 让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            Row currentRow = sheet.getRow(sheet.getLastRowNum());
            columnWidth = getColumnWidth(currentRow,colNum,columnWidth);
            sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
        }
    }

    /***
     * Excel导出
     * @param sheetName        文件名称
     * @param titleKey        表格列名称
     * @param columnName    字段名称
     * @param objectList    列表数据
     */
    private static void export(Workbook workbook, String sheetName, String[] columnName,
            String[] titleKey, List<?> objectList) {
        try {
            Sheet sheet = workbook.createSheet(sheetName);

            // 设置标题单元格和内容单元格样式
            CellStyle titleCellStyle = buildCellStyleForTitle(workbook);
            titleCellStyle.setFont(buildFont(workbook));
            CellStyle contextStyle = buildCellStyleForContent(workbook);
            contextStyle.setFont(buildFont(workbook));

            // 定义所需列数
            int columnNum = columnName.length;
            Row row = sheet.createRow(0);

            // 设置标题
            for (int i = 0; i < columnNum; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(titleCellStyle);
            }

            // 设置内容
            for (int i = 0; i < objectList.size(); i++) {
                row = sheet.createRow(i+1);
                Object object = objectList.get(i);
                // 获取实体类的所有属性，返回Field数组
                Field[] field = object.getClass().getDeclaredFields();
                String[] pName = new String[field.length];
                for (int j = 0; j < field.length; j++) {
                    pName[j] = field[j].getName();
                }

                for (int n = 0; n < titleKey.length; n++) {
                    for (int j = 0; j < pName.length; j++) {
                        String[] str = titleKey[n].split("\\.");
                        String name = str[0];
                        if (name.equals(pName[j])) {
                            /* 将属性名字第一个字符串设置成大写 */
                            char[] c = name.toCharArray();
                            char ch = Character.toUpperCase(c[0]);
                            c[0] = ch;
                            name = new String(c);
                            // 获取属性的类型
                            Type type = field[j].getGenericType();

                            //判断是否为基本类型或Bean型
                            if (type.equals(String.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                String value = (String) m.invoke(object);
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(Integer.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                Integer value = (Integer) m.invoke(object);
                                if (value == null) {
                                    value = 0;
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(BigDecimal.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                BigDecimal value = (BigDecimal) m.invoke(object);
                                double d = 0d;
                                if (value != null) {
                                    d = value.doubleValue();
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(d);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(Double.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                Double value = (Double) m.invoke(object);
                                if (value == null) {
                                    value = 0.0;
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(Long.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                Long value = (Long) m.invoke(object);
                                if (value == null) {
                                    value = 0L;
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(Timestamp.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                String dateString = DataUtils.getString(m.invoke(object));
                                String value = StringUtils.EMPTY;
                                if (!DataUtils.isEmpty(dateString)) {
                                    value = DateUtil.dateToString(DateUtil.stringToDate(dateString, DateUtil.FULL_TIME_FORMAT), DateUtil.FULL_TIME_FORMAT);
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if(type.equals(Date.class)){
                                Method m = object.getClass().getMethod(NAME + name);
                                Date d = (Date) m.invoke(object);
                                String value = StringUtils.EMPTY;
                                if (d != null) {
                                    value = DateUtil.dateToString(d, DateUtil.FULL_TIME_FORMAT);
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(java.time.LocalDate.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                java.time.LocalDate d = (java.time.LocalDate) m.invoke(object);
                                String value = StringUtils.EMPTY;
                                if (d != null) {
                                    value = d.toString();
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else if (type.equals(java.time.LocalDateTime.class)) {
                                Method m = object.getClass().getMethod(NAME + name);
                                LocalDateTime d = (LocalDateTime) m.invoke(object);
                                String value = StringUtils.EMPTY;
                                if (d != null) {
                                    value = DateUtil.dateToString(DateUtil.localDateTimeToDate(d), DateUtil.FULL_TIME_FORMAT);
                                }
                                Cell cell = row.createCell(n);
                                cell.setCellValue(value);
                                cell.setCellStyle(contextStyle);
                            } else {
                                if (!ClassesUtil.isAtomic(type.getClass())) {
                                    String value = String.valueOf(invoke(object, titleKey[n]));
                                    Cell cell = row.createCell(n);
                                    cell.setCellValue(value);
                                    cell.setCellStyle(contextStyle);
                                } else {
                                    Cell cell = row.createCell(n);
                                    cell.setCellValue("--");
                                    cell.setCellStyle(contextStyle);
                                }
                            }
                            break;
                        }
                    }
                }

            }

            adaptor(sheet, columnNum);
        } catch (Exception e) {
            LOGGER.error("Error exportExcel: ", e);
        }
    }

    /**
     * 反射获取对象
     * @param object 对象
     * @param arrayString 字符串
     * @return Object
     */
    private static Object invoke(Object object, String arrayString) {
        try {
            String[] str = arrayString.split("\\.");
            String s1 = DataUtils.builderName(StringUtils.substringBefore(arrayString, "."));
            String s2 = DataUtils.builderName(StringUtils.substringAfter(arrayString, "."));
            Method m1 = object.getClass().getMethod(NAME + s1);
            Object object1 = m1.invoke(object);
            if (object1 == null) {
                return null;
            }

            if (str.length == MIDDLE) {
                Method m2 = object1.getClass().getMethod(NAME + s2);
                return m2.invoke(object1);
            } else {
                // 获取子对象
                return invoke(object1, s2);
            }
        } catch (Exception e) {
            LOGGER.error("invoke Exception: ", e);
        }
        return null;
    }

    /***
     * 根据单元格内容计算单元格宽度
     * @param currentRow    当前行
     * @param colNum        当前列号
     * @param columnWidth    单元格宽度
     * @return int
     */
    private static int getColumnWidth(Row currentRow,int colNum,int columnWidth){
        if (currentRow.getCell(colNum) != null) {
            // 取得当前的单元格
            Cell currentCell = currentRow.getCell(colNum);
            // 如果当前单元格类型为字符串
            if (CellType.STRING.equals(currentCell.getCellType())) {
                int length = currentCell.getStringCellValue().getBytes(Charset.defaultCharset()).length;
                if (columnWidth < length) {
                    // 将单元格里面值大小作为列宽度
                    columnWidth = length;
                }
            }
        }
        return columnWidth;
    }

    /***
     * 构造单元格样式-内容
     * @param workbook    工作薄
     * @return    单元格样式
     */
    private static CellStyle buildCellStyleForContent(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // 设置字体:
        Font font = buildFont(workbook);
        style.setFont(font);

        buildStyle(style);

        // 设置自动换行
        style.setWrapText(false);
        style.setLocked(false);
        return style;
    }

    /***
     * 构造单元格样式-标题
     * @param workbook    工作薄
     * @return 单元格样式
     */
    private static CellStyle buildCellStyleForTitle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // 设置背景色:
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.forInt(FillPatternType.SOLID_FOREGROUND.getCode()));

        buildStyle(style);

        // 设置自动换行:
        style.setWrapText(false);
        style.setLocked(true);
        return style;
    }

    public static void buildStyle(CellStyle style) {
        // 设置边框:
        style.setBorderBottom(BorderStyle.valueOf(BorderStyle.THIN.getCode()));
        style.setBorderLeft(BorderStyle.valueOf(BorderStyle.THIN.getCode()));
        style.setBorderTop(BorderStyle.valueOf(BorderStyle.THIN.getCode()));
        style.setBorderRight(BorderStyle.valueOf(BorderStyle.THIN.getCode()));

        // 设置边框颜色:
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        // 设置居中:
        style.setAlignment(HorizontalAlignment.forInt(HorizontalAlignment.CENTER.getCode()));
        style.setVerticalAlignment(VerticalAlignment.forInt(VerticalAlignment.CENTER.getCode()));
    }

    /**
     * 设置字体
     * @param workbook Workbook
     * @return Font
     */
    private static Font buildFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("宋体");
        return font;
    }

}
