import javax.swing.JOptionPane;

public class Erro {

	public static void tratarError(int i) {

		if (!AnalisadorLexico.errorToken.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error Lexico: " + AnalisadorLexico.errorToken.subList(0, i));
			System.exit(0);
		}

		if (!AnalisadorSintatico.errorToken.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error Sintatico : " + AnalisadorSintatico.errorToken.subList(0, i));
			System.exit(0);
		}
	}

}
