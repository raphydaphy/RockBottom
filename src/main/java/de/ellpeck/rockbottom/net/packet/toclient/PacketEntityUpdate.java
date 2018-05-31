package de.ellpeck.rockbottom.net.packet.toclient;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Direction;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketEntityUpdate implements IPacket{

    private UUID uniqueId;
    private double x;
    private double y;
    private double motionX;
    private double motionY;
    private Direction facing;
    private boolean collidedHor;
    private boolean collidedVert;
    private boolean onGround;

    public PacketEntityUpdate(UUID uniqueId, double x, double y, double motionX, double motionY, Direction facing, boolean collidedHor, boolean collidedVert, boolean onGround){
        this.uniqueId = uniqueId;
        this.x = (float)x;
        this.y = (float)y;
        this.motionX = (float)motionX;
        this.motionY = (float)motionY;
        this.facing = facing;
        this.collidedHor = collidedHor;
        this.collidedVert = collidedVert;
        this.onGround = onGround;
    }

    public PacketEntityUpdate(){

    }

    @Override
    public void toBuffer(ByteBuf buf){
        buf.writeLong(this.uniqueId.getMostSignificantBits());
        buf.writeLong(this.uniqueId.getLeastSignificantBits());
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.motionX);
        buf.writeDouble(this.motionY);
        buf.writeInt(this.facing.ordinal());
        buf.writeBoolean(this.collidedHor);
        buf.writeBoolean(this.collidedVert);
        buf.writeBoolean(this.onGround);
    }

    @Override
    public void fromBuffer(ByteBuf buf){
        this.uniqueId = new UUID(buf.readLong(), buf.readLong());
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.motionX = buf.readDouble();
        this.motionY = buf.readDouble();
        this.facing = Direction.DIRECTIONS[buf.readInt()];
        this.collidedHor = buf.readBoolean();
        this.collidedVert = buf.readBoolean();
        this.onGround = buf.readBoolean();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context){
        if(game.getWorld() != null){
            Entity entity = game.getWorld().getEntity(this.uniqueId);
            if(entity != null){
                entity.motionX = this.motionX;
                entity.motionY = this.motionY;
                entity.facing = this.facing;
                entity.collidedHor = this.collidedHor;
                entity.collidedVert = this.collidedVert;
                entity.onGround = this.onGround;
                entity.setBoundsOrigin(this.x, this.y);
            }
        }
    }
}
