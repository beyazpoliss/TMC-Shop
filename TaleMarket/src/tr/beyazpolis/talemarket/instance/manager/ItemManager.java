package tr.beyazpolis.talemarket.instance.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import tr.beyazpolis.talemarket.instance.shop.ShopItem;
import tr.beyazpolis.talemarket.instance.shop.ShopPage;
import tr.talemc.net.config.IConfig;

public final class ItemManager {

  private final String shopName;
  private int shopItemSize;

  private final Set<ShopItem> shopItemSet;

  private final IConfig config;

  public ItemManager(final String shopName, final IConfig config){
   this.shopName = shopName;
   this.shopItemSet = new HashSet<>();
   this.config = config;
  }

  public void loadShopItemSize(HashMap<Integer, ShopPage> shopPageHashMap){
    for (String itemName : config.getKeys("Market." + shopName +".items",false)) {

      shopItemSize++;

      final int slot = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".slot");
      final int pageNumber = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".page");
      final int sellPrice = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".sellPrice");
      final int buyPrice = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".buyPrice");
      final int amount = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".amount");
      final int data = config.getConfig().getInt("Market." + shopName + "." + "items." + itemName + ".data");
      final String material = config.getConfig().getString("Market." + shopName + "." + "items." + itemName + ".material");
      final boolean sellable = config.getConfig().getBoolean("Market." + shopName + "." + "items." + itemName + ".sellable");
      final boolean buyable = config.getConfig().getBoolean("Market." + shopName + "." + "items." + itemName + ".buyable");

      final ShopItem shopItem = new ShopItem(Material.valueOf(material.toUpperCase()), sellPrice, buyPrice, amount, (byte) data, sellable, buyable, pageNumber,slot);
      shopItem.setLoreItem();

      shopItemSet.add(shopItem);
      shopPageHashMap.get(pageNumber).getShopItemHashMap().put(slot,shopItem);
    }
  }

  public int getShopItemSize() {
    return shopItemSize;
  }

  public String getShopName() {
    return shopName;
  }

  public Set<ShopItem> getShopItemSet() {
    return shopItemSet;
  }
}
