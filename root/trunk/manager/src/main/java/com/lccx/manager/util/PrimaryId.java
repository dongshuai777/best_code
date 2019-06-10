/**
 * Copy Right Information   : lccx 
 * Project                  : KJGL
 * JDK version used         : jdk1.6
 * Comments                 : 获取主键id
 * Version                  : 1.0
 * create date              : 2017.03.23
 * author                   ：刘硕             
*/
package com.lccx.manager.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrimaryId {
	
	/**
	 * Description :
	 * @return String
	 */
    private static SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat yyyymmdd=new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat yyyymm=new SimpleDateFormat("yyyyMM");
    private static NumberFormat nf = NumberFormat.getInstance();
 
    static{
       
        nf.setMinimumIntegerDigits(6);
        nf.setGroupingUsed(false);
    }
    private static long postfix = 0;
    

    //生成充值订单编号使用，2012-08-07 by mafei
    private static long seqOfDepositOrderCode = 0;//充值订单编号的流水号部分；
    private static String dateOfDepositOrderCode = "";//充值订单编号的日期部分；
    private static SimpleDateFormat yyyymmdd8=new SimpleDateFormat("yyyyMMdd");
    private static NumberFormat nf5 = NumberFormat.getInstance();
    static
    {
    	nf5.setMinimumIntegerDigits(5);
    	nf5.setGroupingUsed(false);
		Date date = new Date();
		dateOfDepositOrderCode = yyyymmdd8.format(date);
    }
  //生成订单编号使用，2012-08-28 by wang
    private static long seqOfOrderCode = 0;//订单编号的流水号部分；
    private static String dateOfOrderCode = "";//订单编号的日期部分；
    private static NumberFormat nfo = NumberFormat.getInstance();
    static
    {
    	nfo.setMinimumIntegerDigits(6);
    	nfo.setGroupingUsed(false);
		Date date = new Date();
		dateOfOrderCode = yyyymmdd8.format(date);
    }
    
	public static synchronized String getId(){
		String id="";
		Date date=new Date();
		id=df.format(date);
		
		if(postfix==999999)
		{
		    postfix = 0;
		}else
		{
		    postfix++;
		}
//		double x = Math.random();
//		double y = Math.random();		
//		id=id+((Double.toString(x)).substring(2,5)+Double.toString(y).substring(2,5));		
		return id+nf.format(postfix);
	}
	public static synchronized String getKeyId(){
		String id="";
		Date date=new Date();
		id=df.format(date);
		
		if(postfix==999999)
		{
		    postfix = 0;
		}else
		{
		    postfix++;
		}
//		double x = Math.random();
//		double y = Math.random();		
//		id=id+((Double.toString(x)).substring(2,5)+Double.toString(y).substring(2,5));		
		return id+nf.format(postfix);
	}
	/**
     * Description :取当天日期格式  yyyy-MM-dd
     * @param  :
     * @return :当天日期格式  yyyy-MM-dd
     * @throws :Exception
     */
	public static String getToday(){
		String today="";
		Date date=new Date();
		today=yyyymmdd.format(date);
		return today;
	}
	/**
     * Description :取当月日期格式  yyyymm
     * @param  :
     * @return :当天日期格式  yyyymm
     * @throws :Exception
     */
	public static String getMonth(){
		String month="";
		Date date=new Date();
		month=yyyymm.format(date);
		return month;
	}
	
	
	//获取8位日期串
	public static String getDateStr8()
	{
		Date date = new Date();
		String id = yyyymmdd8.format(date);
		return id;
	}
	
	//校正充值订单编码的流水号最大值
	public static void adjustSeqOfDepositOrderCode(int _iNewMaxSeq)
	{
		seqOfDepositOrderCode = _iNewMaxSeq;
	}
	/**
	 * 功能：获取充值订单编号； 充值订单 D+8位日期+5位流水号 D2012073100001每日流水号从1开始。
	 * 说明：每日重启系统后需要从数据库查询当日最大订单编号，提取“流水号”部分，
	 *      调整这里的seqOfDepositOrderCode的初值；
	 *      dateOfDepositOrderCode的初值=当日8位日期；
	 * 2012-08-06 by mafei
	 */
	public static synchronized String getDepositOrderCode()
	{
		Date date = new Date();
		String id = yyyymmdd8.format(date);
		
		if( 0!=dateOfDepositOrderCode.compareTo(id) )// != id
		{//切换日期时，流水号复零
			seqOfDepositOrderCode = 0;
			dateOfDepositOrderCode = id;
		}

		//同日，流水号递增
		if(99999==seqOfDepositOrderCode)
		{//当日流水号达到最大值时，提示流水号已满==============
			seqOfDepositOrderCode = 0;
		}
		else
		{
			++seqOfDepositOrderCode;
		}
//		System.out.println("****************seqOfDepositOrderCode="+seqOfDepositOrderCode);
		
		return "D"+dateOfDepositOrderCode+nf5.format( seqOfDepositOrderCode );
	}
	
	//校正订单编码的流水号最大值
	public static void lastCodeOfOrder(int _iNewMaxSeq)
	{
		seqOfOrderCode = _iNewMaxSeq;
	}
	/**
	 * 功能：获取订单编号； 充值订单 8位日期+6位流水号 20120731000001每日流水号从1开始。
	 * 说明：每日重启系统后需要从数据库查询当日最大订单编号，提取“流水号”部分，
	 *      调整这里的seqOfOrderCode的初值；
	 *      dateOfOrderCode的初值=当日8位日期；
	 * 2012-08-29 by wang
	 */
	public static synchronized String getOrderCode()
	{
		Date date = new Date();
		String orderId = yyyymmdd8.format(date);
		if(0!=dateOfOrderCode.compareTo(orderId))
		{//切换日期时，流水号复零
			seqOfOrderCode = 0;
			dateOfOrderCode = orderId;
		}

		//同日，流水号递增
		if(999999==seqOfOrderCode)
		{//当日流水号达到最大值时，提示流水号已满==============
			seqOfOrderCode = 0;
		}
		else
		{
			++seqOfOrderCode;
		}
		
		return dateOfOrderCode+nfo.format( seqOfOrderCode );
	}
	
	
	/**
	 * Description :����������ID
	 */
	public static void main(String[] args){
		/*for(int i=0;i<2000;i++){
			System.out.println(i+":"+PrimaryId.getId());	
			//System.out.println(PrimaryId.getId().length());	
		}*/
	/*	 SimpleDateFormat   myFormatter   =   new   SimpleDateFormat("yyyy-MM-dd");   
		    
		  java.util.Date date;
		  java.util.Date mydate;
		try {
			date = myFormatter.parse("2003-05-1");
			mydate = myFormatter.parse("2003-05-02");
			long     day=(date.getTime()-mydate.getTime())/(24*60*60*1000);   
			Date tomorrow  =new   java.util.Date(date.getTime()+(1000*60*60*24));
			System.out.println("相差的日期:"   +   tomorrow);   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    */ 
		    
		System.out.println(PrimaryId.getId());
		    				
	}
}
