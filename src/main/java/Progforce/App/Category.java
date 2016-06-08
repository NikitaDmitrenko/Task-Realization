package Progforce.App;

import Progforce.Enums.Status;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.io.Serializable;
import java.util.List;

public class Category extends BasicDBObject implements Serializable {


    private List<Goods> goodsList;

    private BasicDBObject basicDBObject;

    public BasicDBObject getBasicDBObject() {
        return basicDBObject;
    }

    public void setBasicDBObject(BasicDBObject basicDBObject) {
        this.basicDBObject = basicDBObject;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public Category(String name){
        this.name = name;
    }

    public Category(){}

    public BasicDBList createDefaultCategories() {
        BasicDBList dbList = new BasicDBList();
        for (int i = 0; i < 3; i++) {
            basicDBObject = new BasicDBObject();
            final String categoryName;
            if (i == 0) {
                categoryName = "MobilePhones";
            } else if (i == 1) {
                categoryName = "TV-sets";
            } else {
                categoryName = "Motherboards";
            }
            basicDBObject.put(categoryName, pushSingleGoodsToCategory());
            dbList.add(basicDBObject);
        }
        return dbList;
    }

    public BasicDBObject pushSingleGoodsToCategory(){
        BasicDBList dbList = new BasicDBList();
        BasicDBObject object = new BasicDBObject();
        BasicDBObject iphone;
            iphone = new BasicDBObject();
            iphone.put("title", "iphone");
            iphone.put("price", 500);
            iphone.put("status", Status.AVAILABLE);
            dbList.add(iphone);
        dbList.add(iphone);
        object.put("goods",dbList);
        return object;
    }


}
