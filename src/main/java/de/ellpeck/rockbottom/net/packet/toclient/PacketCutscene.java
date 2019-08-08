package de.ellpeck.rockbottom.net.packet.toclient;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.render.cutscene.Cutscene;
import de.ellpeck.rockbottom.render.cutscene.CutsceneManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketCutscene implements IPacket {

    private int duration;

    public PacketCutscene(int duration) {
        this.duration = duration;
    }

    public PacketCutscene() {
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeInt(this.duration);
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        this.duration = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        CutsceneManager.getInstance().startCutscene(new Cutscene(duration));
    }
}
