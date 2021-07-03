package tr.beyazpolis.talemarket.instance;

import java.util.function.IntConsumer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import tr.beyazpolis.talemarket.TaleMarket;
import tr.beyazpolis.talemarket.instance.manager.ShopManager;
import tr.talemc.net.TaleLib;
import tr.talemc.net.config.IConfig;
import tr.talemc.net.item.ItemCreator;

public final class ShopMainMenu {

  private final Inventory inventory;

  private final ShopManager shopManager;

  private final IConfig config;

  private final Plugin plugin;

  public ShopMainMenu(final IConfig config,final Plugin plugin){
    this.inventory = Bukkit.createInventory(null, 9*4, TaleLib.color("&nPazarcı Tufon"));
    this.shopManager = new ShopManager(config);
    this.plugin = plugin;
    this.config = config;
  }

  public void loadShopInventory(){
    shopManager.loadInventory();
  }

  public void loadMainInventoryItems() {
    inventory.setItem(10, new ItemCreator(Material.BOOK,1).setName("&6Pazarcı Tufon").
      setLore("", "&7Tufon marketinte çeşitli eşyalar", "&7satmaya çalışan bir pazarcıdır.", "",
        "&a▪ &eSimya, Zırh, Aletler", "&a▪ &eBoyalar, Çeşitli Edevatlar", "&a▪ &eAletler, Bloklar, Madenler", "&a▪ &eYünler, Kızıltaşlar, Tarım Aletleri",
        "&a▪ &eBaskın Eşyaları",
        "", "&7Gibi eşyalar satan &aTufon&7 alış veriş yaparken ", "&7sadece &aZümrüt Özü&7 kabul eder.").toItemStack());
    inventory.setItem(12,new ItemCreator(Material.GRASS,1).
      setName("&aBloklar").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&aBlokları&7 tedarik edebilirsin", ""
        , "&7Tufonun sattığı bu &aBloklar&7 harika.", "&7şekilde toplanmışlardır.").toItemStack());
    inventory.setItem(13,new ItemCreator(Material.LEATHER_HELMET,1).
      setName("&BZırhlar").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&bZırhları&7 tedarik edebilirsin", ""
        , "&7Tufonun sattığı bu &BZırhlar&7 harika.", "&7şekilde yapılmışlardır.").toItemStack());
    inventory.setItem(15,new ItemCreator(Material.IRON_PICKAXE,1).
      setName("&fAletler").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&fAletleri&7 satın alabilirsin", ""
        , "&7Tufonun sattığı bu &fAletler&7 çok", "&fkullanışlılar&7.").toItemStack());
    inventory.setItem(16,new ItemCreator(Material.BREWING_STAND_ITEM,1).
      setName("&5Iksirler").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&5iksirleri&7 satın alabilirsin", ""
        , "&7Tufonun sattığı bu &5iksirler&7 harika.", "&7şekilde &5büyülendi&7.").toItemStack());
    inventory.setItem(24,new ItemCreator(Material.INK_SACK,1,(byte)14).
      setName("&dBoyalar").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&dBoyaları&7 tedarik edebilirsin", ""
        , "&7Tufonun sattığı bu &dBoyalar&7 harika.", "&7şekilde &ddoğadan&7 toplanmıştır.").toItemStack());
    inventory.setItem(25,new ItemCreator(Material.LAVA_BUCKET,1).
      setName("&eÇeşitli Eşyalar").
      setLore("", "&7Bu kategoride ihtiyacın olan,", "&eBazı&7 eşyaları tedarik edebilirsin", ""
        , "&7Tufonun sattığı bu eşyalar harika.", "&7şekilde &eÜretilmiştir&7.").toItemStack());
  }

  public void reload(){
    config.load(plugin);
    shopManager.clear();
    this.loadMainInventoryItems();
    this.loadShopInventory();
  }

  public void action(final Player player,String name){
    if (shopManager.getShop(name) == null){
      player.sendMessage(TaleLib.color("&cSayfa bulunamadı Hata Kodu 0x42! Lütfen üst düzey bir yetkiliye ulaşın."));
    } else {
      shopManager.getShop(name).openFirstInventory(player);
    }
  }

  public Inventory getInventory() {
    return inventory;
  }

  public ShopManager getShopManager() {
    return shopManager;
  }
}
