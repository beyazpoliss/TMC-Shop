package tr.beyazpolis.talemarket.instance.manager;

import java.util.HashMap;
import javax.swing.Icon;
import tr.beyazpolis.talemarket.instance.shop.Shop;
import tr.talemc.net.config.IConfig;

public final class ShopManager {

  private final HashMap<String, Shop> shopMap;

  private final IConfig config;

  public ShopManager(final IConfig config) {
    this.shopMap = new HashMap<>();
    this.config = config;
  }

  public void loadInventory(){

    for (String shopName : config.getKeys("Market",false)){
      int pageNumber = config.getConfig().getInt("Market." + shopName + ".maxPage");

      final Shop shop = new Shop(shopName,pageNumber,config);
      shop.load();

      shopMap.put(shopName,shop);
    }
  }

  public HashMap<String, Shop> getShopMap() {
    return shopMap;
  }

  public void clear(){
    shopMap.clear();
  }

  public Shop getShop(String name){
    return getShopMap().get(name);
  }
}
