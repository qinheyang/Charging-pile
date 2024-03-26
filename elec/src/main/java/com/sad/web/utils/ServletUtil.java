package com.sad.web.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtil {
    /**
     * 请求格式为json
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
