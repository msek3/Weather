
public class PageParser {
    private int index = 0;
    private static final String[] begginingOfPages = {
            "http://pogodynka.pl/polska/",
            "http://",
            "http://www.pogodnie.pl/",
            "https://www.meteoprog.pl/pl/weather/",
            "https://www.google.pl/search?q=pogoda+"
    };
    private static final String[] endOfPages= {
            "",
           ".infometeo.pl",
            "",
            "/",
            ""
    };
    private static final String pageName[] = {
            "pogodynka.pl",
            "infometeo.pl",
            "pogodnie.pl",
            "meteoprog.pl",
            "google.pl",
    };

    public static  int howMunyPages(){
        return begginingOfPages.length;
    }
    public String getNextPageName(){
        if(index < pageName.length)
            return pageName[index++];
        return null;
    }

    public String[] preparePages(String city){
        city = city.toLowerCase();
        String cityBeginningOfCapital = cityFromCapital(city);
        String result[] = new String[begginingOfPages.length];
        result[0] = begginingOfPages[0] + city + "_" + city + endOfPages[0];
        result[1] = begginingOfPages[1] + city + endOfPages[1];
        result[2] = begginingOfPages[2] + city + endOfPages[2];
        result[3] = begginingOfPages[3] + cityBeginningOfCapital + endOfPages[3];
        result[4] = begginingOfPages[4] + cityBeginningOfCapital + endOfPages[4];
        return result;
    }

    public static String cityFromCapital(String city){
        char firstLetter = city.charAt(0);
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(firstLetter));
        result.append(city);
        result.delete(1,2);
        return result.toString();
    }

}
