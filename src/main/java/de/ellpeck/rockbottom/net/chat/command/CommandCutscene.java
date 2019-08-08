package de.ellpeck.rockbottom.net.chat.command;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.net.packet.toclient.PacketCutscene;

public class CommandCutscene extends Command {
    public CommandCutscene() {
        super(ResourceName.intern("cutscene"), "/cutscene play <player> <duration>", 1);
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {

        if (args.length >= 1) {
            String cmd = args[0].toLowerCase();

            if (cmd.equals("play")) {
                if (args.length == 3) {
                    AbstractEntityPlayer player = game.getWorld().getPlayer(args[1]);
                    if (player != null) {
                        int duration = Integer.parseInt(args[2]);
                        IPacket packet = new PacketCutscene(duration);
                        if (sender.getWorld().isServer()) {
                            player.sendPacket(packet);
                        } else {
                            packet.handle(game, null);
                        }
                        return new ChatComponentText(FormattingCode.GREEN + "Started Cutscene!");
                    } else {
                        return new ChatComponentText(FormattingCode.RED + "Couldn't find player with name " + args[1] + '!');
                    }
                } else {
                    return new ChatComponentText(FormattingCode.RED + "Invalid Arguments! Expected /cutscene play <player> <duration>");
                }
            } else {
                return new ChatComponentText(FormattingCode.RED + "Invalid Subcommand!");
            }
        } else {
            return new ChatComponentText(FormattingCode.RED + "You must enter a subcommand! Valid options are 'play'.!");
        }
    }

    @Override
    public int getMaxArgumentAmount() {
        return 3;
    }
}
