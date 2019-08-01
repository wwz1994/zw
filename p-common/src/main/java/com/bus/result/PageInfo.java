package com.bus.result;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wwz on 2018-11-25.
 */
public class PageInfo<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    //当前页数
    private int pageNumber;
    //每页记录数
    private int pageSize;
    // 总条数
    private long total;
    //总页数
    private int pages;
    //列表数据
    private List<T> rows;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public PageInfo(int pageNumber,int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
    public PageInfo() {
    }

    // 集成mybatis-plus分页器
    // Page<> page = page.setRecords(list);
    // new PageInfo<>(page)
    /*public PageInfo(Page<T> page) {
        this(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());

    }

    // 集成mybatis-plus分页器,但是不使用page对象里的记录集合
    // 不需要确定page泛型的具体类型
    public PageInfo(Page page,List<T>rows) {
        this(page.getCurrent(), page.getSize(), page.getTotal(), rows);
*/
   // }

    public PageInfo(int pageNum,int pageSize,long total,List<T>rows) {
        this.pageNumber = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;
        if(total % pageSize == 0){
            this.pages = (int)(total/(long)pageSize) ;
        }else {
            this.pages = (int)(total/(long)pageSize) + 1;
        }
        this.isFirstPage = (pageNum == 1);
        this.isLastPage = (pageNum == pages);
        this.hasPreviousPage = (!isFirstPage && pageSize>1);
        this.hasNextPage = (!isLastPage && pageSize>1);
        this.prePage = (hasPreviousPage ? pageNum -1: pageNum);
        this.nextPage = (hasNextPage ? pageNum + 1 : pageNum);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}