package com.dengjinwen.basetool.activity.liveData;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveDataBean extends ViewModel {

    private MutableLiveData<String> text;

    public MutableLiveData<String> getName() {
        if(text==null){
            text=new MutableLiveData<>();
        }
        return text;
    }
}
