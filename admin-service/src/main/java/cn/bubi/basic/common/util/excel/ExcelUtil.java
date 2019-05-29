package cn.bubi.basic.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import cn.bubi.basic.common.base.BaseController;
import cn.bubi.basic.common.base.BaseOpMsg;
import cn.bubi.basic.common.util.ParseUtil;
import cn.bubi.basic.common.util.SpringContextUtil;
import cn.bubi.basic.common.util.StringLowUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bubi.basic.common.annotation.ExcelResources;
import cn.bubi.basic.common.config.sysUtil.SysCacheUtil;
import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.status.StatusCode;
import cn.bubi.basic.modules.sys.entity.SysArea;
import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.entity.SysOffice;

/**
 * @Author: JoinHan
 * @Date: Created in 15:42 2018/3/28
 * @Modified By：
 */
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    static {
        ConvertUtils.register(new Converter() {

            @Override
            public Object convert(Class type, Object value) {

                try {
                    if (!StringLowUtils.isEmpty(value.toString())) {
                        String[] dateStr = {"yyyy-MM-dd", "yyyy.MM.dd", "yyyy.MM", "yyyy-MM" };
                        return DateUtils.parseDate(value.toString(), dateStr);
                    }
                } catch(ParseException e) {
                    logger.debug(e.getMessage());
                }
                return null;
            }
        }, Date.class);
    }

    /**
     * 把查询出来的列表数据 转换成Excel
     *
     * @param os
     *            输出流
     * @param list
     *            对象列表
     * @param clz
     *            对象类型
     */
    public static void exportList2ExcelByClass(ServletOutputStream os, List list, Class clz) {

        try {
            Workbook wb = new HSSFWorkbook();
            // TODO 添加单元格基础样式
            Map<String, CellStyle> styles = createStyles(wb);
            // 数据写到第一页
            Sheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(13);
            sheet.setDefaultRowHeight((short) ( 2 * 256));
            Row r = sheet.createRow(0);
            List<ExcelHeader> headerList = getHeaderList(clz);
            Collections.sort(headerList);

            // 取数据字典缓存 并处理
            Map<String, List<SysDict>> dictMap = SysCacheUtil.getInstance().getDictMapCache();
            Map<String, Map<Object, Object>> exportDataMap = SysCacheUtil.getInstance().exportMapCache();
            Map<String, Map<String, String>> dictDeal = new HashMap<>();
            List<ExcelHeader> headers = new ArrayList<>();
            for (ExcelHeader eh : headerList) {
                if (eh.isExport()) {
                    headers.add(eh);
                }
            }
            boolean[] ifDict = new boolean[headers.size()];// 存储所有导出字段是否是字典字段

            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                // 判断数据类型
                String type = headers.get(i).getType();
                if (!"".equals(type)) {
                    if ("1".equals(type)) {// 数据字典类型 提前存储转换数据
                        ifDict[i] = true;
                        List<SysDict> dictTemp = dictMap.get(headers.get(i).getDictionary());
                        if (dictTemp != null && !( dictTemp.isEmpty())) {
                            Map<String, String> map = new HashMap<>();
                            for (SysDict d : dictTemp) {
                                map.put(d.getValue(), d.getLabel());
                            }
                            dictDeal.put(headers.get(i).getDictionary(), map);
                        }
                    }
                }
                r.createCell(i).setCellStyle(styles.get("header"));
                r.getCell(i).setCellValue(headers.get(i).getTitle());
                sheet.autoSizeColumn(i, true);
            }

            // 写数据
            Object obj = null;
            Map<String, Integer> maxlength = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = list.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    String object = BeanUtils.getProperty(obj, getMethodName(headers.get(j)));
                    String value = "";
                    if (object != null) {
                        if (ifDict[j]) {
                            value = dictDeal.get(headers.get(j).getDictionary()).get(object);
                        } else {
                            // 判断数据时间类型格式
                            if ("2".equals(headers.get(j).getType())) {
                                SimpleDateFormat sdf = new SimpleDateFormat(headers.get(j).getDateFormat());
                                Date date = ParseUtil.getDate(object, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                                value = sdf.format(date);
                            } else if ("3".equals(headers.get(j).getType())) {// 判断是否需要将值转成其他表字段对应的值
                                for (String sql : headers.get(j).getSqlList()) {
                                    Map<Object, Object> dataMap = exportDataMap.get(sql);// 获取缓存数据
                                    if (dataMap.get(object) != null) {
                                        value = dataMap.get(object) + "";
                                    } else {
                                        value = object;
                                    }
                                }
                            } else if ("4".equals(headers.get(j).getType())) {// 地区类型
                                SysArea sysArea = SysCacheUtil.getInstance().getAreaIdMapCache().get(object);
                                if (sysArea != null) {
                                    value = sysArea.getName();
                                }
                            } else if ("5".equals(headers.get(j).getType())) {// 机构类型
                                SysOffice sysOffice = SysCacheUtil.getInstance().getOfficeIdMapCache().get(object);
                                if (sysOffice != null) {
                                    value = sysOffice.getName();
                                }
                            } else {
                                value = object;
                            }
                        }
                    }
                    r.createCell(j).setCellStyle(styles.get("data"));
                    if (maxlength.get(j + "") == null) {
                        sheet.setColumnWidth(j, ( value.toString().getBytes().length + 3) * 256);
                        maxlength.put(j + "", ( value.toString().getBytes().length + 3) * 256);
                    } else {
                        int max = maxlength.get(j + "");
                        if (( ( value.toString().getBytes().length + 3) * 256) > max) {
                            sheet.setColumnWidth(j, ( value.toString().getBytes().length + 3) * 256);
                            maxlength.put(j + "", ( value.toString().getBytes().length + 3) * 256);
                        }
                    }
                    r.getCell(j).setCellValue(value);
                }
            }
            wb.write(os);
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
    }

    /**
     * 根据标题获取相应的方法名称
     *
     * @param eh
     * @return
     */
    private static String getMethodName(ExcelHeader eh) {

        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }

    private static Map<String, CellStyle> createStyles(Workbook wb) {

        Map<String, CellStyle> styles = new HashMap<>();

        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);// 居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        styles.put("header", style);

        CellStyle dataStyle = wb.createCellStyle();
        // dataStyle.cloneStyleFrom(style);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put("data", dataStyle);
        return styles;
    }

    private static List<ExcelHeader> getHeaderList(Class clz) {

        List<ExcelHeader> headers = new ArrayList<>();
        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            String mn = m.getName();
            if (mn.startsWith("get")) {
                if (m.isAnnotationPresent(ExcelResources.class)) {
                    ExcelResources er = m.getAnnotation(ExcelResources.class);
                    ExcelHeader eh = new ExcelHeader();
                    eh.setType(er.type());
                    eh.setDateFormat(er.dateFormat());
                    eh.setDictionary(er.dictionary());
                    eh.setExport(er.ifExport());
                    eh.setImport(er.ifImport());
                    eh.setOrder(er.order());
                    eh.setMethodName(mn);
                    List<String> stringList = new ArrayList<>();
                    List<Boolean> booleanList = new ArrayList<>();
                    for (ExcelResources.List list : er.sqlList()) {
                        if (!"".equals(list.sql())) {// 预先缓存sql数据
                            SysCacheUtil.getInstance().importMapCache(list.sql());
                        }
                        stringList.add(list.sql());
                        booleanList.add(list.ifCash());
                    }
                    eh.setSqlList(stringList);
                    eh.setSqlCash(booleanList);
                    eh.setTitle(er.title());
                    headers.add(eh);
                }
            }
        }
        return headers;
    }

    /**
     * 读取文件excel文件的 第sheet 页 的readLine 至 tailLine 行列表数据
     *
     * @param file
     *            excel 文件
     * @param clz
     *            class
     * @param sheetPage
     *            sheet页序
     * @param readLine
     *            起始行 null 第0行开始
     * @param tailLine
     *            结束行 null 读到文件尾部
     * @param ifWrite
     *            是否边读边写入
     * @param writeClass
     *            写入类名称
     * @return
     */
    public static BaseOpMsg importExcel2ListByClass(File file, Class clz, Integer sheetPage, Integer readLine,
            Integer tailLine, Boolean ifWrite, String writeClass) {

        BaseOpMsg result = new BaseOpMsg();
        Workbook wb = null;
        List<Object> objs = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
            Sheet sheet = wb.getSheetAt(sheetPage);
            if (readLine == null) {
                readLine = 0;
            }
            if (tailLine == null) {
                tailLine = sheet.getLastRowNum();
            }

            Row row = sheet.getRow(readLine);
            // 从class 类里面读取属性 只取有用的属性导入系统
            List<ExcelHeader> headerList = getHeaderList(clz);
            // 取数据字典缓存
            Map<String, List<SysDict>> dictMap = SysCacheUtil.getInstance().getDictMapCache();
            Map<String, Map<String, String>> dictDeal = new HashMap<>();
            // 取出上传文件的头信息
            Map<Integer, ExcelHeader> fileHeader = getHeaderMap(row, headerList, dictDeal, dictMap);
            if (fileHeader == null || fileHeader.size() <= 0) {
                result.setCode(StatusCode.UPLOAD_FILE_FAILURE);
                result.setStatus("error");
                result.setMsg("文件列解析出错！");
                return result;
            }

            // 获取导入预先缓存数据
            Map<String, Map<Object, Object>> importDataMap = SysCacheUtil.getInstance().importMapCache();
            objs = new ArrayList<>();
            for (int i = readLine + 1; i <= tailLine; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                Object obj = clz.newInstance();
                if (row != null && row.getFirstCellNum() <= 0) {
                    for (Cell c : row) {
                        int ci = c.getColumnIndex();
                        ExcelHeader headInfo = fileHeader.get(ci);
                        if (headInfo == null) {
                            continue;
                        }

                        String cellValu = null;

                        /*
                         * CellType 类型 值 CELL_TYPE_NUMERIC 数值型 0 CELL_TYPE_STRING 字符串型 1
                         * CELL_TYPE_FORMULA 公式型 2 CELL_TYPE_BLANK 空值 3 CELL_TYPE_BOOLEAN 布尔型 4
                         * CELL_TYPE_ERROR 错误 5
                         * _NONE(-1), NUMERIC(0), STRING(1), FORMULA(2), BLANK(3),BOOLEAN(4),ERROR(5);
                         */
                        switch (c.getCellTypeEnum()) {
                            case BLANK :
                                cellValu = null;
                                break;
                            case BOOLEAN :
                                cellValu = String.valueOf(c.getBooleanCellValue());
                                break;
                            case FORMULA :
                                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                                if (evaluator.evaluate(c) == null) {
                                    continue;
                                }
                                if (evaluator.evaluate(c).getCellTypeEnum() == CellType.NUMERIC) {
                                    cellValu = evaluator.evaluate(c).getNumberValue() + "";
                                } else {
                                    cellValu = evaluator.evaluate(c).getStringValue();
                                }
                                break;
                            case NUMERIC :
                                if (DateUtil.isCellDateFormatted(c)) {
                                    Date theDate = c.getDateCellValue();
                                    if ("2".equals(headInfo.getType())) {
                                        SimpleDateFormat sdf = new SimpleDateFormat(headInfo.getDateFormat());
                                        cellValu = sdf.format(theDate);
                                    } else {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        cellValu = sdf.format(theDate);
                                    }
                                } else {
                                    cellValu = NumberToTextConverter.toText(c.getNumericCellValue());
                                }
                                break;
                            case STRING :
                                cellValu = c.getStringCellValue();
                                break;
                            default :
                                cellValu = null;
                                break;
                        }

                        logger.debug("单元格内容：{}", cellValu);

                        if ("1".equals(headInfo.getType())) {// 数据字典
                            cellValu = dictDeal.get(headInfo.getDictionary()).get(cellValu);
                        } else if ("3".equals(headInfo.getType())) {// 判断是否需要将值转成其他表字段对应的值
                            if (cellValu != null) {
                                for (int sqlIndex = 0; sqlIndex < headInfo.getSqlList().size(); sqlIndex++) {
                                    String sql = headInfo.getSqlList().get(sqlIndex);
                                    if (headInfo.getSqlCash().get(sqlIndex)) {// 说明系统预缓存了
                                        Map<Object, Object> dataMap = importDataMap.get(sql);// 获取缓存数据
                                        if (dataMap.get(cellValu.trim()) != null) {
                                            cellValu = dataMap.get(cellValu).toString();
                                            break;
                                        }
                                    } else {
                                        Map<String, Long> importMap = SysCacheUtil.getInstance().selectSqlMap(sql);
                                        if (importMap.get(cellValu.trim()) != null) {
                                            cellValu = importMap.get(cellValu).toString();
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if ("4".equals(headInfo.getType())) {// 地区类型
                            // 所有地区替换成用户下的地区
                            // List<SysArea> sysAreaList = SysCacheUtil.getInstance().getAreaListCache();
                            List<SysArea> sysAreaList = UserUtil.getInstance().getUserInfo().getUnderAreaList();
                            boolean isArea = false;
                            if (cellValu != null) {
                                for (SysArea sysArea : sysAreaList) {
                                    if (cellValu.trim().equals(sysArea.getName())) {
                                        cellValu = sysArea.getId() + "";
                                        isArea = true;
                                        break;
                                    }
                                }
                            }
                            if (!isArea) {
                                cellValu = null;
                            }
                        } else if ("5".equals(headInfo.getType())) {// 机构类型
                            // 所有机构替换成用户下的机构
                            // List<SysOffice> sysOfficeList = SysCacheUtil.getInstance().getOfficeListCache();
                            boolean isOffice = false;
                            List<SysOffice> sysOfficeList = UserUtil.getInstance().getUserInfo().getUnderOfficeList();
                            if (cellValu != null) {
                                for (SysOffice sysOffice : sysOfficeList) {

                                    if (cellValu.trim().equals(sysOffice.getName())) {
                                        cellValu = sysOffice.getId() + "";
                                        isOffice = true;
                                        break;
                                    }
                                }
                            }
                            if (!isOffice) {
                                cellValu = null;
                            }
                        }

                        String mn = headInfo.getMethodName();// 获取方法名
                        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
                        BeanUtils.copyProperty(obj, mn, cellValu);// 将值付给bean
                    }
                    if (ifWrite) {
                        BaseController bc = (BaseController) SpringContextUtil.getBean(writeClass);
                        String info = bc.validateObject(obj, UserUtil.getInstance().getUser());
                        if (info != null) {
                            result.setCode(StatusCode.UPLOAD_FILE_FAILURE);
                            result.setStatus("error");
                            result.setMsg("第" + ( i + 1) + "行" + info);
                            return result;
                        }
                    }
                    objs.add(obj);
                }
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            return null;
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch(Exception e) {
                logger.debug(e.getMessage());
            }

        }
        result.setCode(StatusCode.UPLOAD_FILE_SUCCESS);
        result.setStatus("success");
        result.setList(objs);
        result.setMsg("解析成功");
        return result;

    }

    private static Map<Integer, ExcelHeader> getHeaderMap(Row titleRow, List<ExcelHeader> headerList,
            Map<String, Map<String, String>> dictDeal, Map<String, List<SysDict>> dictMap) {

        Map<Integer, ExcelHeader> maps = new HashMap<>();
        for (Cell c : titleRow) {
            c.setCellType(CellType.STRING);
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headerList) {
                if (eh.isImport()) {
                    if (eh.getTitle().equals(title.trim())) {
                        eh.setMethodName(eh.getMethodName().replace("get", ""));
                        maps.put(c.getColumnIndex(), eh);
                        if ("1".equals(eh.getType())) {
                            List<SysDict> dictTemp = dictMap.get(eh.getDictionary());
                            if (dictTemp != null && dictTemp.size() > 0) {
                                Map<String, String> map = new HashMap<>();
                                for (SysDict d : dictTemp) {
                                    map.put(d.getLabel(), d.getValue());
                                }
                                dictDeal.put(eh.getDictionary(), map);
                            }
                        }
                    }
                }
            }
        }
        return maps;
    }
}
