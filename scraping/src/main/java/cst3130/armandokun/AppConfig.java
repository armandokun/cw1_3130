package cst3130.armandokun;

import cst3130.armandokun.hibernate.dao.ItemDao;
import cst3130.armandokun.webscraping.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // Creates an instance of SessionFactory
    private SessionFactory sessionFactory;

    @Bean
    public OnbuyScraper OnBuy() {
        OnbuyScraper onbuy = new OnbuyScraper();

        onbuy.setProductDao(productDao());
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
    public LaptopsDirectScraper LaptopsDirect() {
        LaptopsDirectScraper ldscraper = new LaptopsDirectScraper();

        ldscraper.setProductDao(productDao());
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
    public FreemansScraper Freemans() {
        FreemansScraper fmScraper = new FreemansScraper();

        fmScraper.setProductDao(productDao());
        // Set a store name
        fmScraper.setStoreName("FREEMANS");
        // Multiplies sleep time in miliseconds
        fmScraper.setCrawlDelay(1);
        // Sets path to retrieve HTML for Jsoup
        fmScraper.setJsoupDoc(
                "https://www.freemans.com/electricals/phones/mobile-phones/_/N-1cZ1rZ1z13u09Z1z141dc?Ntt=phones&refined=leftnav&searchType=FullText");
        // Sets path to retrieve HTML for other pages
        fmScraper.setJsoupDocOtherPages(
                "https://www.freemans.com/electricals/phones/mobile-phones/_/N-1cZ1rZ1z13u09Z1z141dc?Ntt=phones&refined=leftnav&searchType=FullText&No=");
        fmScraper.setProductSelector("li.pContainer");
        fmScraper.setDescriptionSelector(".pDescriptionContainer");
        fmScraper.setPriceSelector(".pPriceContainer");
        fmScraper.setSymbolReplacement("£");
        fmScraper.setImageSelector(".pImageContainer");
        fmScraper.setImageElSelector("img");
        fmScraper.setImageUrlSelector("data-original");
        fmScraper.setProductLinkSelector("a");
        fmScraper.setProductLinkSelectorAttr("href");

        return fmScraper;
    }

    @Bean
    public CurrysScraper Currys() {
        CurrysScraper currys = new CurrysScraper();

        currys.setProductDao(productDao());
        // Set a store name
        currys.setStoreName("CURRYS");
        // Multiplies sleep time in miliseconds
        currys.setCrawlDelay(2);
        // Sets path to retrieve HTML for Jsoup
        currys.setJsoupDoc(
                "https://www.currys.co.uk/gbuk/phones-broadband-and-sat-nav/mobile-phones-and-accessories/mobile-phones/362_3412_32041_xx_xx/xx-criteria.html");
        // Sets path to retrieve HTML for other pages
        currys.setJsoupDocOtherPages(
                "https://www.currys.co.uk/gbuk/phones-broadband-and-sat-nav/mobile-phones-and-accessories/mobile-phones/362_3412_32041_xx_xx/");
        currys.setProductSelector("article.product.result-prd.productCompare.clearfix");
        currys.setDescriptionSelector(".productTitle");
        currys.setPriceSelector(".price");
        currys.setSymbolReplacement("£");
        currys.setImageSelector(".lozadImage");
        currys.setImageElSelector("source");
        currys.setImageUrlSelector("srcset");
        currys.setProductLinkSelector("a");
        currys.setProductLinkSelectorAttr("href");

        return currys;
    }

    @Bean
    public ArgosScraper Argos() {
        ArgosScraper argos = new ArgosScraper();

        argos.setProductDao(productDao());
        // Set a store name
        argos.setStoreName("ARGOS");
        // Multiplies sleep time in miliseconds
        argos.setCrawlDelay(2);
        // Sets path to retrieve HTML for Jsoup
        argos.setJsoupDoc("https://www.argos.co.uk/search/iphones/category:42793786/");
        // Sets path to retrieve HTML for other pages
        argos.setJsoupDocOtherPages("https://www.argos.co.uk/search/iphones/category:42793786/opt/page:");
        argos.setProductSelector(".ProductCardstyles__Wrapper-l8f8q8-0");
        argos.setDescriptionSelector(".ProductCardstyles__Title-l8f8q8-11.kLyOND");
        argos.setPriceSelector(".fHBARv");
        argos.setSymbolReplacement("£");
        argos.setImageSelector(".dnXqZD");
        argos.setImageElSelector("img");
        argos.setImageUrlSelector("src");
        argos.setProductLinkSelector(".btn-cta");
        argos.setProductLinkSelectorAttr("href");

        return argos;
    }

    @Bean
    public ScraperManager mng() {
        ScraperManager scrapermng = new ScraperManager();

        // List of Threads
        scrapermng.scrapersList.add(LaptopsDirect());
        scrapermng.scrapersList.add(OnBuy());
        scrapermng.scrapersList.add(Freemans());
        scrapermng.scrapersList.add(Currys());
        scrapermng.scrapersList.add(Argos());

        scrapermng.setScrapersList(scrapermng.scrapersList);

        return scrapermng;
    }

    @Bean
    public SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            try {
                //Create a builder for the standard service registry
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

                //Load configuration from hibernate configuration file.
                //Here we are using a configuration file that specifies Java annotations.
                standardServiceRegistryBuilder.configure();

                //Create the registry that will be used to build the session factory
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
                try {
                    //Create the session factory - this is the goal of the init method.
                    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                } catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                    System.err.println("Session Factory build failed.");
                    e.printStackTrace();
                    StandardServiceRegistryBuilder.destroy(registry);
                }

                //Output result
                System.out.println("Session factory built.");

            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("SessionFactory creation failed." + ex);
            }
        }

        return sessionFactory;

    }

    @Bean
    public ItemDao productDao() {
        ItemDao productDao = new ItemDao();
        productDao.setSessionFactory(sessionFactory());
        return productDao;
    }

}
