package com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier5;

import java.util.HashSet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityT5EnergyCubeNetwork {

	@CapabilityInject(T5EnergyCubeNetworkList.class)
	public static Capability<T5EnergyCubeNetworkList> T5_NETWORK_LIST = null;

	public static void register() {

		CapabilityManager.INSTANCE.register(T5EnergyCubeNetworkList.class, new IStorage<T5EnergyCubeNetworkList>() {
			
			@Override
			public ListNBT writeNBT(final Capability<T5EnergyCubeNetworkList> capability, final T5EnergyCubeNetworkList instance, final Direction side) {
				
				final ListNBT nbtTagList = new ListNBT();

				for (final BlockPos pos : instance.getConnections()) {
					final CompoundNBT compound = new CompoundNBT();
					compound.putLong("pos", pos.asLong());
					nbtTagList.add(compound);
				}

				return nbtTagList;
			}

			@Override
			public void readNBT(final Capability<T5EnergyCubeNetworkList> capability, final T5EnergyCubeNetworkList instance, final Direction side, final INBT nbt) {

				final T5EnergyCubeNetworkList energyNetworkList = instance;

				final ListNBT tagList = (ListNBT) nbt;

				final HashSet<BlockPos> connections = new HashSet<>();

				for (int i = 0; i < tagList.size(); i++) {
					
					final CompoundNBT compound = tagList.getCompound(i);

					final long posLong = compound.getLong("pos");
					final BlockPos pos = BlockPos.of(posLong);

					connections.add(pos);
				}
				
				energyNetworkList.getConnections().clear();
				energyNetworkList.getConnections().addAll(connections);

				energyNetworkList.refreshNetworks();
			}
			
		}, () -> new T5EnergyCubeNetworkList(null));
	}
}