package skywolf46.extrautility.logger;

import skywolf46.extrautility.abstraction.AbstractStorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DateLogger extends Thread {
    private SimpleDateFormat dateFormat;
    private BufferedWriter writer;
    private List<String> textToWrite = new ArrayList<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private long delay;
    private AtomicBoolean enabled = new AtomicBoolean(true);

    public DateLogger(File target, String format, long writingDelay) {
        this.dateFormat = new SimpleDateFormat(format);
        this.delay = writingDelay;
        try {
            if (!target.exists()) {
                if (target.getParentFile() != null)
                    target.getParentFile().mkdirs();
                target.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(target, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDaemon(false);
        start();
    }

    public DateLogger(File target, String format) {
        this(target, format, 200L);
    }

    public void stopLog() {
        enabled.set(false);
    }

    public void append(String text) {
        String txt = dateFormat.format(new Date());
        lock.writeLock().lock();
        textToWrite.add("[" + txt + "] " + text);
        lock.writeLock().unlock();
    }

    private void write() throws IOException {
        lock.writeLock().lock();
        List<String> appender = new ArrayList<>(textToWrite);
        textToWrite.clear();
        lock.writeLock().unlock();
        for (String x : appender) {
            System.out.println("Appended" + x);
            writer.append(x);
            writer.newLine();
        }
        writer.flush();
        appender.clear();
    }

    @Override
    public void run() {
        while (enabled.get()) {
            try {
                write();
                Thread.sleep(delay);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        try {
            write();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
