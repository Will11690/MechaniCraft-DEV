package com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier3;

import java.util.HashSet;
import java.util.Iterator;

import com.github.will11690.mechanicraft.blocks.chute.energychute.tier3.TileEntityT3EnergyChute;
import com.github.will11690.mechanicraft.network.PacketHandler;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier3.ClientboundT3EnergyChuteNetworkPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

public class T3EnergyChuteNetworkList {

	private final HashSet<BlockPos>	connections;
	private final HashSet<T3EnergyChuteNetwork>	networks;
	final World world;
	
	public T3EnergyChuteNetworkList(final World world) {
		
		this.connections = new HashSet<>(0);
		this.networks = new HashSet<>(0);
		this.world = world;
	}

	public HashSet<BlockPos> getConnections() {
		
		return this.connections;
	}

	public void addConnection(final BlockPos pos) {
		
		this.getConnections().add(pos);
		this.onChange(pos);
	}

	public HashSet<T3EnergyChuteNetwork> getNetworks() {
		
		return this.networks;
	}

	public void tick() {
		
		if (this.world.isClientSide) {
			
			return;
		}
		
		if(this.shouldRefreshConnections()) {
			
			this.refreshConnections();
		}
		
		if(this.shouldRefreshNetworks()) {
			
			this.refreshNetworks();
		}
		
		if(this.shouldDistributeEnergy()) {
			
			for(final T3EnergyChuteNetwork network : this.networks) {
				
				network.distributeEnergy();
			}
		}
	}

	private boolean shouldRefreshConnections() {
		
		final int check = (int) (this.getConnections().size() / 1000f);
		
		if(check == 0) {
			
			return true;
		}
		
		return (this.world.getDayTime() % check) == 0;
	}

	private boolean shouldRefreshNetworks() {
		
		final int check = (int)(this.getConnections().size() / 100f);
		
		if(check == 0) {
			
			return true;
		}
		
		return (this.world.getDayTime() % check) == 0;
	}

	private boolean shouldDistributeEnergy() {
		
		final int check = (int)(this.getConnections().size() / 10f);
		
		if(check == 0) {
			
			return true;
		}
		
		return (this.world.getDayTime() % check) == 0;
	}

	public void refreshNetworks() {
		
		this.networks.clear();
		final HashSet<BlockPos> done = new HashSet<>(0);

		for(final BlockPos pos : this.getConnections()) {
			
			if(!done.contains(pos)) {
				
				done.add(pos);

				final T3EnergyChuteNetwork network = new T3EnergyChuteNetwork(this.world);
				this.generateNetwork(network, pos);
				done.addAll(network.getConnections());
				this.networks.add(network);
			}
		}
	}

	private void generateNetwork(final T3EnergyChuteNetwork network, final BlockPos pos) {

		if(network.getConnections().size() > 1024) {
			
			return;
		}

		final TileEntity tile = this.world.getBlockEntity(pos);
		
		if(tile == null) {
			
			return;
		}
		
		if(!(tile instanceof TileEntityT3EnergyChute)) {
			
			return;
		}

		network.add(pos);

		for(final Direction facing : Direction.values()) {
			
			final BlockPos offset = pos.relative(facing);
			
			if(!network.getConnections().contains(offset)) {
				
				this.generateNetwork(network, offset);
			}
		}
	}

	public void refreshConnections() {
		
		if(!this.world.isClientSide) {
			
			Iterator<BlockPos> connections = this.getConnections().iterator();
		    while(connections.hasNext()) {
		    	
		    	BlockPos pos = connections.next();
		    	
		    	final TileEntity tile = this.world.getBlockEntity(pos);
		    	
		    	if(tile == null) {
					
		    		connections.remove();
		    		this.onChange(pos);
					return;
		    	}

		    	if(!(tile instanceof TileEntityT3EnergyChute)) {
					
		    		connections.remove();
		    		this.onChange(pos);
					return;
				}
		    }
		}
	}

	public void onChange(final BlockPos pos) {
		
		if(!this.world.isClientSide) {
			
			final INBT syncTag = CapabilityT3EnergyChuteNetwork.T3_NETWORK_LIST.writeNBT(this, null);
			
			for(final PlayerEntity player : this.world.players()) {
				
				if(player instanceof ServerPlayerEntity) {
					
					PacketTarget target = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player);
					PacketHandler.T3_ENERGY_NETWORK_CHUTE.send(target, new ClientboundT3EnergyChuteNetworkPacket(syncTag));
				}
			}
		}
	}
}