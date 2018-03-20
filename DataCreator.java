
public class DataCreator {
    private PageConnector pageConnector;
    private String[] pages;
    private Data data;
    public void createDataRecords(String city){
        data = new Data(city);
        PageParser pprs = new PageParser();
        pages = pprs.preparePages(city);
        for(String page: pages) {
            pageConnector = new PageConnector(page);
            Integer temperature = pageConnector.getTemperatureFromPage();
            String pageName = pprs.getNextPageName();
            if(pageName == "pogodynka.pl")
                if(PageConnector.checkIfTownIsWarsaw(city) && !(city.toLowerCase().equals("warszawa")))
                    temperature = null;
            if(temperature != null && pageName != null)
                data.addTemperature(pageName, temperature);
        }

    }
    public Data getData(){
        return data;
    }

}
