package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server extends JFrame implements ActionListener {

    private JLabel label;           //状态信息
    private JTextArea textArea;     //聊天框
    private JTextArea inputBox;   //输入框
    private JLabel label2;          //工具栏
    private JButton sendButton;     //消息发送按钮
    private JScrollPane scrollPane1; //滚动条
    private JScrollPane scrollPane2;
    private JTextArea infoTextarea; //资料框

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private SimpleDateFormat currentTime = new SimpleDateFormat("MM-dd HH:mm:ss");//时间格式


    public Server() {
        init();
    }

    private void init(){
        this.setSize(700,600);
        this.setTitle("服务端窗口");
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = this.getContentPane();    //面板内容

        //状态提示
        label = new JLabel("等待客户端连接...");
        label.setBounds(0,0,700,50);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font("微软雅黑",Font.BOLD,18));
        label.setForeground(Color.RED);
        contentPane.add(label);

        //聊天框
        textArea = new JTextArea();
        scrollPane1 = new JScrollPane(textArea);
        textArea.setLineWrap(true);//换行
        textArea.setWrapStyleWord(true);//断行不断字
        textArea.setFont(new Font("微软雅黑",Font.ITALIC,16));
        scrollPane1.setBounds(0,50,500,350);
        //scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane1);

        //工具栏
        label2 = new JLabel();
        label2.setBounds(0,400,500,50);
        contentPane.add(label2);

        //输入框
        inputBox = new JTextArea();
        scrollPane2 = new JScrollPane(inputBox);
        inputBox.setFont(new Font("微软雅黑", Font.ITALIC,16));
        inputBox.setLineWrap(true);//换行
        inputBox.setWrapStyleWord(true);//断行不断字
        scrollPane2.setBounds(0,450,580,150);
        contentPane.add(scrollPane2);

        //消息发送按钮
        sendButton = new JButton("发送");
        sendButton.setBounds(580,450,120,50);
        contentPane.add(sendButton);
        //listener
        sendButton.addActionListener(this);

        //资料框
        infoTextarea = new JTextArea();
        infoTextarea.setBounds(500,50,200,350);
        infoTextarea.setBackground(Color.lightGray);
        infoTextarea.setText("好友资料：");
        infoTextarea.setFont(new Font("微软雅黑", Font.ITALIC,16));
        contentPane.add(infoTextarea);

        //start thread
        new ServerThread().start();
    }

    /*
    线程
     */
    public class ServerThread extends Thread{
        @Override
        public void run() {
            try {
                //新建一个服务套接字
                serverSocket = new ServerSocket(8080,100);
                //通过accept()监听客户端请求
                socket = serverSocket.accept();
                //通过输入流读取客户端发送的请求信息
                inputStream = socket.getInputStream();
                //通过输出流向客户端发送响应信息
                outputStream = socket.getOutputStream();

                label.setText("已连接");
                label.setForeground(Color.green);

                //循环调用accept()等待客户端连接
                while(true){
                    //临时缓冲区
                    byte[] buf = new byte[1024];
                    //根据读取字节长度建立输出缓冲区
                    int len = inputStream.read(buf);
                    byte[] ebuf = new byte[len];
                    //拷贝实际数据
                    System.arraycopy(buf,0,ebuf,0,len);
                    //把字节转化成字符
                    String text = new String(ebuf);

                    //显示接受文本
                    textArea.append("客户端：");
                    textArea.append(currentTime.format(new Date()));
                    textArea.append("\r\n");
                    textArea.append(text);
                    textArea.append("\r\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton){
            String sendText = inputBox.getText();
            if (!"".equals(sendText) && sendText != null){
                //1.显示聊天记录
                textArea.append(currentTime.format(new Date()));
                textArea.append("\r\n");
                textArea.append(sendText);
                textArea.append("\r\n");
                inputBox.setText("");

                //2.发送数据
                byte[] sendbuf = sendText.getBytes();
                try {
                    if (outputStream != null) {
                        outputStream.write(sendbuf);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args){
        Server sw = new Server();
        sw.setResizable(false);
        sw.setVisible(true);
    }
}