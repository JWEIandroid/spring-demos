package com.example.thread.thread;

import java.io.*;
import java.util.*;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/8
 * @Time: 17:24
 * @Description: No Description
 */
public class LargeDataSortTest {
    static File file = new File("F:" + File.separator + "dataTest" + File.separator + "data.txt");
    static File file1 = new File("F:" + File.separator + "dataTest" + File.separator + "dataSorted.txt");

    public static void main(String[] args) throws Exception {
//        createData();
//        System.out.println("大文件写入成功");
//        separateFile();
//        System.out.println("文件拆分成功");
//
        //everySingleFileSort();
        //System.out.println("小文件排序完成");
        loadSameDataFromSingleFile();
        //mergeFile();
//        System.out.println("所有排序都已完成");

    }

    /**
     * 从小文件中读取相同记录
     */
    private static void loadSameDataFromSingleFile() throws Exception {

    }

    public static void createData() throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            bw.write(random.nextInt(Integer.MAX_VALUE) + "\r\n");
        }
        bw.close();
        fw.close();
    }

    public static void separateFile() throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = null;
        BufferedWriter bw = null;
        HashMap<Integer, FileWriter> fwList = new HashMap<Integer, FileWriter>();
        HashMap<Integer, BufferedWriter> bwList = new HashMap<Integer, BufferedWriter>();


        for (int i = 0; i < 20; i++) {
            fw = new FileWriter("F:" + File.separator + "dataTest" + File.separator + "data" + i + ".txt");
            bw = new BufferedWriter(fw);
            fwList.put(i, fw);
            bwList.put(i, bw);
        }

        while (br.ready()) {
            Integer readNumber = Integer.valueOf(br.readLine());
            int a = readNumber.hashCode() % 20;
            BufferedWriter bufferedWriter = bwList.get(a);
            bufferedWriter.write(readNumber + "\r\n");
        }

        //遍历关闭所有子文件流
        /*for (Iterator iterator = bwList.iterator(); iterator.hasNext(); ) {
            BufferedWriter it = (BufferedWriter) iterator.next();
            it.close();
        }*/
        for (Map.Entry<Integer, BufferedWriter> a : bwList.entrySet()) {
            a.getValue().close();
        }

        /*for (Iterator iterator = fwList.iterator(); iterator.hasNext(); ) {
            FileWriter it = (FileWriter) iterator.next();
            it.close();
        }*/
        for (Map.Entry<Integer, FileWriter> a : fwList.entrySet()) {
            a.getValue().close();
        }

        br.close();
        fr.close();
    }

    //对每个小文件进行排序
    public static void everySingleFileSort() throws Exception {
        LinkedList<Integer> numbers;
        for (int i = 0; i < 20; i++) {
            numbers = new LinkedList<Integer>();
            String path = "F:" + File.separator + "dataTest" + File.separator + "data" + i + ".txt";
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                numbers.add(Integer.parseInt(br.readLine()));
            }
            Collections.sort(numbers);
            numbersWrite(numbers, path);
            br.close();
            fr.close();
        }
    }

    //将排好序的没个文件写回到小文件中
    public static void numbersWrite(LinkedList<Integer> numbers, String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        for (Iterator<Integer> iterator = numbers.iterator(); iterator.hasNext(); ) {
            Integer num = (Integer) iterator.next();
            bw.write(num + "\r\n");
        }
        bw.close();
        fw.close();
    }

    //再将所有小文件整合到一个大文件中
    public static void mergeFile() throws Exception {
        PriorityQueue<Obj> queue = new PriorityQueue<Obj>(20, new Obj());
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = new FileWriter(file1);
        BufferedWriter bw = new BufferedWriter(fw);
        List<FileReader> frList = new LinkedList<FileReader>();
        List<BufferedReader> brList = new LinkedList<BufferedReader>();
        int n;
        for (int i = 0; i < 20; i++) {
            String path = "F:" + File.separator + "dataTest" + File.separator + "data" + i + ".txt";
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            frList.add(fr);
            brList.add(br);
        }
        //把每个小文件的第一个数读入队列中
        for (int i = 0; i <= 20; i++) {
            BufferedReader buffR;
            if (i == 20) {
                while (queue.size() != 0) {
                    Obj obj = queue.poll();
                    bw.write(obj.a + "\r\n");
                    buffR = brList.get(obj.b);
                    while (buffR.ready() && queue.size() < 20) {
                        n = Integer.parseInt(buffR.readLine());
                        queue.add(new Obj(n, obj.b));
                    }
                }
                break;
            }
            buffR = brList.get(i);
            while (buffR.ready() && queue.size() < 20) {
                n = Integer.parseInt(buffR.readLine());
                Obj obj = new Obj(n, i);
                queue.add(obj);
                break;
            }
        }
        bw.close();
        fw.close();
        //遍历关闭所有子文件流
        for (Iterator iterator = brList.iterator(); iterator.hasNext(); ) {
            BufferedReader it = (BufferedReader) iterator.next();
            it.close();
        }

        for (Iterator iterator = frList.iterator(); iterator.hasNext(); ) {
            FileReader it = (FileReader) iterator.next();
            it.close();
        }
    }
}

class Obj implements Comparator<Obj> {
    int a, b;

    Obj() {
    }

    Obj(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int compare(Obj o1, Obj o2) {
        return o1.a - o2.a;
    }
}

