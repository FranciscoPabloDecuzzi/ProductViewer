package com.ml.android.productviewer.entities;



/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class Product {
    private String title;
    private double price;
    private String currency;
    private String thumbnail;
    private String permalink;

    public Product(String title,double price,String currency, String thumbail, String permalink)
    {
        setTitle(title);
        setCurrency(currency);
        setPrice(price);
        setThumbnail(thumbail);
        setPermalink(permalink);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    private void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    private void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPermalink() {
        return permalink;
    }

    private void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}
