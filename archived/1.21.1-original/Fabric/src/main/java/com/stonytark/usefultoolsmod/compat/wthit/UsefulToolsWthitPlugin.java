package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserBlockEntity;
import mcp.mobius.waila.api.ICommonRegistrar;
import mcp.mobius.waila.api.IWailaCommonPlugin;

public class UsefulToolsWthitPlugin implements IWailaCommonPlugin {

    @Override
    public void register(ICommonRegistrar registrar) {
        registrar.blockData(new SpectralInfuserDataProvider(), SpectralInfuserBlockEntity.class);
    }
}
