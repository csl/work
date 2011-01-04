import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class login extends JFrame
{
		JLabel user=new JLabel("Account");
		JLabel pass=new JLabel("Password");
		JTextField tuser=new JTextField();
		String z="hasan";
		JPasswordField tpass=new JPasswordField();
		JButton blogin=new JButton("登入");
		JButton bbatal=new JButton("取消");
		public static void main(String[]args){
		login tampilan=new login();
		tampilan.setSize(310,170);
		Dimension layar=Toolkit.getDefaultToolkit().getScreenSize();
		int i=(layar.width-tampilan.getSize().width)/2;
		int t=(layar.height-tampilan.getSize().height)/2;
		tampilan.setLocation(i,t);
		tampilan.setResizable(false);
		tampilan.setVisible(true);
}

		String q="cacad";
public login()
{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setTitle("ftp軟體");
		user.setFont(new Font("Verdana",1,12));
		user.setBounds(new Rectangle(25,20,100,20));
		pass.setFont(new Font("Verdana",1,12));
		pass.setBounds(new Rectangle(25,50,100,20));
		tuser.setBounds(new Rectangle(110,22,140,20));
		tpass.setBounds(new Rectangle(110,52,140,20));
		blogin.setBounds(new Rectangle(62,92,80,20));
		blogin.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
		tlogin();}
});
bbatal.setBounds(new Rectangle(160,92,80,20));
bbatal.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){
batal();}
});
this.getContentPane().add(user,null);
this.getContentPane().add(pass,null);
this.getContentPane().add(tuser,null);
this.getContentPane().add(tpass,null);
this.getContentPane().add(blogin,null);
this.getContentPane().add(bbatal,null);
}
public void batal(){
System.exit(0);
}
public void tlogin(){
String u=z;
String p=q;
if(tuser.getText().equals(u)&&tpass.getText().equals(p)){
JOptionPane.showMessageDialog(null,"Welcome to HASAN IT Programmer, http://www.hasanalatas.com/");
System.exit(0);}
else{
JOptionPane.showMessageDialog(null,"Incorect Username and Password, silahkan coba lagi");
tpass.setText("");
tuser.setText("");
}
}
}