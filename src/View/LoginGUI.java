package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_hastaPass;
	private JTextField fld_doctorTc;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_doctorPass;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Diş Sağlığı Merkezi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("tooth5.png")));
		lbl_logo.setBounds(161, 10, 129, 67);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Diş Sağlığı Merkezine Hoşgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel.setBounds(75, 87, 354, 30);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBackground(new Color(255, 255, 255));
		w_tabpane.setBounds(10, 132, 466, 271);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(new Color(255, 255, 255));
		w_tabpane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTcKimlikNo = new JLabel("T.C. Kimlik No:");
		lblTcKimlikNo.setBounds(35, 41, 134, 27);
		lblTcKimlikNo.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		w_hastaLogin.add(lblTcKimlikNo);
		
		JLabel lblifre = new JLabel("Şifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblifre.setBounds(35, 94, 134, 27);
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 21));
		fld_hastaTc.setBounds(179, 28, 247, 43);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		fld_hastaPass = new JTextField();
		fld_hastaPass.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_hastaPass.setColumns(10);
		fld_hastaPass.setBounds(179, 93, 247, 43);
		w_hastaLogin.add(fld_hastaPass);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_register.setBounds(35, 174, 176, 49);
		w_hastaLogin.add(btn_register);
		
		JButton btn_hastaLogin = new JButton("Giriş Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}else {
					boolean key = true;
					
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tcno"))&& fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString ("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					if(key) {
						Helper.showMsg("Böyle bir hasta bulunamadı lütfen kayıt olunuz !");
					}
				}
			}
		});
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_hastaLogin.setBounds(250, 174, 176, 49);
		w_hastaLogin.add(btn_hastaLogin);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(new Color(255, 255, 255));
		w_tabpane.addTab("Hekim Girişi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JLabel label = new JLabel("T.C. Kimlik No:");
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		label.setBounds(28, 45, 134, 27);
		w_doctorLogin.add(label);
		
		JLabel label_1 = new JLabel("Şifre:");
		label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		label_1.setBounds(28, 110, 134, 27);
		w_doctorLogin.add(label_1);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 21));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(168, 44, 261, 43);
		w_doctorLogin.add(fld_doctorTc);
		
		JButton btn_doctorLogin = new JButton("Giriş Yap");
		btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_doctorTc.getText().equals(rs.getString("tcno"))&& fld_doctorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString ("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString ("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_doctorLogin.setBounds(28, 169, 401, 54);
		w_doctorLogin.add(btn_doctorLogin);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(168, 110, 261, 43);
		w_doctorLogin.add(fld_doctorPass);
	}
}

