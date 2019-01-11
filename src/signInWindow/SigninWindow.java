package signInWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SigninWindow extends JFrame implements ActionListener {
    private JLabel accountLabel;
    private JTextField accountBox;
    private JLabel passwordLabel;
    private JPasswordField passwordBox;
    private JButton jButton;


    public SigninWindow(){
        init();
    }

    private void init() {
        this.setSize(430, 330);
        this.setName("登陆窗口");
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = this.getContentPane();

        //账号输入box
        accountBox = new JTextField();
        accountBox.setBounds(100,100,200,20);
        accountBox.setFont(new Font("微软雅黑",Font.ITALIC,12));
        accountLabel = new JLabel("输入账号");
        accountLabel.setBounds(300,100,100,20);
        accountLabel.setFont(new Font("微软雅黑",Font.BOLD,12));
        contentPane.add(accountBox);
        contentPane.add(accountLabel);

        //密码box
        passwordBox = new JPasswordField();
        passwordBox.setBounds(100,150,200,20);
        passwordBox.setFont(new Font("微软雅黑",Font.ITALIC,12));
        passwordBox.setEchoChar('*');
        passwordLabel = new JLabel("输入密码");
        passwordLabel.setBounds(300,150,100,20);
        passwordLabel.setFont(new Font("微软雅黑",Font.BOLD,12));
        contentPane.add(passwordBox);
        contentPane.add(passwordLabel);

        //登陆按钮
        jButton = new JButton("登陆");
        jButton.setBounds(150,200,100,50);
        contentPane.add(jButton);
        jButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public static void main(String[] args){
        SigninWindow signinWindow = new SigninWindow();
        signinWindow.setResizable(false);
        signinWindow.setVisible(true);
    }
}
