package com.favorite.wick.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.favorite.wick.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        Class classTwo = ReflectClass.class;
        try {
            Class classThree = Class.forName("com.favorite.wick.annotation.ReflectClass");
            Log.d("WICKSIR", classThree.getPackage().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("WICKSIR", classTwo.getPackage().getName());
        reflect(classTwo);
    }

    public void reflect(Class classTwo) {
        Field[] fields = classTwo.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String modifier = Modifier.toString(field.getModifiers()); //获取每个
            Class type = field.getType(); //获取字段的数据类型所对应的Class对象
            Log.d("WICKSIR", "fieldName:" + fieldName + "\n" + "modifier:" + modifier + "\n" + "Class type:" + type);

        }
        Constructor[] constructors = classTwo.getConstructors();
        for (int j = 0; j < constructors.length; j++) {
            String constructName = constructors[j].getName();
            String modifiedNAme = Modifier.toString(constructors[j].getModifiers());
            Log.d("WICKSIR", "constructName:" + constructName);
            Log.d("WICKSIR", "modifiedNAme:" + modifiedNAme);
            Class[] paramTypes = constructors[j].getParameterTypes(); //获取构造方法中的参数
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    Log.d("WICKSIR", "几个构造函数：" + paramTypes.length);
                }
                if (paramTypes[i].isArray()) {
                    System.out.println(paramTypes
                            [i].getComponentType().getName() + "[]");
                } else {
                    System.out.print(paramTypes[i].getName());
                    Log.d("WICKSIR", "paramTypes" + i + ":" + paramTypes[i].getName());
                }
            }
        }
    }
}
