package com.upokecenter.test;

import com.upokecenter.cbor.*;
import java.util.*;

public final class WatchThread {
private int delay=0;
private Object root=new Object();
private CBORObject co1=null;
private CBORObject co2=null;
private boolean done=false;
private final Thread th;
 public WatchThread() {
th=new Thread(new Runnable(){
 public void run(){
  while(true){
   synchronized(root){
    if(done)break;
    if(delay==5000){
     System.out.println("CBORObject cbor1=CBORObject.DecodeFromBytes("+
    TestCommon.ToByteArrayString(co1.EncodeToBytes())+");");
     System.out.println("CBORObject cbor2=CBORObject.DecodeFromBytes("+
    TestCommon.ToByteArrayString(co2.EncodeToBytes())+");");
    }
   }
   try {
   Thread.sleep(1000);
   } catch(InterruptedException ex){}
   synchronized(root){
    delay+=1000;
    delay=Math.min(delay,6000);
   }
  }
 }
});
 }
 public WatchThread start(){

synchronized(root){
 done=false;
 co1=null;
 co2=null;
 delay=0;
}
th.start();
 return this;
 }
 public void reset(CBORObject cbor1, CBORObject cbor2){

synchronized(root){
 co1=cbor1;
 co2=cbor2;
 delay=0;
}
}
 public void stop(){
 synchronized(root){ done=true; }
 }

}
