package org.eq.basic.common.config.sysUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.service.SysAreaService;
import org.eq.basic.modules.sys.service.SysDictService;
import org.eq.basic.modules.sys.service.SysOfficeService;

/**
 * 系统启动后 将部分数据加入到系统缓存中：目前加入的数据是 字典数据 机构类型 机构 角色
 *
 * @author JoinHan on 2017/06/01
 */
@Component
public class SysCacheUtil {

    @Autowired
    SysDictService dictService;

    @Autowired
    SysOfficeService sysOfficeService;

    @Autowired
    SysAreaService sysAreaService;

    @Autowired
    private EHcacheUtil cacheUtil;

    private static SysCacheUtil sysCacheUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Map<type,List<SysDict>>
    private String CACHE_DICT_MAP = "dictDataMap";

    // List<SysDict>
    private String CACHE_DICT_LIST = "dictDataList";

    private String CACHE_OFFICE_CODE_MAP = "officeCodeDataMap";

    private String CACHE_OFFICE_ID_MAP = "officeIdDataMap";

    private String CACHE_OFFICE_LIST = "officeDataList";

    private String CACHE_ROLE_LIST = "roleDataList";

    private String CACHE_AREA_CODE_MAP = "areaCodeDataMap";

    private String CACHE_AREA_ID_MAP = "areaIdDataMap";

    private String CACHE_AREA_LIST = "areaDataList";

    private String IMPORT_CACHE_MAP = "importCacheMap";

    private String EXPORT_CACHE_MAP = "exportCacheMap";

    public synchronized static SysCacheUtil getInstance() {

        if (sysCacheUtil == null) {
            sysCacheUtil = (SysCacheUtil) SpringContextUtil.getBean("sysCacheUtil");
        }
        return sysCacheUtil;
    }

    public void initCache() {

        // 缓存数据字典
        this.initDictCache();
        // 缓存系统机构部门信息
        this.initOfficeCache();
        // 缓存系统地区信息
        this.initAreaCache();
    }

    private Map<String, SysOffice> initOfficeCache() {

        this.logger.debug("put offices into cacahe");
        Map<String, SysOffice> officeCodeMap = new HashMap<>();
        Map<String, SysOffice> officeIdMap = new HashMap<>();
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        List<SysOffice> officesList = this.sysOfficeService.findListByRecord(sysOffice);
        for (SysOffice o : officesList) {
            officeCodeMap.put(o.getCode(), o);
            officeIdMap.put(o.getId() + "", o);
        }
        if (this.cacheUtil == null) {
            this.cacheUtil = EHcacheUtil.getInstance();
        }
        this.cacheUtil.put(this.CACHE_OFFICE_CODE_MAP, officeCodeMap);
        this.cacheUtil.put(this.CACHE_OFFICE_ID_MAP, officeIdMap);
        this.cacheUtil.put(this.CACHE_OFFICE_LIST, officesList);
        return officeCodeMap;
    }

    private Map<String, List<SysDict>> initDictCache() {

        this.logger.debug("put dicts into cacahe");
        // Map<Key,List<SysDict>> 格式的数据字典存放到 系统缓存 key=dictType
        Map<String, List<SysDict>> dictMap = new HashMap<>();
        List<SysDict> dictList = this.dictService.findListByRecord(new SysDict());
        for (SysDict d : dictList) {
            List<SysDict> dicts = null;
            if (d.getDescription() != null) {
                dicts = dictMap.get(d.getType());
                if (dicts == null) {
                    dicts = new ArrayList<>();
                }
                dicts.add(d);
                dictMap.put(d.getType(), dicts);
            }
        }
        if (this.cacheUtil == null) {
            this.cacheUtil = EHcacheUtil.getInstance();
        }
        this.cacheUtil.put(this.CACHE_DICT_MAP, dictMap);
        this.cacheUtil.put(this.CACHE_DICT_LIST, dictList);
        return dictMap;
    }

    private Map<String, SysArea> initAreaCache() {

        this.logger.debug("put area into cacahe");
        Map<String, SysArea> areaCodeMap = new HashMap<>();
        Map<String, SysArea> areaIdMap = new HashMap<>();
        List<SysArea> areaList = this.sysAreaService.findListByRecord(new SysArea());
        for (SysArea d : areaList) {
            areaCodeMap.put(d.getCode(), d);
            areaIdMap.put(d.getId() + "", d);
        }
        if (this.cacheUtil == null) {
            this.cacheUtil = EHcacheUtil.getInstance();
        }
        this.cacheUtil.put(this.CACHE_AREA_CODE_MAP, areaCodeMap);
        this.cacheUtil.put(this.CACHE_AREA_ID_MAP, areaIdMap);
        this.cacheUtil.put(this.CACHE_AREA_LIST, areaList);
        return areaCodeMap;
    }

    public Map<String, SysOffice> getOfficeCodeMapCache() {

        Map<String, SysOffice> officeMap = null;
        try {
            officeMap = (Map<String, SysOffice>) this.cacheUtil.get(this.CACHE_OFFICE_CODE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (officeMap == null) {
            officeMap = this.initOfficeCache();
        }
        return officeMap;
    }

    public Map<String, SysOffice> getOfficeIdMapCache() {

        Map<String, SysOffice> officeMap = null;
        try {
            officeMap = (Map<String, SysOffice>) this.cacheUtil.get(this.CACHE_OFFICE_ID_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (officeMap == null) {
            this.initOfficeCache();
        }
        return (Map<String, SysOffice>) this.cacheUtil.get(this.CACHE_OFFICE_ID_MAP);
    }

    public List<SysOffice> getOfficeListCache() {

        List<SysOffice> officeList = null;
        try {
            officeList = (List<SysOffice>) this.cacheUtil.get(this.CACHE_OFFICE_LIST);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (officeList == null) {
            this.initOfficeCache();
        }
        return (List<SysOffice>) this.cacheUtil.get(this.CACHE_OFFICE_LIST);
    }

    public Map<String, List<SysDict>> getDictMapCache() {

        Map<String, List<SysDict>> dictMap = null;
        try {
            dictMap = (Map<String, List<SysDict>>) this.cacheUtil.get(this.CACHE_DICT_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (dictMap == null) {
            dictMap = this.initDictCache();
        }
        return dictMap;
    }

    public List<SysDict> getDictListCache() {

        List<SysDict> dictList = null;
        try {
            dictList = (List<SysDict>) this.cacheUtil.get(this.CACHE_DICT_LIST);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (dictList == null) {
            dictList = this.dictService.findListByRecord(new SysDict());
        }
        return dictList;
    }

    public Map<String, SysArea> getAreaCodeMapCache() {

        Map<String, SysArea> areaCodeMap = null;
        try {
            areaCodeMap = (Map<String, SysArea>) this.cacheUtil.get(this.CACHE_AREA_CODE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (areaCodeMap == null) {
            areaCodeMap = this.initAreaCache();
        }
        return areaCodeMap;
    }

    public Map<String, SysArea> getAreaIdMapCache() {

        Map<String, SysOffice> areaIdMap = null;
        try {
            areaIdMap = (Map<String, SysOffice>) this.cacheUtil.get(this.CACHE_AREA_ID_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (areaIdMap == null) {
            this.initAreaCache();
        }
        return (Map<String, SysArea>) this.cacheUtil.get(this.CACHE_AREA_ID_MAP);
    }

    public List<SysArea> getAreaListCache() {

        List<SysArea> areaList = null;
        try {
            areaList = (List<SysArea>) this.cacheUtil.get(this.CACHE_AREA_LIST);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (areaList == null) {
            this.initAreaCache();
        }
        return (List<SysArea>) this.cacheUtil.get(this.CACHE_AREA_LIST);
    }

    public void refreshDict() {

        this.logger.debug("refresh dicts into cacahe");
        this.initDictCache();
    }

    public void refreshArea() {

        this.logger.debug("refresh area into cacahe");
        this.initAreaCache();
    }

    public void refreshOffice() {

        this.logger.debug("refresh office into cacahe");
        this.initOfficeCache();
    }

    /*
     * public void refreshOffice(){
     * logger.debug("refresh dicts into cacahe");
     * initOfficeCache();
     * }
     */

    public void importMapCache(String sql) {

        Map<String, Map<Object, Object>> importDictMap = null;
        Map<String, Map<Object, Object>> exportDictMap = null;
        try {
            importDictMap = (Map<String, Map<Object, Object>>) this.cacheUtil.get(this.IMPORT_CACHE_MAP);
            exportDictMap = (Map<String, Map<Object, Object>>) this.cacheUtil.get(this.EXPORT_CACHE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (importDictMap == null) {
            importDictMap = new HashMap<>();
        }
        if (exportDictMap == null) {
            exportDictMap = new HashMap<>();
        }
        Map<Object, Object> dataMap = importDictMap.get(sql);
        Map<Object, Object> exportDateMap = exportDictMap.get(sql);
        if (dataMap == null || dataMap.isEmpty() || dataMap.containsKey("null")) {
            dataMap = new HashMap<>();
            exportDateMap = new HashMap<>();
            this.logger.info("系统缓存没有 " + sql + " 数据，从数据库查询数据");
            UserUtil.getInstance().getUserInfo();

            List<Map> tableDataList = this.dictService.exceImportSql(sql);
            if (tableDataList != null) {
                for (Map m : tableDataList) {
                    dataMap.put(( (String) m.get("name")).trim(), m.get("id"));
                    exportDateMap.put(m.get("id"), m.get("name"));
                }
            }
            importDictMap.put(sql, dataMap);
            exportDictMap.put(sql, exportDateMap);
            this.cacheUtil.put(this.IMPORT_CACHE_MAP, importDictMap);
            this.cacheUtil.put(this.EXPORT_CACHE_MAP, exportDictMap);
        }
    }

    public Map<String, Map<Object, Object>> importMapCache() {

        Map<String, Map<Object, Object>> importDictMap = null;
        try {
            importDictMap = (Map<String, Map<Object, Object>>) this.cacheUtil.get(this.IMPORT_CACHE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，无法从数据库查询数据");
        }
        return importDictMap;
    }

    public Map<String, Map<Object, Object>> exportMapCache() {

        Map<String, Map<Object, Object>> exportDictMap = null;
        try {
            exportDictMap = (Map<String, Map<Object, Object>>) this.cacheUtil.get(this.EXPORT_CACHE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，无法从数据库查询数据");
        }
        return exportDictMap;
    }

    public Map<String, Map<Long, String>> exportMapCache(String sql) {

        Map<String, Map<String, Long>> importDictMap = null;
        Map<String, Map<Long, String>> exportDictMap = null;
        try {
            importDictMap = (Map<String, Map<String, Long>>) this.cacheUtil.get(this.IMPORT_CACHE_MAP);
            exportDictMap = (Map<String, Map<Long, String>>) this.cacheUtil.get(this.EXPORT_CACHE_MAP);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (importDictMap == null) {
            importDictMap = new HashMap<>();
        }
        if (exportDictMap == null) {
            exportDictMap = new HashMap<>();
        }
        Map<String, Long> dataMap = importDictMap.get(sql);
        Map<Long, String> exportDateMap = exportDictMap.get(sql);
        if (exportDateMap == null) {
            dataMap = new HashMap<>();
            exportDateMap = new HashMap<>();
            this.logger.info("系统缓存没有 " + sql + " 数据，从数据库查询数据");
            UserUtil.getInstance().getUserInfo();

            List<Map> tableDataList = this.dictService.exceImportSql(sql);
            if (tableDataList != null) {
                for (Map m : tableDataList) {
                    dataMap.put((String) m.get("name"), (Long) m.get("id"));
                    exportDateMap.put((Long) m.get("id"), (String) m.get("name"));
                }
            }
            importDictMap.put(sql, dataMap);
            exportDictMap.put(sql, exportDateMap);
            this.cacheUtil.put(this.IMPORT_CACHE_MAP, importDictMap);
            this.cacheUtil.put(this.EXPORT_CACHE_MAP, exportDictMap);
        }
        return exportDictMap;
    }

    public Map<String, Long> selectSqlMap(String sql) {

        Map<String, Long> importMap = null;
        List<Map> tableDataList = this.dictService.exceImportSql(sql);
        if (tableDataList != null) {
            if (importMap == null) {
                importMap = new HashMap<>();
            }
            for (Map m : tableDataList) {
                importMap.put((String) m.get("name"), (Long) m.get("id"));
            }
        }
        return importMap;
    }
}
