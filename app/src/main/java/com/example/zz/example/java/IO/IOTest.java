package com.example.zz.example.java.IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author: zhuozhang6
 * @date: 2021/10/29
 * @email: zhuozhang6@iflytek.com
 * @Description: JAVA的IO操作
 * 参考：https://www.cnblogs.com/fysola/p/6123947.html
 * 归纳后只有两种：
 * 字节IO操作，InputStream、OutputStream
 * 字符IO操作，Reader、Writer，扩展类FileReader/FileWriter
 * 在上面的基础上分别扩展出各种功能类：
 * FileInputStream/FileOutputStream，BufferedInputStream/BufferedOutputStream，StringBufferInputStream/StringBufferOutputStream 等
 * FileReader/FileWriter, StringReader/StringWriter, InputStreamReader/OutputStreamWriter 等
 * <p>
 * 其他主要流
 * 包装流：PrintStream/PrintWriter/Scanner
 * PrintStream可以封装（包装）直接与文件交互的节点流对象OutputStream, 使得编程人员可以忽略设备底层的差异，进行一致的IO操作。因此这种流也称为处理流或者包装流。
 * PrintWriter除了可以包装字节流OutputStream之外，还能包装字符流Writer
 * Scanner可以包装键盘输入，方便地将键盘输入的内容转换成我们想要的数据类型。
 * <p>
 * 字符串流：StringReader/StringWriter
 * 这两个操作的是专门操作String字符串的流，其中StringReader能从String中方便地读取数据并保存到char数组，而StringWriter则将字符串类型的数据写入到StringBuffer中（因为String不可写）。
 * <p>
 * 转换流：InputStreamReader/OutputStreamReader
 * 这两个类可以将字节流转换成字符流，被称为字节流与字符流之间的桥梁。我们经常在读取键盘输入(System.in)或网络通信的时候，需要使用这两个类
 * <p>
 * 缓冲流：BufferedReader/BufferedWriter ， BufferedInputStream/BufferedOutputStream
 * BufferedReader/BufferedWriter可以将字符流(Reader)包装成缓冲流，这是最常见用的做法。
 * 另外，BufferedReader提供一个readLine()可以方便地读取一行，而FileInputStream和FileReader只能读取一个字节或者一个字符，因此BufferedReader也被称为行读取器
 */
public class IOTest {
    public static void main(String[] args) {

        fileInputStreamTest();

        fileReader();

        //总体来讲，FileInputStream和FileReader都能读取文件，功能相同，FileInputStream用更小的细粒度进行处理，分得太小，所以中文可能乱码
    }

    private static void fileReader() {
        try {
            FileReader fileReader = new FileReader("app/src/assets/a.txt");
            // 这里我们新建了一个长度为2的字符数组，用来临时存储每次从文件中读取的字符数据，每个字符是用两个字节表示的，所有中文也可以表示
            char[] charBuf = new char[2];
            int length;
            while ((length = fileReader.read(charBuf)) > 0) {
                System.out.println("FileReader读取的文件数据：" + new String(charBuf, 0, length));
            }

            /**
             * 打印效果：
             * FileReader读取的文件数据：ab
             * FileReader读取的文件数据：c你
             * FileReader读取的文件数据：好1
             * FileReader读取的文件数据：23
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileInputStreamTest() {
        try {
            // a.txt中存储的是  abc你好123
            FileInputStream fileInputStream = new FileInputStream("app/src/assets/a.txt");
            // 这里我们新建了一个长度为2的字节数组，用来临时存储每次从文件中读取的字节数据
            byte[] byteBuf = new byte[2];
            int length;
            // while循环读取文件中的数据，每次读取byteBuf长度的数据(这里长度是2)，然后将读取的数据转化成字符串进行打印
            // 文件中的a b c 1 2 3  都分别用一个字节存储的，但是一个中文是两个字节存储的，所以就会出现乱码
            // while循环第一次读时，读两个字节，刚好a和b是两个字节，所以打印出ab
            // 第二次循环时，再次读取两个字节，读完c使用了一个字节，读"你"占用一个字节，但是"你"是中文，要多个字节才能表示，所以读"你"是没法正常一次读完的，所以第二次读完的数据去打印时，打印的乱码
            // 最后，虽然分段转化成字符串看是乱码的，但是，传输完成后，文件还是和原来一样的，只不过通过byte传输时是分开了的
            while ((length = fileInputStream.read(byteBuf)) > 0) {
//                System.out.println("FileInputStream读取的文件数据：" + byteBuf[0] + " " + byteBuf[1]);
                System.out.println("FileInputStream读取的文件数据：" + new String(byteBuf));
            }

            /**
             * 打印效果：
             * FileInputStream读取的文件数据：ab
             * FileInputStream读取的文件数据：c�
             * FileInputStream读取的文件数据：��
             * FileInputStream读取的文件数据：�
             * FileInputStream读取的文件数据：�1
             * FileInputStream读取的文件数据：23
             * 上面的中文表示的乱码，要区分内码外码编码方式，中文的字节数不同
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 中文汉字各类编码字节数：
     * 字节数 : 2;编码：GB2312
     * 字节数 : 2;编码：GBK
     * 字节数 : 2;编码：GB18030
     * 字节数 : 1;编码：ISO-8859-1
     * 字节数 : 3;编码：UTF-8
     * 字节数 : 4;编码：UTF-16
     * 字节数 : 2;编码：UTF-16BE
     * 字节数 : 2;编码：UTF-16LE
     */
}
