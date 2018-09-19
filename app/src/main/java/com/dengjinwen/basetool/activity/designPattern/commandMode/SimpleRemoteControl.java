package com.dengjinwen.basetool.activity.designPattern.commandMode;

/**
 * 调用者 Invoker
 */
public class SimpleRemoteControl {

    private  Command slot;

    public SimpleRemoteControl() {
    }

    public void setCommand(Command command){
        slot=command;
    }

    public void buttonWasPressed(){
        slot.execute();
    }
}
