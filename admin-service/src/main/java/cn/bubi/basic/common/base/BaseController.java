package cn.bubi.basic.common.base;

import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.util.ParseUtil;
import cn.bubi.basic.modules.sys.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 控制器支持类
 * Created by JoinHan on 2018/01/11.
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * dataTable对象
     */
    protected BaseTableData baseTableData = new BaseTableData();

    /**
     * dataTables请求头处理
     *
     * @param request
     * @param params
     */
    protected void getInfoFromRequest(HttpServletRequest request, Map<String, Object> params) {

        // 分页信息
        Integer start = ParseUtil.getInteger(request.getParameter("start"));
        Integer pageSize = ParseUtil.getInteger(request.getParameter("length"));
        if (start == null) {
            start = 0;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        // 0代表行号 表头第一行 表格的排序列 排序方式 列名称
        Integer orderNum = ParseUtil.getInteger(request.getParameter("order[0][column]"));
        String orderDir = request.getParameter("order[0][dir]");
        String orderName = request.getParameter("columns[" + orderNum + "][name]");
        params.put("start", start);
        params.put("pageSize", pageSize);
        if (orderName != null) {
            params.put("orderDir", orderDir);
            params.put("orderName", orderName);
        }
    }

    protected void downHeadSetting(HttpServletRequest request, HttpServletResponse response, String fileName)
            throws UnsupportedEncodingException {

        response.setContentType("application/octet-stream");
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        response.setHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
    }

    public String validateObject(Object entity, SysUser sysUser) {

        return "";
    }


    public SysUser getUser(){
        return  UserUtil.getInstance().getUser();
    }
}
