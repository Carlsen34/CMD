
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.*;

public class Janela extends JFrame implements ActionListener {

	JFrame tela = new JFrame("CMD");
	JTextArea area = new JTextArea();
	JPanel botoes = new JPanel();
	JButton compilar = new JButton();
	JButton apagar = new JButton();
	Fonte fonte = new Fonte();
	String aux = fonte.lerArquivo();

	public Janela() {

		tela.add(area);
		area.setBorder(BorderFactory.createLineBorder(Color.black));
		area.setBackground(Color.white);
		area.setText(aux);
		area.setPreferredSize(new Dimension(600, 10000));

		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(700, 500));
		tela.add(scroll);

		tela.setLayout(new FlowLayout());
		tela.setSize(1300, 700);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setVisible(true);

		tela.add(botoes);
		botoes.setPreferredSize(new Dimension(700, 100));

		compilar.setText("COMPILAR");
		compilar.setBackground(Color.GREEN);
		botoes.add(compilar);

		apagar.setText("APAGAR");
		apagar.setBackground(Color.RED);
		botoes.add(apagar);

		compilar.addActionListener(this);
		apagar.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == compilar) {
			String valor = area.getText();
			fonte.lerFonteLinha(valor);
		}

		if (e.getSource() == apagar) {
			area.setText("");
		}

	}

}