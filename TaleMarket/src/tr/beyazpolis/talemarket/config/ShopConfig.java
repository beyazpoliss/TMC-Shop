package tr.beyazpolis.talemarket.config;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import tr.beyazpolis.talemarket.instance.ShopMainMenu;
import tr.talemc.net.config.ConfigManager;
import tr.talemc.net.config.IConfig;

public class ShopConfig extends ConfigManager implements IConfig {

  private final ShopMainMenu shopMainMenu;

  public ShopConfig(final ShopMainMenu shopMainMenu,final Plugin plugin, final String ymlName) {
    super(plugin, ymlName);
    this.shopMainMenu = shopMainMenu;
  }

  @Override
  public void saveAsync(final Plugin plugin) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
      @Override
      public void run() {
       saveYML();
      }
    });
  }

  @Override
  public void firstOpenSetAsync(final Plugin plugin) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
      @Override
      public void run() {
        if (!isSet("Market")){
          setIfNotExists("Market.Zırhlar.slot", 13);
          setIfNotExists("Market.Zırhlar.maxPage", 1);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.slot",0);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.sellPrice",100);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.buyPrice",200);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.amount",1);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.data",0);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.page",1);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.sellable",false);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.buyable",false);
          setIfNotExists("Market." + "Zırhlar" + "." + "items." + "Elmas_Kask.material","DIAMOND_HELMET");
          saveAsync(plugin);
        }
      }
    });
  }

  @Override
  public List<String> getKeys(final String path, final boolean deep) {
    return super.getKeys(path, deep);
  }

  @Override
  public boolean isSet(final String path) {
    return super.isSet(path);
  }

  @Override
  public void setIfNotExists(final String path, final Object o) {
    super.setIfNotExists(path, o);
  }


  @Override
  public void load(final Plugin plugin) {
    super.loadYML();
  }

  @Override
  public FileConfiguration getConfig() {
    return super.getConfiguration();
  }
}
