package com.testsocket;

import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ToastModuleTest extends ReactContextBaseJavaModule{
    ReactApplicationContext mReactContext;

    public ToastModuleTest(ReactApplicationContext reactContext){

        super(reactContext);
        mReactContext=reactContext;
    }

    @Override
    public  String getName(){
        return "ToastModuleTest";
    }
    @ReactMethod
    public  void testCallback(final Callback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    callback.invoke("Callback:"+ Thread.currentThread().getName()+"has slept 3ms.");
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @ReactMethod
    public void testPromise(final Promise promise)
    {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Thread.sleep(3000);
                    WritableMap map= Arguments.createMap();
                    map.putString("msg","Promise: "+Thread.currentThread().getName()+" has slept 3 ms.");
                    promise.resolve(map);
                }catch(InterruptedException e){
                    promise.reject("InterruptedException",e);
                }
            }
        }).start();
    }
    @ReactMethod
    public void testEvent()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("testEvent","Event: "+Thread.currentThread().getName()+" has slept 3 ms.");
                } catch (InterruptedException e){


                }
            }
        }).start();
    }
    @ReactMethod
    public void EventType1(String msg)
    {
//        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("EventToAndroid","111");
        Toast.makeText(getReactApplicationContext(),"Type 1 Event" + msg, Toast.LENGTH_LONG).show();
    }
    @ReactMethod
    public void EventType2(String msg)
    {
        Toast.makeText(getReactApplicationContext(),"Type 2 Event" + msg,Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void Eventdispatcher(String EventType, String Event)
    {
        switch(EventType){
            case "Type1":
                EventType1(Event);
                break;
            case "Type2":
                EventType2(Event);
                break;
            default:
                break;
        }
    }
}

