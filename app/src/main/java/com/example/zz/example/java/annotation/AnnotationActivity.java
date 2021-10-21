package com.example.zz.example.java.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zz.example.R;
import com.zz.zzbutterknife.ZzButterKnife;
import com.zz.zzbutterknifeannotation.ZzBindView;

/**
 * 注解：注解就相当于注释，本身没有意义，只是作为标记，这些标记搭配其他技术如APT、反射、插桩等技术就可以实现很多功能
 * 注解分为：元注解（@Target、@Retention）、内置注解（@Override、@Deprecated）、自定义注解（@ZzBindView）。注解注解的注解就是元注解
 * RetentionPolicy.SOURCE ：被此注解标注的自定义注解保留在源码级别，会被编译器忽略
 * RetentionPolicy.CLASS ：编译级别，在编译时被编译器保留，但java虚拟机（jvm）会忽略
 * RetentionPolicy.RUNTIME : 标记的注解由JVM保留，因此运行时环境可以使用它
 *
 * 三种元注解对应的使用场景
 * 1、SOURCE注解+APT：如butterknife，下面ZzBindView就是使用注解加APT技术实现findViewById功能
 * 2、CLASS注解+插桩：
 * 3、RUNTIME注解+反射：
 */

public class AnnotationActivity extends AppCompatActivity {

    @ZzBindView(R.id.annotation_text)
    TextView annotationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ZzButterKnife.bind(this);
        annotationText.setText("注解获取成功！");
    }
}