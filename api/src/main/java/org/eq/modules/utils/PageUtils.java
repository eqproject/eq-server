package org.eq.modules.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存分页
 * @author  kaka
 * @date  20190606
 */
public class PageUtils{


    /**
     * 内存分页
     * @param list
     * @param pagesize
     * @param currentPage
     * @return
     */
    public  static  List pageBySubList(List list, int pagesize, int currentPage) {
        int totalcount = list.size();
        int pagecount = 0;
        List subList = new ArrayList<>();
        int m = totalcount % pagesize;
        if (m > 0) {
            pagecount = totalcount / pagesize + 1;
        } else {
            pagecount = totalcount / pagesize;
        }
        int start = (currentPage - 1) * pagesize;
        if(start>totalcount){
            return subList;
        }
        int end = pagesize * (currentPage);
        if(m!=0 && currentPage == pagecount){
            end = totalcount;
        }
        if(end>totalcount){
            end = totalcount;
        }
        return  list.subList(start,end );
    }
}
