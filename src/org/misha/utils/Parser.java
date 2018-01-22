/*
* Copyright (c) 2017 by Casenet, LLC
*
* This file is protected by Federal Copyright Law, with all rights
* reserved. No part of this file may be reproduced, stored in a
* retrieval system, translated, transcribed, or transmitted, in any
* form, or by any means manual, electric, electronic, mechanical,
* electro-magnetic, chemical, optical, or otherwise, without prior
* explicit written permission from Casenet, LLC.
*
* Created by: imouraviev
* Date: 3/7/2017
*/
package org.misha.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Document rootDoc;


    public void init(String url) {
        try {
            rootDoc = Jsoup.connect(url).get();
        } catch (IOException ioe){
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }
    }

    public String getTitle(){
        Elements elements = rootDoc.getElementsByTag("title");

        return elements.get(0).text();
    }

    public String getPlot(){
        Elements elements = rootDoc.getElementsByClass("summary_text");
        return elements.get(0).text();
    }

    public List<String> getGenres(){
        Elements elements = rootDoc.getElementsByClass("itemprop");
        List<String> genres = new ArrayList<String>();

        for (Element ee : elements){
            if(ee.attr("itemprop").equals("genre"))
            genres.add(ee.text());
        }
        return genres;
    }

    public List<String> getCast(){
        Elements elements = rootDoc.getElementsByClass("itemprop");
        List<String> cast = new ArrayList<String>();

        for(Element ee : elements){
            if(ee.attr("itemprop").equals("actor")){
                cast.add(ee.text());
            }
        }
        return cast;
    }

    public String getImage(){
        Elements elements = rootDoc.getElementsByAttributeValue("rel", "image_src");
        Element ee = elements.first();

        for (Attribute aa : ee.attributes()){
            if(aa.getKey().equals("href")){
                return aa.getValue();
            }
        }
        return null;
    }
}
