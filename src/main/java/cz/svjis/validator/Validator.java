/*
 *       Validator.java
 *
 *       This file is part of SVJIS project.
 *       https://github.com/svjis/svjis
 *
 *       SVJIS is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 3 of the License, or
 *       (at your option) any later version. <http://www.gnu.org/licenses/>
 */

package cz.svjis.validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jaroslav_b
 */
public class Validator {
    
    public static final int MAX_INT_ALLOWED = 10000000;
    public static final int MAX_STRING_LEN_ALLOWED = 1000000;

    private Validator() {}
    
    public static String getString(HttpServletRequest request, String parName, int minLen, int maxLen, boolean canBeNull, boolean canContainHtmlTags) throws InputValidationException {
        return validateString(request.getParameter(parName), parName, minLen, maxLen, canBeNull, canContainHtmlTags);
    }
    
    public static String getStringFromCookie(HttpServletRequest request, String parName, int minLen, int maxLen, boolean canBeNull, boolean canContainHtmlTags) throws InputValidationException {    
        return validateString(getCookie(request.getCookies(), parName), parName, minLen, maxLen, canBeNull, canContainHtmlTags);
    }
    
    private static String validateString(String value, String parName, int minLen, int maxLen, boolean canBeNull, boolean canContainHtmlTags) throws InputValidationException {
        String msg = "String parameter %s[%s, %s] is not valid. (%s)";
        
        if ((value == null) && canBeNull) {
            return value;
        }
        
        value = Validator.fixTextInput(value, canContainHtmlTags);
        
        if (!Validator.validateString(value, minLen, maxLen)) {
            throw new InputValidationException(String.format(msg, parName, String.valueOf(minLen), String.valueOf(maxLen), value));
        }
        
        return value;
    }
    
    public static int getInt(HttpServletRequest request, String parName, int minInt, int maxInt, boolean canBeNull) throws InputValidationException {
        return validateInt(request.getParameter(parName), parName, minInt, maxInt, canBeNull);
    }
    
    public static int getIntFromCookie(HttpServletRequest request, String parName, int minInt, int maxInt, boolean canBeNull) throws InputValidationException {
        return validateInt(getCookie(request.getCookies(), parName), parName, minInt, maxInt, canBeNull);
    }
    
    private static int validateInt(String value, String parName, int minInt, int maxInt, boolean canBeNull) throws InputValidationException {
        String msg = "Integer parameter %s[%s, %s] is not valid. (%s)";
        
        if ((value == null) && canBeNull) {
            return 0;
        }
        
        if (!Validator.validateInteger(value, minInt, maxInt)) {
            throw new InputValidationException(String.format(msg, parName, String.valueOf(minInt), String.valueOf(maxInt), value));
        }

        return Integer.valueOf(value);
    }
    
    
    public static boolean getBoolean(HttpServletRequest request, String parName) {
        boolean result;
        if ((request.getParameter(parName) == null) || (request.getParameter(parName).equals("0"))) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }
    
    
    protected static boolean validateInteger(String s, int minInt, int maxInt) {
        int i;
        
        try {
            i = Integer.valueOf(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        
        if (i < minInt) {
            return false;
        }
        
        if (i > maxInt) {
            return false;
        }

        return isInjectionFree(s);
    }
    
    protected static boolean validateString(String s, int minLen, int maxLen) {
        if (s == null) {
            return false;
        }
        
        if (s.length() < minLen) {
            return false;
        }
        
        if (s.length() > maxLen) {
            return false;
        }
        
        return isInjectionFree(s);
    }
    
    private static boolean isInjectionFree(String s) {
        String sup = s.toUpperCase();
        
        return !(sup.contains(";") && 
            (sup.contains("SELECT") || sup.contains("INSERT") || sup.contains("UPDATE") || 
            sup.contains("DELETE") || sup.contains("DROP") || sup.contains("CREATE") || sup.contains("ALTER") || 
            sup.contains("EXECUTE") || sup.contains("COMMIT") || sup.contains("ROLLBACK")));
    }
    
    protected static String fixTextInput(String input, boolean enableHtml) {
        String result = input;
        
        if (result == null) {
            return null;
        }
        
        if (!enableHtml) {
            result = result.replace("<", "&lt;");
            result = result.replace(">", "&gt;");
        }
        
        return result;
    }
    
    /**
    *
    * @param cookies
    * @param key
    * @return cookie value
    */
   private static String getCookie(Cookie[] cookies, String key) {
       String result = null;
       if (cookies != null) {
           for (int i = 0; i < cookies.length; i++) {
               if (cookies [i].getName().equals(key)) {
                   result = cookies[i].getValue();
                   break;
               }
           }
       }
       return result;
   }
}
