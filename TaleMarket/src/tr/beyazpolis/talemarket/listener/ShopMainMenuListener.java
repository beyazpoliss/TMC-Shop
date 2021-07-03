package tr.beyazpolis.talemarket.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import tr.beyazpolis.ekonomi.network.EconomyAPI;
import tr.beyazpolis.talemarket.TaleMarket;
import tr.beyazpolis.talemarket.instance.shop.Shop;
import tr.beyazpolis.talemarket.instance.ShopMainMenu;
import tr.beyazpolis.talemarket.instance.shop.ShopItem;
import tr.talemc.net.TaleLib;


public final class ShopMainMenuListener implements Listener {

  private final ShopMainMenu shopMainMenu;
  private final Plugin plugin;

  public ShopMainMenuListener(final ShopMainMenu shopMainMenu,final Plugin plugin) {
    this.shopMainMenu = shopMainMenu;
    this.plugin = plugin;
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    if (event.getClickedInventory() == null) return;
    if (!(event.getWhoClicked() instanceof Player)) return;
    if (!event.getClickedInventory().getName().equalsIgnoreCase(TaleLib.color("&nPazarcı Tufon"))) return;
    event.setCancelled(true);
    if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
    ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.LEVEL_UP, 3, 3);
    if (event.getRawSlot() == 10) {
      return;
    }
    shopMainMenu.action(((Player) event.getWhoClicked()).getPlayer(), ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
  }

  @EventHandler
  public void onClicks(InventoryClickEvent event) {
    if (event.getClickedInventory() == null) return;
    String stripColor = ChatColor.stripColor(event.getClickedInventory().getName()).split(" Sayfa")[0];
    if (shopMainMenu.getShopManager().getShop(stripColor) == null) return;
    final Shop shop = shopMainMenu.getShopManager().getShop(stripColor);
    int stripInt = Integer.parseInt(ChatColor.stripColor(event.getClickedInventory().getName()).split("/ ")[1]);
    final Inventory inventory = shop.getInventoryHashMap().get(stripInt);
    if (event.getClickedInventory().getName().equalsIgnoreCase(inventory.getName())) {
      final Player player = (Player) event.getWhoClicked();
      event.setCancelled(true);
      if (event.getRawSlot() == 53) {
        if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
          player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
          player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
          player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
        } else if (event.getCurrentItem().getType().equals(Material.ARROW)) {
          final ItemStack itemStack = event.getCurrentItem();
          final int inventoryNumber = Integer.parseInt(itemStack.getItemMeta().getDisplayName().split("/ ")[1]);
          player.openInventory(shop.getInventoryHashMap().get(inventoryNumber));
          player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 2);
        }
      } else if (event.getRawSlot() == 45) {
        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(TaleLib.color("&6Geri Dön / Pazar"))) {
          player.openInventory(shopMainMenu.getInventory());
        } else {
          final ItemStack itemStack = event.getCurrentItem();
          final int inventoryNumber = Integer.parseInt(itemStack.getItemMeta().getDisplayName().split("/ ")[1]);
          player.openInventory(shop.getInventoryHashMap().get(inventoryNumber));
        }
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 2);
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 2);
      } else {
        final int slot = event.getRawSlot();
        final ShopItem shopItem = shop.getShopItem(stripInt,slot);
        if (shopItem == null) {
          player.closeInventory();
          TaleLib.sendTitle(player, "&4?", "&6Tufon hava satarak minik domuzcukları kazıklamaz!", 20, 20, 5);
          player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
        } else {
          if (player.getInventory().firstEmpty() == -1) {
            player.closeInventory();
            TaleLib.sendTitle(player, "&4?", "&6Tufondan envanterin doluyken bir şey satın alamazsın!", 20, 20, 5);
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
            return;
          }
          switch (event.getClick()) {
            case LEFT:
              if (shopItem.isBuyable()) {
                double buyMoney = shopItem.getBuyPrice();
                if (EconomyAPI.getMoney(player.getUniqueId()) >= buyMoney) {
                  EconomyAPI.removeMoney(player.getUniqueId(),buyMoney);
                  player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 1);
                  final ItemStack addItem = new ItemStack(shopItem.getMaterial(),shopItem.getAmount(),shopItem.getData());
                  player.getInventory().addItem(addItem);
                  player.sendMessage(TaleLib.color("&E&LTUFON&A&L : &aMarket'den " + shopItem.getAmount() + "x adet " + shopItem.getMaterial().name() + " eşyasını " + ShopItem.formatter(shopItem.getBuyPrice()) + "Z karşılığında tufondan Satın aldın."));
                } else {
                  TaleLib.sendTitle(player, "&4?", "&6Tufon veresiye kabul etmez, paran yetersiz!", 20, 20, 5);
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
                  player.closeInventory();
                }
              } else {
                TaleLib.sendTitle(player, "&4?", "&6Bu eşyayı satın alamazsın!", 20, 20, 5);
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
                player.closeInventory();
              }
              break;
            case RIGHT:
              if (shopItem.isSellable()) {
                int sellMoney = shopItem.getSellPrice();
                this.sellItem(player,shopItem.getAmount(),shopItem.getMaterial(),sellMoney);
                
              } else {
                TaleLib.sendTitle(player, "&4?", "&6Bu eşyayı satamazsın!", 20, 20, 5);
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
                player.closeInventory();
              }
              break;
          }
        }
      }
    }
  }

  private void sellItem(final Player player,final int amount,final Material material,final int sellPrice) {

    final Inventory inventory = player.getInventory();



    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < inventory.getSize(); i++) {
          if (inventory.contains(material)) {
            if (inventory.getItem(i) == null) continue;
            final ItemStack loopStack = inventory.getItem(i);
            if (loopStack.hasItemMeta()) continue;
            if (!loopStack.getType().equals(material)) continue;
            int startNumber = 0;
            if (startNumber >= amount) {
              return;
            }
            if (loopStack.getAmount() >= amount) {
              startNumber += amount;
              if (loopStack.getAmount() - amount <= 0){
                player.getInventory().setItem(i,new ItemStack(Material.AIR));
              } else {
                player.getInventory().setItem(i,new ItemStack(loopStack.getType(),loopStack.getAmount() - amount));
              }
              EconomyAPI.addMoney(player.getUniqueId(), sellPrice);
              player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 1);
              player.sendMessage(TaleLib.color("&E&LTUFON&A&L : &aMarkete " + amount + "x adet " + material.name() + " eşyasını " + ShopItem.formatter(sellPrice) + " karşılığında tufona sattın."));
              return;
            } else {
              startNumber += amount;
              final int newSellPrice = loopStack.getAmount() / sellPrice;
              player.getInventory().setItem(i,new ItemStack(Material.AIR));
              EconomyAPI.addMoney(player.getUniqueId(), newSellPrice);
              player.playSound(player.getLocation(), Sound.VILLAGER_YES, 2, 1);
              player.sendMessage(TaleLib.color("&E&LTUFON&A&L : &aMarkete " + amount + "x adet " + material.name() + " eşyasını " + ShopItem.formatter(newSellPrice) + " karşılığında tufona sattın."));
              return;
            }
          } else {
            TaleLib.sendTitle(player, "&4?", "&6Bu eşya sende yok!", 20, 20, 5);
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 2, 1);
            player.closeInventory();
          }
        }
      }
    });
  }
}
