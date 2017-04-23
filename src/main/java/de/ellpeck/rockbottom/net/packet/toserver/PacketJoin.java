package de.ellpeck.rockbottom.net.packet.toserver;

import de.ellpeck.rockbottom.RockBottom;
import de.ellpeck.rockbottom.net.NetUtil;
import de.ellpeck.rockbottom.net.packet.IPacket;
import de.ellpeck.rockbottom.net.packet.toclient.PacketInitialServerData;
import de.ellpeck.rockbottom.world.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.newdawn.slick.util.Log;

import java.io.IOException;
import java.util.UUID;

public class PacketJoin implements IPacket{

    private String version;
    private UUID id;

    public PacketJoin(UUID id, String version){
        this.id = id;
        this.version = version;
    }

    public PacketJoin(){

    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException{
        buf.writeLong(this.id.getMostSignificantBits());
        buf.writeLong(this.id.getLeastSignificantBits());
        NetUtil.writeStringToBuffer(this.version, buf);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException{
        this.id = new UUID(buf.readLong(), buf.readLong());
        this.version = NetUtil.readStringFromBuffer(buf);
    }

    @Override
    public void handle(RockBottom game, ChannelHandlerContext context){
        game.scheduleAction(() -> {
            if(RockBottom.VERSION.equals(this.version)){
                if(game.world != null){
                    if(game.world.getPlayer(this.id) == null){
                        EntityPlayer player = game.world.createPlayer(this.id, context.channel());
                        game.world.addEntity(player);
                        player.sendPacket(new PacketInitialServerData(player, game.world.info));

                        Log.info("Player with id "+this.id+" joined, sending initial server data");
                    }
                    else{
                        Log.warn("Player with id "+this.id+" tried joining while already connected!");
                    }
                }
            }
            else{
                Log.warn("Player with id "+this.id+" tried joining with game version "+this.version+", server version is "+RockBottom.VERSION+"!");
            }
            return true;
        });
    }
}