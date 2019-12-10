package cst3130.armandokun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cst3130.armandokun.webscraping.*;

@Configuration
public class AppConfig {

    @Bean
    public OnbuyScraper OnBuy(){
        OnbuyScraper onbuy = new OnbuyScraper();

        // Set a store name
        onbuy.setStoreName("ONBUY");
        // Multiplies sleep time in miliseconds
        onbuy.setCrawlDelay(1);
        // Sets path to retrieve HTML for Jsoup
        onbuy.setJsoupDoc("https://www.onbuy.com/gb/search/?query=samsung&category=12871");
        // Sets path to retrieve HTML for other pages
        onbuy.setJsoupDocOtherPages("https://www.onbuy.com/gb/search/?query=samsung&category=12871&offset=");
        onbuy.setProductSelector("div.product.options.sponsored");
        onbuy.setDescriptionSelector(".name");
        onbuy.setPriceSelector(".value");
        onbuy.setSymbolReplacement("From £");
        onbuy.setImageSelector(".image");
        onbuy.setImageElSelector("img");
        onbuy.setImageUrlSelector("src");
        onbuy.setProductLinkSelector("a");
        onbuy.setProductLinkSelectorAttr("href");

        return onbuy;
    }

    @Bean
    public LaptopsDirectScraper LaptopsDirect(){
        LaptopsDirectScraper ldscraper = new LaptopsDirectScraper();

        // Set a store name
        ldscraper.setStoreName("LAPTOPS DIRECT");
        // Multiplies sleep time in miliseconds
        ldscraper.setCrawlDelay(1);
        // Sets path to retrieve HTML for Jsoup
        ldscraper.setJsoupDoc("https://www.laptopsdirect.co.uk/ct/phones-and-pdas/smartphones");
        // Sets path to retrieve HTML for other pages
        ldscraper.setJsoupDocOtherPages("https://www.onbuy.com/gb/search/?query=samsung&category=12871&offset=");
        ldscraper.setProductSelector(".OfferBox");
        ldscraper.setDescriptionSelector("a.offerboxtitle");
        ldscraper.setPriceSelector("span.offerprice");
        ldscraper.setSymbolReplacement("£");
        ldscraper.setImageSelector(".offerImage");
        ldscraper.setImageUrlSelector("src");
        ldscraper.setProductLinkSelector("a.offerboxlink.btn-prime");
        ldscraper.setProductLinkSelectorAttr("href");
        
        return ldscraper;
    }

    @Bean
    public ScraperManager mng() {
        ScraperManager scrapermng = new ScraperManager();
        scrapermng.scrapersList.add(LaptopsDirect());
        scrapermng.scrapersList.add(OnBuy());
        scrapermng.setScrapersList(scrapermng.scrapersList);

        return scrapermng;
    }

}