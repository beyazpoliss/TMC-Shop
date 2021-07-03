package tr.beyazpolis.talemarket;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tr.beyazpolis.talemarket.commands.ShopCommand;
import tr.beyazpolis.talemarket.config.ShopConfig;
import tr.beyazpolis.talemarket.instance.ShopMainMenu;
import tr.beyazpolis.talemarket.listener.ShopMainMenuListener;
import tr.talemc.net.config.IConfig;

public final class TaleMarket extends JavaPlugin {

  @NotNull
  private ShopMainMenu shopMainMenu;

  @NotNull
  private IConfig shopConfig;

  @Override
  public void onEnable() {

    this.shopConfig = new ShopConfig(shopMainMenu,this,"market.yml");

    this.shopConfig.load(this);

    this.shopMainMenu = new ShopMainMenu(shopConfig,this);

    loadConfig();

    this.shopMainMenu.loadMainInventoryItems();
    this.shopMainMenu.loadShopInventory();

    getCommand("market").setExecutor(new ShopCommand(shopMainMenu));
    getCommand("reloadmarket").setExecutor(new ShopCommand(shopMainMenu));

    Bukkit.getPluginManager().registerEvents(new ShopMainMenuListener(shopMainMenu,this),this);

  }



  private void loadConfig(){
    this.shopConfig.firstOpenSetAsync(this);
  }

  @Override
  public void onDisable() {
    this.shopConfig.saveAsync(this);
  }

  public IConfig getConfigManager() {
    return shopConfig;
  }

  public ShopMainMenu getShopMainMenu() {
    return shopMainMenu;
  }

}
