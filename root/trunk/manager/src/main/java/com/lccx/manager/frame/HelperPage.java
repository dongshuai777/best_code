package com.lccx.manager.frame;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.List;

public class HelperPage {

	public static void getPage(BaseData data){
		PageHelper.startPage(data.getPageNumber(),data.getPageRows());
	}
	
	public static void setPageMessage(BaseData data, List list){
		Page<BaseData> page = (Page<BaseData>)list;
		data.setPageCountRows(page.getTotal());
		data.setPages(page.getPages());
		int startIndex=data.getPageNumber()*data.getPageRows()+1;
		data.setStartIndex(startIndex);
	}
}
