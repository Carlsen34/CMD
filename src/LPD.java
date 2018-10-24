import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class LPD {

	Stack token = new Stack();
	Stack nLinhas = new Stack();
	// Janela janela = new Janela();

	public LPD() {
		lerLPDLinha();
		token = AnalisadorLexico.eliminarComentario(token);
		token = AnalisadorLexico.consumirEspaco(token);
	}

	public void lerLPDLinha() {
		int numerosLinhas = 0;

		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;

			String aux = "";
			while ((linha = reader.readLine()) != null) {
				numerosLinhas++;
				aux = aux + linha + '\n';
				

				// janela.area.setText(aux);

				linha = linha + " ";
				lerLPDCaracter(linha, numerosLinhas);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int lerLPDLinha1(String caracter) {
		int numerosLinhas = 0;

		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;

			String aux = "";
			while ((linha = reader.readLine()) != null) {
				numerosLinhas++;
				aux = aux + linha + '\n';
				// janela.area.setText(aux);

				if (linha.contains(caracter)) {
					return numerosLinhas;
				}
				// lerLPDCaracter(linha,numerosLinhas);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void lerLPDCaracter(String linha, int numberLinhas) {
		// Fazer loop infinito pra jogar os tokens para o analisador lexico, junto com o
		// numero da linha
		if (linha.length() == 0) {
			token.add(' ');
		}
		for (int i = 0; i < linha.length(); i++) {
			token.add(linha.charAt(i));

		}
	}
}
