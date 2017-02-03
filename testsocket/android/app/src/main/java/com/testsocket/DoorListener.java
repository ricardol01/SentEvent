package com.testsocket;

import java.util.EventListener;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface DoorListener extends EventListener {
    public void doorEvent(DoorEvent event);
}