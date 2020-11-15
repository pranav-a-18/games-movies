package com.assnpresn.moviegameapp.Scraper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebScraper {
    //private static final  String searchUrl= "https://sfbay.craigslist.org/d/for-sale/search/sss?query=iphone%208&sort=rel";

    public static List<Item> search(String searchQuery){

        String baseUrl = "https://in.ign.com/se/?model=article%2Cvideo&order_by=-date&q=";

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        try {
            String sUrl = baseUrl + URLEncoder.encode(searchQuery, "UTF-8");
            System.out.println(sUrl);
            HtmlPage page = client.getPage(sUrl);

            List<Item> resultItems = new ArrayList<>();
            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//article[@class = 'article NEWS']") ;
            if(items.isEmpty()){
                System.out.println("No items found !");
            }else{

                for(HtmlElement htmlItem : items){
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//div[@class='m']/h3/a"));

                    Item item = new Item();
                    item.setTitle(itemAnchor.asText());
                    item.setUrl( itemAnchor.getHrefAttribute());
                    resultItems.add(item);
                    System.out.println(item.getTitle());
                }
            }
            return resultItems;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return new ArrayList<Item>();
    }

}
