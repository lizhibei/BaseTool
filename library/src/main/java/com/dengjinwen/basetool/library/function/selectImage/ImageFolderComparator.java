package com.dengjinwen.basetool.library.function.selectImage;

import java.util.Comparator;

public class ImageFolderComparator implements Comparator<ImageFloder> {
    @Override
    public int compare(ImageFloder o1, ImageFloder o2) {
        if(o1.getCount()<o2.getCount()){
            return 1;
        }else if(o1.getCount()==o2.getCount()){
            return 0;
        }else {
            return -1;
        }
    }
}
