package tr.beyazpolis.talemarket.commands;

import com.sun.istack.internal.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tr.beyazpolis.talemarket.instance.ShopMainMenu;

public final class ShopCommand implements CommandExecutor {

  private final ShopMainMenu shop;

  public ShopCommand(final ShopMainMenu shop){
    this.shop = shop;
  }

  @Override
  public boolean onCommand(@NotNull final CommandSender sender, final Command cmd, final String label, final String[] args) {

    if (!(sender instanceof Player)){
      return true;
    }

    final Player player = (Player) sender;

    switch (cmd.getName()){
      case "reloadmarket":
        if (player.isOp()) {
          shop.reload();
          player.sendMessage("reload başarılı");
        }
        break;
      case "market":
        player.openInventory(shop.getInventory());
        break;
    }
    return true;
  }
}
