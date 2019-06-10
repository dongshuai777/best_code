package com.lccx.manager.frame.jwt;



import com.lccx.manager.frame.ConstantClass;
import com.lccx.manager.util.PropertyUtil;
import com.lccx.manager.util.RedisUtil;
import com.lccx.manager.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.sf.json.JSONObject;
import org.springframework.web.filter.GenericFilterBean;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

public class JwtFilter extends GenericFilterBean{

    private Logger logger = Logger.getLogger("JwtFilter");

//    @Value("${com.jwt.secret}")
//    private  String SECRET;

//    @Value("${com.jwt.issuer}")
//    private  String JWT_ISSUER;*/

    @Resource
    private RedisUtil redisUtil;


    private static final String HTTP_STATUS_ERROR = "ERROR";
    private static final String HTTP_STATUS_OK = "OK";
    private static final String HTTP_TOKEN_MESSAGE_INVALID = "Token validate failure. ";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //secretKey
        String SECRET = PropertyUtil.getProperty("com.jwt.secret","!QAZ@WSX#EDC");

        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Base64.getEncoder().encodeToString(SECRET.getBytes()));

        //请求头上的token
        String jsonTokenWeb = request.getHeader(ConstantClass.TOKEN_HEADER);

//        HttpResponseBean responseWsBean = new HttpResponseBean();

        try {
            Claims claims = Jwts.parser().setSigningKey(apiKeySecretBytes).parseClaimsJws(jsonTokenWeb)
                    .getBody();
            if(null==claims){
                JSONObject json=new JSONObject();
                json.put("success",false);
                json.put("code","50001");
                System.out.println("claims空");
                response.getWriter().write(json.toString());
            }else{
                if(Util.isCon(redisUtil.get(jsonTokenWeb,0))){
                    JSONObject loginStateJson= JSONObject.fromObject(redisUtil.get(jsonTokenWeb,0));
                    if (loginStateJson.get("state").equals("1")){
                        //更新redis过期时间
                        redisUtil.expire(jsonTokenWeb,1800,0);
                        filterChain.doFilter(request,response);
                    }else{
                        //登录过期清空jwt
                        claims.clear();
                        JSONObject json=new JSONObject();
                        json.put("success",false);
                        json.put("code","40000");
                        response.getWriter().write(json.toString());
                    }
                }else{
                    JSONObject json=new JSONObject();
                    json.put("success",false);
                    json.put("code","50001");
                    response.getWriter().write(json.toString());
                }
            }

        }catch (Exception e){
            JSONObject json=new JSONObject();
            json.put("success",false);
            json.put("code","50001");
            System.out.println("jwt拦截器报错："+e);
            response.getWriter().write(json.toString());
        }

    }

    private void setResponseWsBean(HttpResponseBean responseWsBean, String httpStatus , String message){
        responseWsBean.setHttpStatus(httpStatus);
        responseWsBean.setMessage(message);
    }
}
