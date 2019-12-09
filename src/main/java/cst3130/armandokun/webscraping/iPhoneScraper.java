package cst3130.armandokun;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/** Example code to illustrate web scraping with JSoup */
public class iPhoneScraper {
    
    
    /** Constructor */
    iPhoneScraper(){
        try{
            scrapeIphones();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    /** Scrapes cornflakes data from the Ocado website */
    void scrapeIphones() throws Exception{
        //Name of item that we want to scrape
        String itemName = "phones";
        
        //Download HTML document from website
        Document doc = Jsoup.connect("https://www.argos.co.uk/search/" + itemName).get();
        
        //Get all of the products on the page
        Elements prodsLayer1 = doc.select(".ProductCardstyles__Wrapper-l8f8q8-0");
        Elements prodsLayer2 = prodsLayer1.select(".dlOkBP");
        
        //Work through the products
        for(int i=0; i<prodsLayer2.size(); ++i){
            
            //Get the product description
            Elements description = prodsLayer2.get(i).select(".ProductCardstyles__Title-l8f8q8-11.kLyOND");
            
            //Get the product price
            Elements price1 = prodsLayer2.get(i).select(".fHBARv");

            //Deletes pound symbol from the price and formats to float
            String scrapedPrice = price1.text().replace("£", ""); 
            Float price = Float.parseFloat(scrapedPrice);

            //Get the image url
            Elements image = prodsLayer2.get(i).select(".dnXqZD");
            String imageUrl = image.select("img").attr("src");

            //Get product url
            Elements productLink = prodsLayer2.get(i).select(".btn-cta");
            String productUrl = productLink.attr("href");
            
            //Output the data that we have downloaded
            System.out.println("\n ARGOS: " + i + "\n DESCRIPTION: " + description.text() + ";\n PRICE: " + price + ";\n IMAGE_URL: " + imageUrl
            + ";\n PRODUCT_URL: " + productUrl + ";");
        }
    }
}
