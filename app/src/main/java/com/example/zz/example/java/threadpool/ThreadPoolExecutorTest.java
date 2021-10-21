package com.example.zz.example.java.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhuozhang6
 * @date: 2021/10/21
 * @email: zhuozhang6@iflytek.com
 * @Description:
 *
 *     public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler) {
 *
 *                               }
 * 上面是线程池基础构造方法，其他线程池最终都通过此构造方法进行生成配置
 * corePoolSize：核心线程数，核心线程在使用时才会建立，一旦建立会一直存在
 * maximumPoolSize：最大线程数，最大线程数 - 核心线程数 = 临时线程数
 * keepAliveTime：临时线程空闲存活时间，超时会被停止
 * unit：临时线程超时单位
 * workQueue：缓存队列，
 * threadFactory：线程工厂
 * handler：拒绝测略
 * <p>
 * 线程池执行流程：
 * 1、加入新任务时，运行线程数如果小于corePoolSize，新建线程完成任务
 * 2、加入新任务时，运行线程数大于等于corePoolSize，加入队列，而不是新建线程
 * 3、加入新任务时，队列已满时，新建临时线程
 * 4、加入新任务时，新建临时线程会超过临时线程数，任务被拒绝，怎么拒绝由拒绝测略限定
 * <p>
 * 所以缓存队列能缓存多少很重要，常用的缓存队列：
 * SynchronousQueue：阻塞队列，容量只有1的队列，必须先移除才能添加新的内容
 * ArrayBlockingQueue：有界队列，需要设置队列容量大小，超过容量流程走第三第四步
 * LinkedBlockingQueue：无界队列，可以无限向队列中添加任务，直到内存溢出。使用这个队列maximumPoolSize和keepAliveTime就没用了，因为流程不会到第三部
 * PriorityBlockingQueue：优先级队列，线程池会选取优先级高的线程进行执行，队列中的元素必须实现comparable接口
 * <p>
 * 拒绝测略：
 * AbortPolicy：默认测略，添加失败会抛出RejectedExecutionException
 * DiscardPolicy：添加失败，则放弃，不抛任何异常
 * DiscardOldestPolicy：添加失败，会将队列中最早添加的元素移除，再尝试添加，如果失败则按测略不断重试
 * CallerRunsPolicy：添加失败，主线程会自己调用执行器中的execute来执行更改任务
 * 自定义测略
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {

        //基础构造方法构造线程池
        baseExample();

        //使用JDK提供的快捷构造方案构造线程池，所有的快捷线程池都是对基础线程池的包装，自动给预置了不同参数而已
        example();
    }

    private static void baseExample() {

        /**
         *  SynchronousQueue 假设所有加入的任务都不会执行完，举例如下：
         *  从初始化的参数看，这个线程池支持2个核心线程，1个临时线程，临时线程空闲60s会回收，队列一个时间点只能添加一个任务
         *  首先加入任务A，如果以后不加入任务，则这个线程池始终只有一个线程运行，其他情况也是一样，以后不举例；
         *  再加入任务B，此时核心线程（corePoolSize）还没用完，会新建核心线程；
         *  再加入任务C，此时核心线程已用完，根据流程2，加入队列SynchronousQueue中等待
         *  再加入任务D，此时SynchronousQueue已经有一个成员，未处理完前不能再加入，所以，新建一个临时线程
         *  再加入任务E，此时临时线程已用完，只能抛出异常
         */
        ThreadPoolExecutor synchronousExecutor = new ThreadPoolExecutor(
                2,
                3,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());

        /**
         *  ArrayBlockingQueue 条件同上，举例如下：
         *  从初始化的参数看，这个线程池支持2个核心线程，1个临时线程，临时线程空闲60s会回收，队列可以添加2个任务
         *  首先加入任务A，新建线程
         *  再加入任务B，此时核心线程（corePoolSize）还没用完，会新建核心线程；
         *  再加入任务C，此时核心线程已用完，根据流程2，加入队列ArrayBlockingQueue中等待
         *  再加入任务D，此时ArrayBlockingQueue已经有一个成员，容量是2个，所以可以接受D
         *  再加入任务E，此时ArrayBlockingQueue容量已用完，未处理完前不能再加入，所以，新建一个临时线程
         *  再加入任务F,此时临时线程已用完，只能抛出异常
         */
        ThreadPoolExecutor arrayBlockingExecutor = new ThreadPoolExecutor(
                2,
                3,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2));

        /**
         *  LinkedBlockingQueue 条件同上，举例如下：
         *  从初始化的参数看，这个线程池支持2个核心线程，1个临时线程，临时线程空闲60s会回收，队列可以添加无数个任务，
         *  实际当队列可以无线添加时，maximumPoolSize和keepAliveTime设置多少就没有意义了，因为会一直使用队列不会使用临时线程
         *  首先加入任务A，新建线程
         *  再加入任务B，此时核心线程（corePoolSize）还没用完，会新建核心线程；
         *  再加入任务C，此时核心线程已用完，根据流程2，加入队列LinkedBlockingDeque中等待
         *  再加入任务D，加入队列LinkedBlockingDeque中等待
         *  再加入任务E，加入队列LinkedBlockingDeque中等待
         *  再加入任务F，加入队列LinkedBlockingDeque中等待
         *  。。。
         */
        ThreadPoolExecutor linkedBlockingExecutor = new ThreadPoolExecutor(
                2,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
    }

    private static void example() {

        /**
         *  单线程线程池
         *  只会用唯一的线程执行任务，所有的任务都会按照顺序一个一个执行，可以无限添加，不会拒绝，直到内存溢出
         *  从构造函数看出，使用的是LinkedBlockingQueue，所以对列是无线长的，因此设置maximumPoolSize和keepAliveTime也都是不会用到的
         *  new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
         */
        ExecutorService singleService = Executors.newSingleThreadExecutor();


        /**
         *  固定大小线程池
         *  与单线程线程池相比，就是多了核心线程数，指定多少就有多少核心线程数，其他表现同单线程池
         *  new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
         */
        ExecutorService fixedService = Executors.newFixedThreadPool(3, null);


        /**
         *  无界线程池
         *  核心线程数为0，所以不会有常驻核心线程，都是临时线程，执行完后60s就会被杀掉回收。最大线程数是MAX_VALUE，所以可以无限新建临时线程，知道内存溢出
         *  new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
         */
        ExecutorService cachedService = Executors.newCachedThreadPool();


        /**
         *  周期线程池
         *  任务立即提交给线程池，线程池安排线程在指定时间后正式开始运作，运作以后保持正常节奏，相当于多线程版本的Timer
         *  super(corePoolSize, Integer.MAX_VALUE, DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS, new DelayedWorkQueue());
         */
        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(2);
        scheduledService.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 5, TimeUnit.SECONDS);
    }


}
