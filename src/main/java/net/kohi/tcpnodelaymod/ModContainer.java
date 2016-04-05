package net.kohi.tcpnodelaymod;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

public class ModContainer extends DummyModContainer {

    public ModContainer() {
        super(new ModMetadata());

        ModMetadata meta = getMetadata();
        meta.modId = "TcpNoDelayMod";
        meta.name = "TcpNoDelayMod";
        meta.version = "2.0";
        meta.authorList = Arrays.asList("prplz", "Travisvv", "KohiBot");

    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
