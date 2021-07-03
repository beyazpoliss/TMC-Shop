package tr.beyazpolis.talemarket.instance.shop;

import java.util.HashMap;
import javax.swing.plaf.PanelUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import tr.beyazpolis.talemarket.TaleMarket;
import tr.beyazpolis.talemarket.instance.manager.ItemManager;
import tr.beyazpolis.talemarket.instance.manager.PageManager;
import tr.talemc.net.TaleLib;
import tr.talemc.net.config.IConfig;

public final class Shop {

  private final String shopName;

  private final ItemManager itemManager;
  private final PageManager pageManager;

  private final HashMap<Integer, Inventory> inventoryHashMap = new HashMap<>();

  private final int pageNumber;

  private final IConfig config;

  public Shop(final String shopName, final int pageNumber, final IConfig config){
    this.shopName = shopName;
    this.pageNumber = pageNumber;
    this.itemManager = new ItemManager(shopName,config);
    this.pageManager = new PageManager(shopName,pageNumber);
    this.config = config;
  }

  public void load(){
    pageManager.loadPages(inventoryHashMap);
    itemManager.loadShopItemSize(pageManager.getShopPageHashMap());
    pageManager.loadItems(itemManager.getShopItemSet(),inventoryHashMap);
  }

  public void openFirstInventory(final Player player){

    if (inventoryHashMap.get(1) == null){
      player.sendMessage(TaleLib.color("&cSayfa bulunamadı Hata Kodu 0x43! Lütfen üst düzey bir yetkiliye ulaşın."));
      return;
    }

    player.openInventory(inventoryHashMap.get(1));

  }

  public String getShopName() {
    return shopName;
  }

  public ItemManager getItemManager() {
    return itemManager;
  }

  public PageManager getPageManager() {
    return pageManager;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public ShopPage getShopPage(int var){
    return this.pageManager.getShopPageHashMap().get(var);
  }

  public ShopItem getShopItem(int pageNumber,int slot){
    return this.getShopPage(pageNumber).getShopItem(slot);
  }

  public HashMap<Integer, Inventory> getInventoryHashMap() {
    return inventoryHashMap;
  }
}
