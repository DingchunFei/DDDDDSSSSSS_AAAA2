package com.fei.paintUI;

import com.fei.paintUI.shape.Shape;
import com.fei.paintUI.util.ClientUtils;
import com.fei.paintUI.util.IOUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;
import javax.swing.*;

public class PainterUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private com.fei.paintUI.shape.Shape[] shapeParameter = new com.fei.paintUI.shape.Shape[20000];

    public void initUI() {
        // 新建窗体并命名
        JFrame jf = new JFrame("画板");
        // 设置窗体大小
        jf.setSize(1100, 700);
        // 窗体设置居中
        jf.setLocationRelativeTo(null);
        // 设置窗体关闭
        jf.setDefaultCloseOperation(3);
        // 设置窗体边界布局
        jf.setLayout(new BorderLayout());

        // 添加3个JPanel容器
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        //JButton button4=new JButton("右");     //菜单栏


        // 将JPanel布局到窗体中
        jf.add(this, BorderLayout.CENTER);
        jf.add(jp2, BorderLayout.WEST);
        jf.add(jp3, BorderLayout.EAST);

        // 设置jp1
        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(Color.white);

        // 创建事件监听器对象
        DrawListener dl = new DrawListener();
        // 给画布添加监听器
        this.addMouseListener(dl);
        this.addMouseMotionListener(dl);

        // 设置jp2
        jp2.setPreferredSize(new Dimension(100, 700));
        jp2.setBackground(Color.LIGHT_GRAY);
        // 设置jp3
        jp3.setPreferredSize(new Dimension(50, 700));
        jp3.setBackground(Color.LIGHT_GRAY);

        // 添加图形按钮
        String[] shapeArray = { "Line",  "Brush","Rectangle","FillRect", "Oval","FillOval","Circle",  "Text", "Rubber" ,"Clear"};
        for (int i = 0; i < shapeArray.length; i++) {
            // 创建图形按钮
            JButton jbu1 = new JButton(shapeArray[i]);
            // 设置按钮大小
            jbu1.setPreferredSize(new Dimension(100, 40));
            // 将按钮添加到jp2容器中
            jp2.add(jbu1);
            // 给按钮注册监听器
            jbu1.addActionListener(dl);
        }

        // 设置颜色按钮
        Color[] colorArray = { Color.red, Color.pink, Color.orange, Color.yellow, new Color(176,247,13), Color.green, new Color(18,187,161),
                Color.cyan, Color.blue, new Color(84,13,247) ,new Color(255,0,182), new Color(138,0,148) ,
                Color.black, new Color(58,58,58), Color.gray, new Color(200,200,200), Color.white };
        for (int i = 0; i < colorArray.length; i++) {
            JButton jbu2 = new JButton();
            jbu2.setBackground(colorArray[i]);
            jbu2.setPreferredSize(new Dimension(50, 30));
            jp3.add(jbu2);
            jbu2.addActionListener(dl);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("文件");
        //JMenu menu2 = new JMenu("退出");
        menuBar.add(menu1);
        //menuBar.add(menu2);
        JMenuItem item1 = new JMenuItem("打开");
        JMenuItem item2 = new JMenuItem("另存为");
        //JMenuItem item3 = new JMenuItem("另存为");
        JMenuItem item4 = new JMenuItem("退出");
        menu1.add(item1);
        menu1.add(item2);
        //menu1.add(item3);
        menu1.addSeparator();
        menu1.add(item4);

        //打开
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(item1)==JFileChooser.APPROVE_OPTION) {//
/*                    File file = chooser.getSelectedFile();
                    textArea.setText(file.getName()+":"+file.getPath()+"\n"+file.length());
                    readFile(file);*/
                    File file = chooser.getSelectedFile();
                    Shape[] shapes = IOUtil.readFile(file);
                    dl.rebuild(shapes);
                };

            }
        });

        //另存为
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JFileChooser chooser = new JFileChooser();

                if (chooser.showSaveDialog(item2)==JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    IOUtil.writeFile(file.getPath(),dl);
                }
            }
        });

        //菜单栏监听器
        //退出
        item4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e) {
                // TODO Auto-generated method stub
                int i=JOptionPane.showConfirmDialog(null, "是否真的退出系统",
                        "退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                //通过对话框中按钮的选择来决定结果，单机yes时，窗口直接消失
                if(i==0)
                    jf.dispose();

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 设置窗体可见
        jf.setVisible(true);
        // 获取画笔
        Graphics g = this.getGraphics();
        // 将画笔传递过去
        dl.setGr(g);
        // 将图形数组传递过去
        dl.setSp(shapeParameter);

        Thread thread = new Thread(new PaintingRunner(dl)) ;
        thread.start();

    }



    // 重写父类方法
    public void paint(Graphics g) {
        super.paint(g);     //遍历图形数组，重绘图形
        for (int i = 0; i < shapeParameter.length; i++) {
            Shape shape = shapeParameter[i];
            if (shape != null) {
                shape.drawShape(g);
                System.out.println(i+":"+shape.toString());
            }
            else{
                break;
            }
        }
    }


    //这个专门用来接受别人的画画的
    class PaintingRunner implements Runnable  {
        private DrawListener dl;

        public PaintingRunner (DrawListener dl)  {
            this.dl = dl ;
        }

        public void run()  {
            while(true){
                if(!ClientUtils.isEmpty()){
                    //取出第一个元素
                    System.out.println("去除了第一个元素！");
                    dl.rebuild(ClientUtils.getShape());

                }else{  //如果没有内容就睡一会
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        PainterUI pui = new PainterUI();
        pui.initUI();
    }
}
