package com.lccx.manager.frame;

import java.util.List;

public class BaseData<T>{

	private int pages;//总页数
	private int pageRows=10;//每页多少行
	private int pageNumber=1;//当前第几页
	private long pageCountRows;//总行数
	private int startIndex;//开始记录的序号

	private List<T> baseList;//controller传参不分页实体集合
	private List<T> pageList;//分页实体集合


	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageCountRows() {
		return pageCountRows;
	}

	public void setPageCountRows(long pageCountRows) {
		this.pageCountRows = pageCountRows;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<T> baseList) {
		this.baseList = baseList;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
}
