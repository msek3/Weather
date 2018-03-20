import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageConnector {
    private URL url;
    private BufferedReader in;

    public PageConnector(String address) {
        try {
            url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {
            if(e instanceof IOException)
                in = null;
        }
    }

    public Integer getTemperatureFromPage() {
        String pageContent = downloadPageContent();
        return cutTemperatures(pageContent);
    }

    public static boolean checkIfTownIsWarsaw(String city){
        PageParser pprswarsaw = new PageParser();
        String warsawPages[] = pprswarsaw.preparePages("warszawa");
        PageConnector pc1 = new PageConnector(warsawPages[0]);
        PageParser pprscity = new PageParser();
        String cityPages[] = pprswarsaw.preparePages(city);
        PageConnector pc2 = new PageConnector(cityPages[0]);
        String warsawContent = pc1.downloadPageContent();
        String cityContent = pc2.downloadPageContent();
        Matcher m1 = Pattern.compile("content.title...Warszawa").matcher(warsawContent);
        Matcher m2 = Pattern.compile("content.title...Warszawa").matcher(cityContent);
        return m1.find() && m2.find();
    }
    private String downloadPageContent() {
        boolean read = true;
        String line;
        StringBuilder result = new StringBuilder();
        while (read) {
            try {
                line = in.readLine();
                if (line == null)
                    read = false;
                result.append(line);
            } catch (Exception e) {
                read = false;
                if(in == null)
                    return null;
            }
        }

        try {
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    private Integer cutTemperatures(String pageContent) {
        if(pageContent == null)
            return null;
        Matcher matcher = Pattern.compile("..\\d&deg;C.|..\\d.&#176;|temperature.value.?....|display.inline\">...").matcher(pageContent);
        if(matcher.find()) {
            String find = matcher.group();
            StringBuilder result = new StringBuilder(find.toString());
            if(find.contains("&deg")){
                if (!(result.charAt(result.length() - 1) == '/'));
                else {
                    while(matcher.find())
                        find = matcher.group();
                    result = new StringBuilder(find);

                }
                if (find.charAt(0) == '>' || find.charAt(0) == ' ')
                    result.delete(0, 1);
                result.delete(result.length() - 7, result.length());
            }
            else if(find.contains("#176")){
                result.delete(result.length() - 7, result.length());
                if(Character.isLetter(result.charAt(0)))
                    result.delete(0,1);
                if(result.charAt(0) == '>')
                    result.delete(0,1);
            }
            else if(find.contains("value")){
                result.delete(0, 19);
                if(result.charAt(result.length()-1) == '<' || result.charAt(result.length() -1) == '/')
                    result.delete(result.length()-1, result.length());
                if(result.charAt(result.length()-1) == '<')
                    result.delete(result.length()-1, result.length());
            } else if(find.contains("inline")){
                result.delete(0, 16);
                if(result.charAt(result.length()-1) == '<' || result.charAt(result.length() -1) == '/' )
                    result.delete(result.length()-1, result.length());
                if(result.charAt(result.length()-1) == '<')
                    result.delete(result.length()-1, result.length());
            }
            else
                return null;
            return Integer.parseInt(result.toString());
        }
        return null;
    }
}

