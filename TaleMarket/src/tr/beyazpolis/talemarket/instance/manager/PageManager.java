package tr.beyazpolis.talemarket.instance.manager;

import java.util.HashMap;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import tr.beyazpolis.talemarket.instance.shop.ShopItem;
import tr.beyazpolis.talemarket.instance.shop.ShopPage;
import tr.talemc.net.TaleLib;
import tr.talemc.net.item.ItemCreator;

public final class PageManager {

  private final String shopName;
  private final int size;

  private final HashMap<Integer, ShopPage> shopPageHashMap;

  public PageManager(final String shopName,final int size) {
    this.shopName = shopName;
    this.size = size;
    this.shopPageHashMap = new HashMap<>();
  }

  public void loadPages(HashMap<Integer, Inventory> integerInventoryHashMap){

    for (int page = 1; page <= size; page++) {
      Inventory inventory = Bukkit.createInventory(null, 9 * 6, TaleLib.color("&n" + shopName + " Sayfa / " + page));
      System.out.println(page + " = " + size);
      if (page == size) {
        final int backNumber = page - 1;

        if (page == 1){
          inventory.setItem(45, new ItemCreator(Material.ARROW, 1).setName("&6Geri Dön / Pazar").setLore("", "&7Tufonun pazarına geri dönmek", "&7için &atıkla!").toItemStack());

        } else {
          inventory.setItem(45, new ItemCreator(Material.ARROW, 1).setName("&6Önceki Sayfa / " + backNumber).setLore("", "&7Tufonun pazarına geri dönmek", "&7için &atıkla!").toItemStack());
        }
        inventory.setItem(53, new ItemCreator(Material.BARRIER, 1).setName("&4Başka Sayfa Yok").setLore("", "&7Tufon bu kategoride daha fazla", "&7eşya &csatmıyor!").toItemStack());
      } else if (page == 1) {
        final int nextNumber = page + 1;
        inventory.setItem(45, new ItemCreator(Material.ARROW, 1).setName("&6Geri Dön / Pazar").setLore("", "&7Tufonun pazarına geri dönmek", "&7için &atıkla!").toItemStack());
        inventory.setItem(53, new ItemCreator(Material.ARROW, 1).setName("&6Sonraki Sayfa Pazarı / " + nextNumber).setLore("", "&7Sonraki sayfaya gitmek", "&7için &atıkla!").toItemStack());
      } else {
        final int backNumber = page - 1;
        final int nextNumber = page + 1;
        inventory.setItem(45, new ItemCreator(Material.ARROW, 1).setName("&6Önceki Sayfa / " + backNumber).setLore("", "&7Tufonun pazarına geri dönmek", "&7için &atıkla!").toItemStack());
        inventory.setItem(53, new ItemCreator(Material.ARROW, 1).setName("&6Sonraki Sayfa Pazarı / " + nextNumber).setLore("", "&7Sonraki sayfaya gitmek", "&7için &atıkla!").toItemStack());
      }

      shopPageHashMap.put(page,new ShopPage());
      integerInventoryHashMap.put(page, inventory);
    }

  }

  public void loadItems(final Set<ShopItem> shopItems,final HashMap<Integer, Inventory> inventory){
   shopItems.forEach(shopItem -> inventory.get(shopItem.getPage()).setItem(shopItem.getSlot(),shopItem.getStack()));
  }

  public String getShopName() {
    return shopName;
  }

  public HashMap<Integer, ShopPage> getShopPageHashMap() {
    return shopPageHashMap;
  }
}
