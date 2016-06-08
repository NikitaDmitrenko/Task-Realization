package Progforce.App;

import Progforce.Enums.Status;
import com.mongodb.*;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Shop extends BasicDBObject implements Runnable, Serializable {


    public static void setShop(Shop shop) {
        Shop.shop = shop;
    }

    public static Shop getFoodShop() {
        return foodShop;
    }

    public static void setFoodShop(Shop foodShop) {
        Shop.foodShop = foodShop;
    }

    private String name;

    private List<Category> categoryList;

    public String getName() {
        return name;
    }

    private BasicDBObject basicDBObject;

    public BasicDBObject getBasicDBObject() {
        return basicDBObject;
    }

    public void setBasicDBObject(BasicDBObject basicDBObject) {
        this.basicDBObject = basicDBObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    private static Shop shop;

    private Shop(String name) {
        this.name = name;
    }

    private Shop() {
    }

    private static Shop foodShop;

    public static synchronized Shop getShop() {
        if (foodShop == null) {
            foodShop = new Shop();
        }
        return foodShop;
    }

    @Override
    public void run() {
        MongoClient client;
        DB db ;
        DBCollection collection ;
        try {
            client = new MongoClient("localhost", 27017);
            db = client.getDB("shop");
            collection = db.getCollection("shop");
            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("shop", this.createCategoriesWithGoods());
            collection.insert(dbObject);
            DBCollection collection1 = db.getCollection("shop");
            //BasicDBObject dbObject1 =(BasicDBObject) collection.find().next();
           // collection1.update(dbObject1, new BasicDBObject("$push", new BasicDBObject("shop.category.MobilePhones", new BasicDBObject("goods", new BasicDBObject("title", "iphone").append("price", 100).append("status", "availablefuckit")))));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public BasicDBObject createCategoriesWithGoods() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("shopName", "progForce");
        basicDBObject.put("category", new Category().createDefaultCategories());
        return basicDBObject;
    }

    private void addGoodsToCategory(String categoryName, Goods goods) throws UnknownHostException {
        DBCollection collection1 = intialize().getCollection("shop");
        DBCursor cursor = collection1.find();
        while (cursor.hasNext()) {
            BasicDBObject basicDBObject = (BasicDBObject) cursor.next();
            BasicDBObject shopObject = (BasicDBObject) basicDBObject.get("shop");
            BasicDBList categoryDBList = (BasicDBList) shopObject.get("category");
            if (!categoryDBList.isEmpty()) {
                BasicDBList goodsDbList = (BasicDBList) categoryDBList.get(categoryName);
                if (goodsDbList != null) {
                    BasicDBObject basicDBObject1 = new BasicDBObject();
                    basicDBObject.put("price",goods.getPrice());
                    basicDBObject.put("title",goods.getTitle());
                    basicDBObject.put("status",goods.getStatus());
                }
            }
        }
    }


    public List<Goods> getGoodsFromCategory(String categoryName) throws UnknownHostException {
        DBCollection collection1 = intialize().getCollection("shop");
        DBCursor cursor = collection1.find();
        List<Goods> goodsList = new ArrayList<>();
        Goods goods;
        while (cursor.hasNext()) {
            BasicDBObject basicDBObject = (BasicDBObject) cursor.next();
            BasicDBObject shopObject = (BasicDBObject) basicDBObject.get("shop");
            BasicDBList categoryDBList = (BasicDBList) shopObject.get("category");
            if (!categoryDBList.isEmpty()) {
                BasicDBList goodsDbList = (BasicDBList) categoryDBList.get(categoryName);
                if (goodsDbList != null && !goodsDbList.isEmpty()) {
                    for (Object object : goodsDbList) {

                        String title = ((BasicDBObject) object).getString("title");
                        double price = ((BasicDBObject) object).getDouble("price");
                        String status = ((BasicDBObject) object).getString("status");

                        goods = new Goods();
                        goods.setStatus(status);
                        goods.setTitle(title);
                        goods.setPrice(price);
                        goodsList.add(goods);
                    }
                }
            }
        }
        return goodsList;
    }

    public void changeGoodsStatus() throws UnknownHostException {
        DBCollection collection1 = intialize().getCollection("shop");
        DBCursor cursor = collection1.find();
        while (cursor.hasNext()) {
            BasicDBObject basicDBObject = (BasicDBObject) cursor.next();
            BasicDBObject shopObject = (BasicDBObject) basicDBObject.get("shop");
            BasicDBList categoryDBList = (BasicDBList) shopObject.get("category");
            if (!categoryDBList.isEmpty()) {
                for (int i = 0; i < categoryDBList.size(); i++) {
                    BasicDBList goodsDbList = (BasicDBList) categoryDBList.get(i);
                    if (!goodsDbList.isEmpty()) {
                            for (int g = 0; g < goodsDbList.size(); i++) {
                                if (i == 0) {
                                    String status = ((BasicDBObject) goodsDbList.get(i)).getString("status");
                                    if (!status.equals(Status.ABSENT)) {
                                        status = Status.ABSENT;
                                    }
                                } else {
                                    int halfAList = calculteHalfAList(goodsDbList);
                                    if(g<=halfAList){
                                        String status = ((BasicDBObject) goodsDbList.get(i)).getString("status");
                                        if (!status.equals(Status.ABSENT)) {
                                            status = Status.EXPECTED;
                                        }
                                    }else{
                                        double price  = ((BasicDBObject)goodsDbList.get(i)).getInt("price");
                                        price *=1.2;
                                    }
                                }
                            }
                    }
                }
            }
        }
    }



    private DB intialize() throws UnknownHostException {
        MongoClient client = new MongoClient("localhost", 27017);
        return client.getDB("shop");
    }

    public int calculteHalfAList(BasicDBList basicDBList){
        int n = basicDBList.size();
        int halfAnArray;
        if((n%2)==0){
            halfAnArray = n/2;
        }
        else{
           halfAnArray = (n+1)/2;
        }
        return halfAnArray;
    }
}
