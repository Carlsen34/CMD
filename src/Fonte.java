import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Fonte {

	//Stack token = new Stack();
	Stack nLinhas = new Stack();
	static List<Token> token = new ArrayList<Token>();
	
	
	
	public String lerArquivoEdicao() {

		int numerosLinhas = 1;
		String aux = "";
		try {
			// Cria arquivo
			File file = new File("CSD/codigoFonte.txt");

			// Le o arquivo
			FileReader ler = new FileReader("CSD/codigoFonte.txt");
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



	public String lerArquivoCompilacao() {

		int numerosLinhas = 1;
		String aux = "";
		try {
			// Cria arquivo
			File file = new File("CSD/codigoFonte.txt");

			// Le o arquivo
			FileReader ler = new FileReader("CSD/codigoFonte.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;

			while ((linha = reader.readLine()) != null) {
			    
				lerLFonteCaracter(linha,numerosLinhas);
				numerosLinhas++;
				aux = aux + linha + '\n';
				linha = linha + " ";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	      AnalisadorLexico.AnaliseLexica(token);

		return aux;
	}




	public void lerLFonteCaracter(String linha, int numberLinhas) {
		
		char[] letras = null;
		letras = linha.toCharArray();
	
		for(int i = 0; i<letras.length;i++) {
			Token t = new Token();
			t.setLexema(String.valueOf(letras[i]));
			t.setNumLinha(numberLinhas);
			token.add(t);

		}
		

	}
}
