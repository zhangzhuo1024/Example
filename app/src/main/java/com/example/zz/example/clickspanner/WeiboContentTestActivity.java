package com.example.zz.example.clickspanner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.zz.example.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/3/6
 */
public class WeiboContentTestActivity extends Activity {
    private static final String CONTENT = "点击#HELLO#跳转到百度";
    private static final Pattern topicPattern = Pattern.compile("#\\w+#");
    TextView mTv;
    TextView textview;
    TextView mTv3;
    private SpannableString msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_url);
        mTv = (TextView) findViewById(R.id.tv1);
        textview = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);

        //最简单的富文本实现
        clickToHao123();
        //有回调，有文字识别，有设置的富文本实现
        clickToBaidu();

        clickAll();
    }

    private void clickAll() {
        //创建一个 SpannableString对象

        msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x8x3电话邮件网站短信彩信地图X轴综合/bot");

//设置字体(default,default-bold,monospace,serif,sans-serif)

        msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//设置字体大小（绝对值,单位：像素）

        msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        msp.setSpan(new AbsoluteSizeSpan(20,true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。

//设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍

        msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //0.5f表示默认字体大小的一半

        msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体大小的两倍

//设置字体前景色

        msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置前景色为洋红色

//设置字体背景色

        msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置背景色为青色

//设置字体样式正常，粗体，斜体，粗斜体

        msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //正常

        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体

        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //斜体

        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗斜体

//设置下划线
        msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//设置删除线
        msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//设置上下标
        msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //下标msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //上标

//超级链接（需要添加setMovementMethod方法附加响应）
        msp.setSpan(new URLSpan("tel:10086"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //电话msp.setSpan(new URLSpan("123456789@qq.com"), 39, 41,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //邮件

        msp.setSpan(new URLSpan("https://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //网络

        msp.setSpan(new URLSpan("sms:10086"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //短信 使用sms:或者smsto: 

        msp.setSpan(new URLSpan("mms:10086"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //彩信 使用mms:或者mmsto:

        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //地图

//设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变//设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
        ColorStateList csllink = null;
        ColorStateList csl = null;

// XmlResourceParser xppcolor=getResources().getXml (R.color.color);

// try {

// csl= ColorStateList.createFromXml(getResources(),xppcolor);

// }catch(XmlPullParserException e){

// e.printStackTrace();

// }catch(IOException e){

// e.printStackTrace();

// }



// XmlResourceParser xpplinkcolor=getResources().getXml(R.color.linkcolor);

// try {

// csllink= ColorStateList.createFromXml(getResources(),xpplinkcolor);

// }catch(XmlPullParserException e){

// e.printStackTrace();

// }catch(IOException e){

// e.printStackTrace();

// }
        msp.setSpan(new TextAppearanceSpan("monospace",android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51,53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//设置项目符号msp.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH,Color.GREEN),0,msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色

//设置图片
// Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
// msp.setSpan(new   ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv3.setText(msp);
        mTv3.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void clickToHao123() {
        //底部提示语  快速咨询为超链
        SpannableString str = new   SpannableString("点击亮着的textview跳转到hao123");
        //设置属性
        str.setSpan(new   MyCheckTextView(this), 4, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(str);
        textview.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        textview.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }

    private void clickToBaidu() {
        mTv.setText(CONTENT);
        SpannableString ss = new   SpannableString(mTv.getText());

        setkeywordClickable(mTv, ss, topicPattern, new   weiboclickspan(
                new   onTextViewClickListener() {
                    @Override
                    public void clickTextView() {
                        Intent it = new   Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.baidu.com"));
                        startActivity(it);
                    }

                    @Override
                    public void setStyle(TextPaint ds) {
                        ds.setColor(Color.BLUE);// 颜色
                        ds.setUnderlineText(false);// 是否有下划线
                    }
                }));
    }


    // 设置某个具体关键字被点击
    public void setkeywordClickable(TextView tv, SpannableString ss,
                                    Pattern pt, ClickableSpan cs) {
        Matcher mc = pt.matcher(ss.toString());
        while (mc.find()) {
            String key = mc.group();
            if (!"".equals(key)) {
                int start = ss.toString().indexOf(key);
                int end = start + key.length();
                setClickTextView(tv, ss, start, end, cs);
            }
        }
    }

    // 设置textview中的字段可点击
    public void setClickTextView(TextView tv, SpannableString ss, int start,
                                 int end, ClickableSpan cs) {
        ss.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface onTextViewClickListener {
        public void clickTextView();
        public void setStyle(TextPaint ds);
    }
}
