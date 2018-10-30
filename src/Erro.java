import javax.swing.JOptionPane;

public class Erro {

	public static void tratarError(int i) {

		if (!AnalisadorLexico.errorToken.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error Lexico: " + AnalisadorLexico.errorToken.pop());
			System.exit(0);
		}

		if (!AnalisadorSintatico.tokenAS.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error Sintatico : " + AnalisadorSintatico.tokenAS.get(i) + " = "
					+ AnalisadorSintatico.tokenAS.get(i));
			System.exit(0);
		}
	}

}
