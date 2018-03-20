import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private Map<String, Integer> temperaturesOnPages = new HashMap<>();
    private Date date;
    private String city;


    public String getCity(){
        return city;
    }
    public Data(String city){
        this.city = city;
        date = new Date();
    }
    public Data(String city, Date date, Map<String, Integer> tempnsOnPages){
        this.city = city;
        this.date = date;
        this.temperaturesOnPages = tempnsOnPages;
    }
    public Data(String city, Date date){
        this.city = city;
        this.date = date;
    }

    public String getSimpleDate(){
        StringBuilder result = new StringBuilder();
        result.append(date);
        result.delete(result.length()-12, result.length());
        return result.toString();
    }
    public void addTemperature(String website, Integer temperature){
        temperaturesOnPages.put(website, temperature);
    }

    public Map<String, Integer> getTemperaturesOnPages(){
        return temperaturesOnPages;
    }

    public void saveData() throws IOException {
        StringBuilder fileName = new StringBuilder();
        fileName.append(date);
        fileName.delete(0,4);
        fileName.delete(3,4);
        fileName.delete(5,6);
        fileName.delete(7,8);
        fileName.delete(fileName.length()-12, fileName.length());
        String pathAndName = "src\\output\\" + city.toLowerCase();
        String filePathAndName = pathAndName + fileName.toString();
        DataOutputStream out  =new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePathAndName)));
        if(temperaturesOnPages.containsKey("pogodynka.pl"))
            out.writeInt(temperaturesOnPages.get("pogodynka.pl"));
        else
            out.writeInt(777);
        if(temperaturesOnPages.containsKey("infometeo.pl"))
            out.writeInt(temperaturesOnPages.get("infometeo.pl"));
        else
            out.writeInt(777);
        if(temperaturesOnPages.containsKey("pogodnie.pl"))
            out.writeInt(temperaturesOnPages.get("pogodnie.pl"));
        else
            out.writeInt(777);
        if(temperaturesOnPages.containsKey("meteoprog.pl"))
            out.writeInt(temperaturesOnPages.get("meteoprog.pl"));
        else
            out.writeInt(777);
        if(temperaturesOnPages.containsKey("google.pl"))
            out.writeInt(temperaturesOnPages.get("google.pl"));
        else
            out.writeInt(777);
        out.close();
    }

    public static Data loadData(String name) throws IOException{
        String pathAndName = Weather.PATH + name;
        DataInputStream in = new DataInputStream((new BufferedInputStream(new FileInputStream(pathAndName))));
        Integer[] values = new Integer[5];
        for(int i =0; i < 5; i++)
            values[i] = new Integer(in.readInt());
        String city = name.substring(0,name.length() - 9);
        Date loadedDate = dateOfSave(2018, checkMonth(name.substring(name.length() - 9, name.length() -6)),
                checkDay(name.substring(name.length() - 6, name.length()-4)),
                checkHour(name.substring(name.length() -4, name.length() -2)),
                checkMin(name.substring(name.length() -2, name.length())));
        Data tempData = new Data(city, loadedDate);
        if(values[0] != 777)
            tempData.addTemperature("pogodynka.pl", values[0]);
        if(values[1] != 777)
            tempData.addTemperature("infometeo.pl", values[1]);
        if(values[2] != 777)
            tempData.addTemperature("pogodnie.pl", values[2]);
        if(values[3] != 777)
            tempData.addTemperature("meteoprog.pl", values[3]);
        if(values[4] != 777)
            tempData.addTemperature("google.pl", values[4]);
        return  tempData;
    }
    private static int checkMonth(String name){
        if(name.equals("Jan"))
            return 0;
        if(name.equals("Feb"))
            return 1;
        if(name.equals("Mar"))
            return 2;
        if(name.equals("Apr"))
            return 3;
        if(name.equals("May"))
            return 4;
        if(name.equals("Jun"))
            return 5;
        if(name.equals("Jul"))
            return 6;
        if(name.equals("Aug"))
            return 7;
        if(name.equals("Sep"))
            return 8;
        if(name.equals("Oct"))
            return 9;
        if(name.equals("Nov"))
            return 10;
        if(name.equals("Dec"))
            return 11;
        return 0;
    }
    private static int checkDay(String name){
        return Integer.valueOf(name);
    }
    private static int checkHour(String name){
        return Integer.valueOf(name);
    }
    private static int checkMin(String name){
        return Integer.valueOf(name);
    }
    private static Date dateOfSave(int year, int month, int day, int hour, int min){
        return new Date(year, month, day, hour, min);
    }


 /*   @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Miasto: ");
        result.append(city);
        result.append("\nData: ");
        result.append(date);
        result.delete(result.length()-12, result.length());
        result.append("\n");
        for(String page: temperaturesOnPages.keySet()){
            result.append(page).append(": ");
            result.append(temperaturesOnPages.get(page)).append("\n");
        }
        return result.toString();
    }*/
}
