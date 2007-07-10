/*
 * Vodafone.java
 *
 * Created on 7. červenec 2007, 1:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package operators;

import esmska.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ripper
 */
public class Vodafone implements Operator {
    private String imgid;
    private String ppp;
    private final int MAX_CHARS = 760;
    private final int SMS_CHARS = 152;
    
    /**
     * Creates a new instance of Vodafone
     */
    public Vodafone() {
    }
    
    public URL getSecurityImage() {
        URL url = null;
        try {
            url = new URL("http://sms.vodafone.cz/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            
            String content = "";
            String s = "";
            while ((s = br.readLine()) != null) {
                content += s + "\n";
            }
            
            br.close();
            con.disconnect();
            
            Pattern p = Pattern.compile("<input type=\"hidden\" name=\"imgid\" value=\"(.*)\" />");
            Matcher m = p.matcher(content);
            if (m.find()) {
                imgid = m.group(1);
                url = new URL("http://sms.vodafone.cz/imgcode.php?id=" + imgid);
            }
            
            p = Pattern.compile("<input type=\"hidden\" name=\"ppp\" value=\"(.*)\" />");
            m = p.matcher(content);
            if (m.find())
                ppp = m.group(1);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }
    
    public boolean send(SMS sms) {
        try {
            URL url = new URL("http://sms.vodafone.cz/send.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            wr.write("message=" + URLEncoder.encode(sms.getText()!=null?sms.getText():"","UTF-8")
            + "&number=" + URLEncoder.encode(sms.getNumber()!=null?sms.getNumber():"","UTF-8")
            + "&mynumber=" + URLEncoder.encode(sms.getSenderNumber()!=null?sms.getSenderNumber():"","UTF-8")
            + "&sender=" + URLEncoder.encode(sms.getSenderName()!=null?sms.getSenderName():"","UTF-8")
            + "&imgid=" + URLEncoder.encode(imgid!=null?imgid:"","UTF-8")
            + "&ppp=" + URLEncoder.encode(ppp!=null?ppp:"","UTF-8")
            + "&pictogram=" + URLEncoder.encode(sms.getImageCode()!=null?sms.getImageCode():"","UTF-8")
            + "&send=" + URLEncoder.encode("Odeslat!","UTF-8")
            );
            wr.flush();
            wr.close();
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            
            String content = "";
            String s = "";
            while ((s = br.readLine()) != null) {
                content += s + "\n";
            }
            
            br.close();
            con.disconnect();
            
            Pattern p = Pattern.compile("<div id=\"thanks\">");
            Matcher m = p.matcher(content);
            boolean ok = m.find();
            
            p = Pattern.compile("<div id=\"errmsg\">\n<p>(.*?)</p>");
            m = p.matcher(content);
            if (m.find())
                sms.setErrMsg(m.group(1));
             
            return ok;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String toString() {
        return "Vodafone";
    }

    public int getMaxChars() {
        return MAX_CHARS;
    }

    public int getSMSCount(int chars) {
        return (int)Math.ceil((double)chars / SMS_CHARS);
    }
    
    
}
