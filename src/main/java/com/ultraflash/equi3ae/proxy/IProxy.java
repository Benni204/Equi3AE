package com.ultraflash.equi3ae.proxy;

public interface IProxy 
{
    public abstract ClientProxy getClientProxy();
    public abstract void registerEventHandlers();
    public abstract void registerKeyBindings();



}
