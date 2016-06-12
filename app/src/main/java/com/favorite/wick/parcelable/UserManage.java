package com.favorite.wick.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Weimin on 6/1/2016.
 */
public class UserManage implements Parcelable {
   public int age;
    public String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
    }

    public UserManage() {
    }

    protected UserManage(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<UserManage> CREATOR = new Parcelable.Creator<UserManage>() {
        @Override
        public UserManage createFromParcel(Parcel source) {
            return new UserManage(source);
        }

        @Override
        public UserManage[] newArray(int size) {
            return new UserManage[size];
        }
    };
}
