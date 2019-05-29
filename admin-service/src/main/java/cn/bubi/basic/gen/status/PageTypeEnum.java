package cn.bubi.basic.gen.status;

import cn.bubi.basic.gen.entity.page.PageCurdModal;
import cn.bubi.basic.gen.entity.page.PageModal;

/**
 * @Author: JoinHan
 * @Date: Created in 17:49 2018/2/2
 * @Modified By：
 */
public enum PageTypeEnum {

    CURD(1, "基本的增删改查页面", new PageCurdModal()),;

    private Integer pageTypeId;

    private String pageTypeDescription;

    private PageModal pageModal;

    PageTypeEnum(Integer pageTypeId, String pageTypeDescription, PageModal pageModal) {
        this.pageTypeId = pageTypeId;
        this.pageTypeDescription = pageTypeDescription;
        this.pageModal = pageModal;
    }

    public Integer getPageTypeId() {

        return this.pageTypeId;
    }

    public String getPageTypeDescription() {

        return this.pageTypeDescription;
    }

    public PageModal getPageModal() {

        return this.pageModal;
    }

    public void setPageModal(PageModal pageModal) {

        this.pageModal = pageModal;
    }
}
