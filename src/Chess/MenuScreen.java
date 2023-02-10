package Chess;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javax.swing.SwingConstants;

import Pieces.Rook;

import java.awt.Color;
import javax.swing.JButton;

public class MenuScreen {

	private JFrame chessMenu;

	public MenuScreen() {
		
		chessMenu = new JFrame();
		chessMenu.getContentPane().setBackground(Color.LIGHT_GRAY);
		chessMenu.setTitle("Chess");
		chessMenu.setResizable(false);
		chessMenu.setBounds(100, 100, 661, 433);
		chessMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessMenu.setLocationRelativeTo(null);
		chessMenu.getContentPane().setLayout(null);
		
		JLabel chessLbl = new JLabel("Chess");
		chessLbl.setHorizontalAlignment(SwingConstants.CENTER);
		chessLbl.setFont(new Font("Tahoma", Font.BOLD, 36));
		chessLbl.setBounds(199, 30, 237, 94);
		chessMenu.getContentPane().add(chessLbl);
		
		JButton newGameButton = new JButton("New Game");
		newGameButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		newGameButton.setBounds(166, 170, 309, 56);
		newGameButton.setFocusable(false);
		newGameButton.setBackground(new Color(212, 212, 212));
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GameMain.board = new Board();
				
				chessMenu.setVisible(false);
			} 
		});
		chessMenu.getContentPane().add(newGameButton);
		
		JButton loadGameButton = new JButton("Load Save");
		loadGameButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		loadGameButton.setBounds(166, 261, 309, 56);
		loadGameButton.setFocusable(false);
		loadGameButton.setBackground(new Color(212, 212, 212));
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					FileInputStream fi = new FileInputStream(new File("Save.txt"));
					ObjectInputStream oi = new ObjectInputStream(fi);

					BoardLayoutManager boardLayoutManager = (BoardLayoutManager) oi.readObject();

					oi.close();
					fi.close();
					
					GameMain.board = new Board(boardLayoutManager);
					
					chessMenu.setVisible(false);
					
				} catch (FileNotFoundException exeption) {
					JOptionPane.showMessageDialog(
							chessMenu,
						    "Save file is missing.",
						    "Error",
						    JOptionPane.PLAIN_MESSAGE
					);
				} catch (IOException exeption) {
					JOptionPane.showMessageDialog(
							chessMenu,
						    "No save can be retrieved",
						    "Error",
						    JOptionPane.PLAIN_MESSAGE
					);
				} catch (ClassNotFoundException exeption) {
				
				}
			} 
		});
		chessMenu.getContentPane().add(loadGameButton);
		
		chessMenu.setVisible(true);
	}
}
