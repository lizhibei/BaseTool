package com.dengjinwen.basetool.library.function.selectImage;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemEntity implements Parcelable {
    /**
     * 视频文件路径
     */
    private String path;
    /**
     * 视频持续时间
     */
    private int duration;
    /**
     * 视频缩略图路径或者图片路径
     */
    private String thumb;

    private boolean isSelect;

    public ItemEntity() {
    }

    protected ItemEntity(Parcel in) {
        path = in.readString();
        duration = in.readInt();
        thumb = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(duration);
        dest.writeString(thumb);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemEntity> CREATOR = new Creator<ItemEntity>() {
        @Override
        public ItemEntity createFromParcel(Parcel in) {
            return new ItemEntity(in);
        }

        @Override
        public ItemEntity[] newArray(int size) {
            return new ItemEntity[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
