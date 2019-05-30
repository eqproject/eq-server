package org.eq.basic.gen.util;

import java.io.File;
import java.util.Map;

import org.eq.basic.common.util.FileLowUtils;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.basic.common.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eq.basic.gen.entity.xml.Template;

/**
 * @Author: JoinHan
 * @Date: Created in 11:06 2018/2/7
 * @Modified By：
 */
public class TemplateLowUtil extends TemplateUtil {

    private static Logger logger = LoggerFactory.getLogger(TemplateLowUtil.class);

    /**
     * 生成到文件
     *
     * @param tpl
     * @param model
     * @param isReplaceFile
     * @return
     */
    public static boolean generateToFile(Template tpl, Map<String, Object> model, boolean isReplaceFile)
            throws Exception {

        // 获取生成文件
        String fileName = StringLowUtils.replaceEach(renderString(tpl.getFilePath() + "/", model),
                new String[] {"//", "/", "." }, new String[] {File.separator, File.separator, File.separator })
                + renderString(tpl.getFileName(), model);
        logger.debug(" fileName === " + fileName);

        // 获取生成文件内容
        String content = renderString(StringLowUtils.trimToEmpty(tpl.getContent()), model);
        logger.debug(" content === \r\n" + content);

        // 如果选择替换文件，则删除原文件
        if (isReplaceFile) {
            FileLowUtils.deleteFile(fileName);
        }

        // 创建并写入文件
        if (FileLowUtils.createFile(fileName)) {
            FileLowUtils.writeToFile(fileName, content, true);
            logger.debug(" file create === " + fileName);
            return true;
        } else {
            logger.debug(" file extents === " + fileName);
            return false;
        }
    }
}
