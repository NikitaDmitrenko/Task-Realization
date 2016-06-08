package Progforce.Main;

import Progforce.App.Shop;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[]args) throws UnknownHostException {
        Shop shop;
        for (int i = 0; i < 3; i++) {
            shop = Shop.getShop();

            Thread thread = new Thread(shop);
            thread.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All task complete");
    }

}
