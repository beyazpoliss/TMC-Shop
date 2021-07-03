package tr.beyazpolis.talemarket.instance.shop;

import java.util.HashMap;

public class ShopPage {

  private final HashMap<Integer, ShopItem> shopItemHashMap;

  public ShopPage(){
    this.shopItemHashMap = new HashMap<>();
  }

  public HashMap<Integer, ShopItem> getShopItemHashMap() {
    return shopItemHashMap;
  }

  public ShopItem getShopItem(int var){
    return this.shopItemHashMap.get(var);
  }
}
