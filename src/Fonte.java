import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Fonte {

	Stack token = new Stack();
	Stack nLinhas = new Stack();

	public String lerArquivo() {

		int numerosLinhas = 0;
		String aux = "";
		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;

			while ((linha = reader.readLine()) != null) {
				numerosLinhas++;
				aux = aux + linha + '\n';
				linha = linha + " ";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return aux;
	}

	public void lerFonteLinha(String str) {
		for (int i = 0; i < str.length(); i++) {
			token.add(str.charAt(i));
		}
		token = AnalisadorLexico.eliminarComentario(token);
		token = AnalisadorLexico.consumirEspaco(token);

	}

	public void lerLFonteCaracter(String linha, int numberLinhas) {
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
