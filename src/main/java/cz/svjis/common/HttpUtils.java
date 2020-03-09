/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.common;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jaroslav_b
 */
public class HttpUtils {
    
    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class.getName());

    public static void writeBinaryData(String contentType, String fileName, byte[] data, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String userAgent = request.getHeader("User-Agent");
        String encodedFileName;
        if (userAgent.contains("MSIE") || userAgent.contains("Edge") || userAgent.contains("Opera") || userAgent.contains("Trident")) {
            encodedFileName = URLEncoder.encode(fileName.replace(" ", "_"), "UTF-8");
        } else {
            encodedFileName = "=?UTF-8?B?" + Base64.encodeBase64String(fileName.replace(" ", "_").getBytes("UTF-8")) + "?=";
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
        response.setDateHeader("Expires", 0);

        
        try (OutputStream outb= response.getOutputStream()) {
            outb.write(data, 0, data.length);
        } catch (java.io.IOException ex) {
            LOGGER.log(Level.SEVERE, "ClientAbortException:  java.io.IOException: Roura přerušena (SIGPIPE) ", ex);
        }
    }
    
    public static String highlight(String html, String textToFind) {
        return envelStrInHtml(html, textToFind, "<b style=\"color:black;background-color:#ffff66\">", "</b>");
    }
    
    protected static String envelStrInHtml(String html, String textToFind, String atBegin, String atEnd) {
        String result = html;
        String subp1 = "(?![^<]*>)";
        String subp2 = "(?:<[^>]+>)*";

        if ((textToFind != null) && !textToFind.equals("")) {

            char[] arr = textToFind.toCharArray();
            StringBuilder sbu = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    sbu.append(subp2);
                } else {
                    sbu.append(subp1);
                }

                sbu.append(String.valueOf(arr[i]));
            }
            String regexp = sbu.toString();
            
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regexp, java.util.regex.Pattern.CASE_INSENSITIVE);
            java.util.regex.Matcher m = p.matcher(html);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String replacement = "";
                replacement += atBegin;
                replacement += m.group();
                replacement += atEnd;
                m.appendReplacement(sb, replacement);
            }
            m.appendTail(sb);
            result = sb.toString();
        }
        return result;
    }
}
