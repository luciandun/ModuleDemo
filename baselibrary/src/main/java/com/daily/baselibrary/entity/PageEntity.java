package com.daily.baselibrary.entity;

import java.util.List;

/**
 * description:分页数据
 * author: dlx
 * date: 2018/11/15
 * version: 1.0
 */
public class PageEntity<T> {

    public int number;
    public int numberOfElements;
    public int totalPages;
    public int size;
    public boolean first;
    public boolean last;
    public int totalElements;
    public List<T> content;

}
