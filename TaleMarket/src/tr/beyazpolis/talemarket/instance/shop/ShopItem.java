package tr.beyazpolis.talemarket.instance.shop;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tr.talemc.net.item.ItemCreator;

public final class ShopItem {

  private final Material material;
  private final int sellPrice;
  private final int buyPrice;
  private final int amount;
  private final byte data;
  private final boolean sellable;
  private final boolean buyable;
  private final int page;
  private final int slot;

  private ItemStack stack;

  public ShopItem(final Material material, final int sellPrice, final int buyPrice, final int amount, final byte data, final boolean sellable, final boolean buyable,final int page,final int slot) {

    this.material = material;
    this.sellPrice = sellPrice;
    this.buyPrice = buyPrice;
    this.amount = amount;
    this.data = data;
    this.sellable = sellable;
    this.buyable = buyable;
    this.slot = slot;
    this.page = page;
    this.stack = null;

  }

  public void setLoreItem(){
    final List<String> lore = new ArrayList<>();
    if (buyable){
      lore.add(" &7Bu eşyayı &aTufondan&7 satın almak");
      lore.add(" &7için &aZümrüt Parası&7 ile odeme yapmalısın.");
      lore.add("");
      lore.add("  &7Adet : &e" + amount);
      lore.add("");
      lore.add("  &7Satın Alım Bedeli : &A&N" + formatter(buyPrice)+ "Z");
    } else {
      lore.add(" &aGezgin Tufon&7 uzak diyarlardan bu eşyayı");
      lore.add(" &7pazarına getirdi.");
      lore.add("");
      lore.add(" &7Bu eşyayı &aTufondan&7 satın almak");
      lore.add(" &7için &aZümrüt Parası&7 ile odeme yapmalısın.");
      lore.add("");
      lore.add("  &7Satın Alım Bedeli : &4✘.     ");
      lore.add("");

    }
    if (sellable){
      lore.add("  &7Geri Satış Bedeli : &A&N" + formatter(sellPrice) + "Z");
    } else {
      lore.add("  &7Geri Satış Bedeli : &4✘.     ");
    }
    lore.add("");

    stack = new ItemCreator(material,amount,data).setLore(lore).toItemStack();

  }

  public int getAmount() {
    return amount;
  }

  public byte getData() {
    return data;
  }

  public int getBuyPrice() {
    return buyPrice;
  }

  public Material getMaterial() {
    return material;
  }

  public int getSellPrice() {
    return sellPrice;
  }

  public boolean isSellable() {
    return sellable;
  }

  public boolean isBuyable() {
    return buyable;
  }

  public ItemStack getStack() {
    return stack;
  }

  public int getSlot() {
    return slot;
  }

  public static String formatter(int money){
    return NumberFormat.getCurrencyInstance(new Locale("tr","TR")).format(money).split(",")[0];
  }

  //düzelttim locale tr yaptım
  //farketmez
  public int getPage() {
    return page;
  }
}
