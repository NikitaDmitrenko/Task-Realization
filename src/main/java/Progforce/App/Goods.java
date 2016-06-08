package Progforce.App;

import com.mongodb.BasicDBObject;

import java.io.Serializable;

public class Goods extends BasicDBObject implements Serializable {

    private String title;

    private double price;

    private String status;

    private BasicDBObject basicDBObject;

    public BasicDBObject getBasicDBObject() {
        return basicDBObject;
    }

    public void setBasicDBObject(BasicDBObject basicDBObject) {
        this.basicDBObject = basicDBObject;
    }

    public Goods(String title, double price, String status) {
        this.title = title;
        this.price = price;
        this.status = status;
    }

    public Goods() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
