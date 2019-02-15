package com.dengjinwen.basetool.library.tool.comparator;

import com.dengjinwen.basetool.library.function.selectImage.ItemEntity;

import java.io.File;
import java.util.Comparator;

/**
 * 按文件的修改时间排序
 */
public class FileModityTimeComparator implements Comparator<ItemEntity> {

    @Override
    public int compare(ItemEntity o1, ItemEntity o2) {
        File o1File=new File(o1.getPath());
        File o2File=new File(o2.getPath());
        //修改时间近的在前面
        if(o1File.lastModified()>o2File.lastModified()){
            return -1;
        }else if(o1File.lastModified()==o2File.lastModified()){
            return 0;
        }else {
            return 1;
        }
    }
}
