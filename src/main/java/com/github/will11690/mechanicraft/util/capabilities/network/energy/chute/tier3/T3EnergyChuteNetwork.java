package com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier3;

import java.util.Arrays;
import java.util.HashSet;

import com.github.will11690.mechanicraft.blocks.chute.energychute.tier3.TileEntityT3EnergyChute;
import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class T3EnergyChuteNetwork {

	private final HashSet<BlockPos>	connections;
	private final World	world;

	public T3EnergyChuteNetwork(final World world) {
		
		this.connections = new HashSet<>(0);
		this.world = world;
	}

	public HashSet<BlockPos> getConnections() {
		
		return this.connections;
	}

	public T3EnergyChuteNetwork add(final BlockPos connection) {
		
		this.getConnections().add(connection);
		return this;
	}

	public T3EnergyChuteNetwork remove(final BlockPos connection) {
		
		this.getConnections().remove(connection);
		return this;
	}

	@Override
	public boolean equals(final Object obj) {
		
		return (obj instanceof T3EnergyChuteNetwork) && ((T3EnergyChuteNetwork) obj).getConnections().equals(this.getConnections());
	}

	@Override
	public int hashCode() {
		
		return this.getConnections().hashCode();
	}

	public void distributeEnergy(final BlockPos... dontDistribute) {
		
		int networkEnergy = this.getNetworkEnergy();

		final HashSet<BlockPos> hashedDontDistribute = new HashSet<>(Arrays.asList(dontDistribute));

		final HashSet<IEnergyStorage> storages = new HashSet<>();

		for (final BlockPos pos : this.getConnections()) {

			try {
				if (!hashedDontDistribute.contains(pos)) {

					if(this.world.getBlockEntity(pos) instanceof TileEntityT3EnergyChute) {

						if(this.world.getBlockEntity(pos).getCapability(CapabilityEnergy.ENERGY).isPresent()) {

							IEnergyStorage storage = this.world.getBlockEntity(pos).getCapability(CapabilityEnergy.ENERGY).orElseThrow(IllegalStateException::new);
							storages.add(storage);
						}
					}
				}
				
			} catch (final Exception e) {
				
				e.printStackTrace();
			}
		}

		int repetitions = 0;
		while(networkEnergy > 0) {
			
			repetitions++;

			final int[] sets = splitIntoParts(networkEnergy, storages.size());

			if(repetitions >= 5) {
				break;
			}

			for(int i = 0; i < storages.size(); i++) {
				
				networkEnergy -= storages.toArray(new MechaniCraftEnergyStorage[0])[i].setEnergy(sets[i]);
			}

		}
	}
	
	public static int[] splitIntoParts(final int whole, final int parts) {
		
		final int[] arr = new int[parts];
		int remain = whole;
		int partsLeft = parts;
		
		for (int i = 0; partsLeft > 0; i++) {
			
			final int size = ((remain + partsLeft) - 1) / partsLeft;
			arr[i] = size;
			remain -= size;
			partsLeft--;
		}
		
		return arr;
	}

	public int getNetworkEnergy() {
		
		int networkEnergy = 0;
		for (final BlockPos pos : this.getConnections()) {
			
			try {
				final TileEntity tile = this.world.getBlockEntity(pos);
				
				if(tile instanceof TileEntityT3EnergyChute) {
					
					if(tile.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

						IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY).orElseThrow(IllegalStateException::new);
						networkEnergy += storage.getEnergyStored();
					}
				}
				
			} catch (final Exception e) {
				
				e.printStackTrace();
			}
		}
		return networkEnergy;
	}
}