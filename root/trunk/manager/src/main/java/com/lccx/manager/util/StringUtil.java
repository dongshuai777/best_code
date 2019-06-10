/**
 * Copy Right Information   : lccx
 * JDK version used         : jdk1.6
 * Comments                 : 公共字符串管理类
 * Version                  : 1.0
 * create date              : 2014.04.23
 * author                   ：刘硕
 */
package com.lccx.manager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.util.*;

public class StringUtil {
    //默认分隔符
    private static final String MULTI_VALUE_ATTRIBUTE_DELIMITERS = ",; ";
    public StringUtil() {

    }

    // 显示中文
    public static String switchChinese(String str) {
        try {
            String temp;
            byte[] temp_byte = str.getBytes("ISO8859-1");
            temp = new String(temp_byte, "UTF-8");
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public static String switchEnglish(String str) {
        try {
            String temp;
            byte[] temp_byte = str.getBytes("UTF-8");
            temp = new String(temp_byte, "ISO8859-1");
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    // 用odbc桥时，转中文（SUN BUG）
    public static String makeinsertstring(String s) {
        if (s == null)
            return "";
        int oldStringLen = 0;
        oldStringLen = s.length();
        StringBuffer spaceString = new StringBuffer();
        for (int i = 0; i <= oldStringLen; i++)
            spaceString.append(" ");
        s = s + spaceString.toString();
        return s;
    }


    //是否为数字
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        if (str.length() == 0)
            return false;
        boolean number = true;
        int point = 0;
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if (i == 0) {
                if (!(tmp == '+' || tmp == '-' || (tmp >= '0' && tmp <= '9') || tmp == '.'))
                    number = false;
                if (tmp == '.')
                    point++;
            } else {
                if (!((tmp >= '0' && tmp <= '9') || tmp == '.'))
                    number = false;
                if (tmp == '.')
                    point++;
            }
        }
        if (point > 1)
            number = false;
        return number;
    }

    public static String getHtml1(String contents) {
        String _return = "\r\n";
        int find = 0;
        if (contents == null)
            return "";
        if (contents.length() == 0)
            return "";
        // replace "return" by "".
        while (true) {
            find = contents.indexOf(_return, find);
            if (find == -1)
                break;
            contents = contents.substring(0, find) + " "
                    + contents.substring(find + 2, contents.length());
        }
        return contents;
    }

    /** 将字符串改为html文档格式 */
    public static String getHtml(String contents) {
        String _return = "\r\n";
        int find = 0;
        if (contents == null)
            return "";
        if (contents.length() == 0)
            return "";
        // replace "return" by "".
        while (true) {
            find = contents.indexOf(_return, find);
            if (find == -1)
                break;
            contents = contents.substring(0, find) + " "
                    + contents.substring(find + 2, contents.length());
        }
        find = 0;
        // replace "space" by "&nbsp;".
        while (true) {
            find = contents.indexOf(" ", find);
            if (find == -1)
                break;
            contents = contents.substring(0, find) + "&nbsp;"
                    + contents.substring(find + 1, contents.length());
        }
        return contents;
    }


    /**
     * Description :用指定分隔符把字符串分解为数组．
     * @param  :str 要进行分解的字符串
     * @param  :delimiters 分隔符
     * @return :String[]
     * @throws :Exception
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * Description :用指定分隔符把字符串分解为数组．
     * @param  :str 要进行分解的字符串
     * @param  :delimiters 分隔符
     * @param  :trimTokens 是否将分解后的字符串进行trim处理
     * @param  :ignoreEmptyTokens 是否忽略分解后的字符为空 
     * @return :String[]
     * @throws :Exception
     */
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }

        if(Util.isCon(delimiters)){
            delimiters = MULTI_VALUE_ATTRIBUTE_DELIMITERS;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }
    //将Collection转换为数组
    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }

    /** 将回车、换行、双引号转换为空格 */
    public static String getNoReturn(String string) {
        char oldChar1 = 13;// 回车
        char oldChar2 = 10;// 换行
        char oldChar3 = "\"".charAt(0);// 引号
        char newChar = 32;// 空格
        if (string == null)
            return null;
        string = string.replace(oldChar1, newChar);
        string = string.replace(oldChar2, newChar);
        string = string.replace(oldChar3, newChar);
        return string;
    }

    public static String getTable(String[][] data) {
        if (data != null && data.length > 0)
            return getTable(data, data.length, data[0].length);
        else
            return "";
    }

    public static String getTable(String[][] data, int cols) {
        if (data != null && data.length > 0)
            return getTable(data, data.length, cols);
        else
            return "";
    }
    /**
     * 将数组转换成以逗号分隔的字符串
     *
     * @param needChange
     *            需要转换的数组
     * @return 以逗号分割的字符串
     */
    public static String arrayToStrWithComma(String[] needChange) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < needChange.length; i++) {
            sb.append(needChange[i]);
            if ((i + 1) != needChange.length) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public static String getTable(String[][] data, int rows, int cols) {
        if (data == null)
            return "";
        String returnValue = "";
        for (int i = 0; i < rows; i++) {
            returnValue += "<tr>";
            for (int n = 0; n < cols; n++) {
                returnValue += "<td>" + data[i][n] + "</td>";
            }
            returnValue += "</tr>";
        }
        return returnValue;
    }

    public static String getTable(String[][] data, String tr, String td,
                                  String type) {
        if (data == null)
            return "";
        String value = "";
        for (int i = 0; i < data.length; i++) {
            // checkbox
            if (type.equalsIgnoreCase("checkbox")) {
                value += "<tr " + tr + ">";
                value += "<td " + td
                        + "><input type='checkbox' name='cb' value=\""
                        + data[i][0] + "\"></td>";
                for (int n = 1; n < data[i].length; n++) {
                    value += "<td " + td + ">" + data[i][n] + "</td>";
                }
                value += "</tr>";
            }
            // checkbox+hidden
            else if (type.equalsIgnoreCase("checkbox1")) {
                value += "<tr " + tr + ">";
                value += "<td " + td
                        + "><input type='checkbox' name='cb' value=\""
                        + data[i][0]
                        + "\"><input type='hidden' name='hid' value=\""
                        + data[i][0] + "\"></td>";
                for (int n = 1; n < data[i].length; n++) {
                    value += "<td " + td + ">" + data[i][n] + "</td>";
                }
                value += "</tr>";
            }
            // radio
            else if (type.equalsIgnoreCase("radio")) {
                value += "<tr " + tr + ">";
                value += "<td " + td
                        + "><input type='radio' name='rd' value=\""
                        + data[i][0] + "\" " + (i == 0 ? "checked" : "")
                        + "></td>";
                for (int n = 1; n < data[i].length; n++) {
                    value += "<td " + td + ">" + data[i][n] + "</td>";
                }
                value += "</tr>";
            } else {
                value += "<tr " + tr + ">";
                for (int n = 0; n < data[i].length; n++) {
                    value += "<td " + td + ">" + data[i][n] + "</td>";
                }
                value += "</tr>";
            }
        }
        return value;
    }

    public static String getTable(String[][] data, String[][] input, String td) {
        if (data == null)
            return "";
        String returnValue = "";
        for (int i = 0; i < data.length; i++) {
            returnValue += "<tr id=\"trs\" ondblclick=\"on_dblclick(" + i
                    + ");\" onclick=\"displayMe(" + i
                    + ");\" bgcolor=\"#E3E3E3\" style=\"cursor:hand\">";
            for (int n = 0; n < data[0].length; n++) {
                if (data[i][n].length() == 0)
                    data[i][n] = "&nbsp;";
                boolean find = false;
                for (int m = 0; m < input.length; m++) {
                    if (input[m][0].equals("" + n)) {
                        if (input[m][1].equalsIgnoreCase("hidden"))
                            returnValue += "<td " + td + "><input type='"
                                    + input[m][1] + "' name='" + input[m][2]
                                    + "' value=\"" + data[i][n] + "\">"
                                    + data[i][n] + "</td>";
                        else
                            returnValue += "<td " + td + "><input type='"
                                    + input[m][1] + "' name='" + input[m][2]
                                    + "' value=\"" + data[i][n] + "\"></td>";
                        find = true;
                    }
                }
                if (!find)
                    returnValue += "<td " + td + ">" + data[i][n] + "</td>";
            }
            returnValue += "</tr>";
        }
        return returnValue;
    }

    public static String getOptions(String[][] data, String key) {
        String returnV = "";
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (data[i][0].equals(key))
                    returnV += "<option value='" + data[i][0] + "' selected>"
                            + data[i][1] + "</option>";
                else
                    returnV += "<option value='" + data[i][0] + "'>"
                            + data[i][1] + "</option>";
            }
        }
        return returnV;
    }

    public static String getOptions(String[][] data) {
        return getOptions(data, "");
    }

    public static String[][] sort(String[][] data, int sortedColumn,
                                  boolean isString) {
        return sort(data, sortedColumn, "asc", isString);
    }

    public static String[][] sort(String[][] data, int sortedColumn,
                                  String order, boolean isString) {
        int[] sortedColumn1 = new int[1];
        sortedColumn1[0] = sortedColumn;
        String[] order1 = new String[1];
        order1[0] = order;
        boolean[] isString1 = new boolean[1];
        isString1[0] = isString;
        return sort(data, sortedColumn1, order1, isString1);
    }

    public static String[][] sort(String[][] data, int[] sortedColumn,
                                  String[] order, boolean[] isString) {
        if (data == null)
            return null;
        if (data.length == 0)
            return data;
        boolean isChange = true;
        for (int i = data.length - 1; i > 0 && isChange; i--) {
            isChange = false;
            for (int j = 0; j < i; j++) {
                for (int m = 0; m < sortedColumn.length; m++) {
                    String result = compare(j, data, sortedColumn[m], order[m],
                            isString[m]);
                    if (!result.equals("equals")) {
                        if (m == 0 && result.equals("changed"))
                            isChange = true;
                        break;
                    }
                }
            }
        }
        return data;
    }

    private static String compare(int j, String[][] data, int sortedColumn,
                                  String order, boolean isString) {
        String result;// changed,not_changed,equals
        String tmp = null;
        if (order.equalsIgnoreCase("asc")) {
            if ((isString && (data[j][sortedColumn]
                    .compareTo(data[j + 1][sortedColumn])) > 0)
                    || (!isString && (Double
                    .parseDouble(data[j][sortedColumn] == null ? "0"
                            : data[j][sortedColumn]) > Double
                    .parseDouble(data[j + 1][sortedColumn] == null ? "0"
                            : data[j + 1][sortedColumn])))) {
                for (int l = 0; l < data[0].length; l++) {
                    tmp = data[j + 1][l];
                    data[j + 1][l] = data[j][l];
                    data[j][l] = tmp;
                }
                result = "changed";
            } else if ((isString && (data[j][sortedColumn]
                    .compareTo(data[j + 1][sortedColumn])) == 0)
                    || (!isString && (Double
                    .parseDouble(data[j][sortedColumn] == null ? "0"
                            : data[j][sortedColumn]) == Double
                    .parseDouble(data[j + 1][sortedColumn] == null ? "0"
                            : data[j + 1][sortedColumn])))) {
                result = "equals";
            } else {
                result = "not_changed";
            }
        } else {
            if ((isString && (data[j][sortedColumn]
                    .compareTo(data[j + 1][sortedColumn])) < 0)
                    || (!isString && (Double
                    .parseDouble(data[j][sortedColumn] == null ? "0"
                            : data[j][sortedColumn]) < Double
                    .parseDouble(data[j + 1][sortedColumn] == null ? "0"
                            : data[j + 1][sortedColumn])))) {
                for (int l = 0; l < data[0].length; l++) {
                    tmp = data[j + 1][l];
                    data[j + 1][l] = data[j][l];
                    data[j][l] = tmp;
                }
                result = "changed";
            } else if ((isString && (data[j][sortedColumn]
                    .compareTo(data[j + 1][sortedColumn])) == 0)
                    || (!isString && (Double
                    .parseDouble(data[j][sortedColumn] == null ? "0"
                            : data[j][sortedColumn]) == Double
                    .parseDouble(data[j + 1][sortedColumn] == null ? "0"
                            : data[j + 1][sortedColumn])))) {
                result = "equals";
            } else {
                result = "not_changed";
            }
        }
        return result;
    }

    public static void include(PrintWriter out, String file) throws IOException {
        include(out, file, false);
    }

    public static String strASCII(String src) {
        String keyStr = "lccx";
        int length = src.length();
        StringBuffer strBuff = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int l = src.charAt(i) ^ keyStr.charAt(i % 8);
            if (l < 100) {
                strBuff.append("0");
                if (l < 10)
                    strBuff.append("0");
            }
            strBuff.append(Integer.toString(l));
        }
        String secrect = strBuff.toString();
        // System.out.println("the source is :"+src+" and it's length is
        // :"+src.length());
        // System.out.println("the secrect is :"+secrect+" and it's length is
        // :"+secrect.length());
        return secrect;
    }

    // 在SERVLET中输出文件（类似JSP中INCLUDE标签）
    public static void include(PrintWriter out, String file, boolean chinese)
            throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        String tmp;
        while ((tmp = raf.readLine()) != null) {
            if (chinese)
                out.println(switchChinese(tmp));
            else
                out.println(tmp);
        }
    }

    // public int dd(String sDate){
    // java.sql.Date dCur=new java.sql.Date(0);
    // java.sql.Date dStandard=new java.sql.Date(0);
    // dCur=dCur.valueOf(sDate);//current day
    // dStandard=dStandard.valueOf("1899-12-31");
    // long lCur=dCur.getTime();
    // long lStandard=dStandard.getTime();
    // int i=(int)((lCur - lStandard )/(1000*86400));
    // return i;
    // }
    public static String StringReplace(String orgString, String repString,
                                       String withString) {// 字符串替换
        String resString = orgString;
        int _i;
        do {
            _i = orgString.indexOf(repString);
            if (_i != -1) {
                resString = orgString.substring(0, _i);
                resString = resString + withString;
                resString = resString
                        + orgString.substring(_i + repString.length());
                orgString = resString;
            }
        } while (_i != -1);
        return resString;
    }

    public static String replaceAll(String con, String tag, String rep) {
        int i = 0;
        int j = 0;
        int k = 0;
        String RETU = "";
        String temp = con;
        int tagc = tag.length();
        while (i < con.length())
            if (con.substring(i).startsWith(tag)) {
                temp = String.valueOf(con.substring(j, i))
                        + String.valueOf(rep);
                RETU = String.valueOf(RETU) + String.valueOf(temp);
                i += tagc;
                j = i;
            } else {
                i++;
            }
        RETU = String.valueOf(RETU) + String.valueOf(con.substring(j));
        return RETU;
    }
    //把但一个引号替换成两个单引号
    public static String reSingleQuote(String str){
        return replaceAll(str,"'","''");
    }

    public static String HtmlEncode(String s) {
        String re = new String();
        re = replaceAll(s, "<", "&lt;");
        re = replaceAll(re, ">", "&gt;");
        re = replaceAll(re, "\n", " ");
                re = replaceAll(re, " ", "&nbsp;");
        re = replaceAll(re, "'", "&#39");
        return re;
    }

    public static String TextEncode(String s) {
        String re = new String();
        re = replaceAll(s, "&lt;", "<");
        re = replaceAll(re, "&gt;", ">");
        re = replaceAll(re, " ", "\n");
                re = replaceAll(re, "&nbsp;", " ");
        re = replaceAll(re, "&#39", "'");
        return re;
    }

    //取出字符串中的的数字
    public static String sNumber(String all){
        String num="";
        for(int i=0;i<all.length();i++){
            for(int j=0;j<10;j++){
                if(String.valueOf(all.charAt(i)).equals(String.valueOf(j))){
                    num+=all.charAt(i);
                }
            }
        }
        return num;


    }
    /**去除数组中重复的记录
     * */
    public static String[] array_unique(String[] a) {
        List<String> list = new LinkedList<String>();
        for(int i = 0; i < a.length; i++) {
            if(!list.contains(a[i])) {
                list.add(a[i]);
            }
        }
        return (String[])list.toArray(new String[list.size()]);
    }

    /**
     * 打印json
     * @param json
     * @param response
     */
    public static void outPrintJson(JSONObject json, HttpServletResponse response) {
        try{
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);
            //System.out.println(json);
            response.getWriter().flush();
        }catch(Exception e){

        }
    }

    /**
     * 打印jsonarr
     * @param json
     * @param response
     */
    public static void outPrintJsonArr(JSONArray jsarr, HttpServletResponse response) {
        try{
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsarr);
            //System.out.println(jsarr);
            response.getWriter().flush();
        }catch(Exception e){

        }
    }

    //生成随机数字和字母,
    public static String getRandomPwd(int length) {

        String val = "";
        Random random = new Random();
        //length为几位密码 
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    public static String getIP(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 求两个数的百分比
     * @param x
     * @param total
     * @return
     */
    public static String getPercent(int x, int total){
        if(x==0){
            return "0";
        }
        // 创建一个数值格式化对象

        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位

        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format((float) x / (float) total * 100);

//    System.out.println("num1和num2的百分比为:" + result + "%");
        return result;
    }


    public static JSONObject getTableType4Tile(){
        JSONObject t1=new JSONObject();
        t1.put("name","被评议对象的基本情况");
        t1.put("id","1");
        JSONArray c1arr=new JSONArray();
        JSONObject nameJ=new JSONObject();
        nameJ.put("name","姓名");
        nameJ.put("id","2");
        JSONObject ageJ=new JSONObject();
        ageJ.put("name","出生年月");
        ageJ.put("id","3");
        JSONObject yJ=new JSONObject();
        yJ.put("name","原任职务");
        yJ.put("id","4");
        JSONObject xJ=new JSONObject();
        xJ.put("name","现任职务");
        xJ.put("id","5");
        JSONObject tJ=new JSONObject();
        tJ.put("name","任职时间");
        tJ.put("id","6");
        c1arr.add(nameJ);
        c1arr.add(ageJ);
        c1arr.add(yJ);
        c1arr.add(xJ);
        c1arr.add(tJ);
        t1.put("child",c1arr);
        return t1;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(StringUtil.getPercent(1,4));
//    StringUtil ss = new StringUtil();
//    String str = "*!@$%^&*()_\\";
//    System.out.println("ggggggggg");
//    str = StringUtil.replaceAll(str, "\\", "\\\\");
//    System.out.println(str);
//    str = StringUtil.replaceAll(str, "%", "\\%");
//    System.out.println(str);
//    str = StringUtil.replaceAll(str, "_ ", "\\_");
//    System.out.println(str);
//     String str1 = StringUtil.replaceAll("'", "'", "''");
//    System.out.println(str1);
        // System.out.println(ss.replaceAll("*!@$%^&*()","%","\\%"));
//        String[]a = {"11","3","001","11","3","001","2","5","001",};
//        a=StringUtil.array_unique(a);
//        System.out.println(Arrays.toString(a));
    }

}