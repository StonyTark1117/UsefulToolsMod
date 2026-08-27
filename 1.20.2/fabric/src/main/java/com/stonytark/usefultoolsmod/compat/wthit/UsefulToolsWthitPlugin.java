package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;

public class UsefulToolsWthitPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockData(new SpectralInfuserDataProvider(), SpectralInfuserBlock.class);
        registrar.addComponent(new SpectralInfuserComponentProvider(), TooltipPosition.BODY,
                SpectralInfuserBlock.class);
        registrar.addComponent(new GhostComponentProvider(), TooltipPosition.BODY, GhostEntity.class);
        registrar.addComponent(new WraithComponentProvider(), TooltipPosition.BODY, WraithEntity.class);
    }
}
