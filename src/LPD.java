import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class LPD {
	
	Stack token = new Stack();
	AnalisadorLexico lexico = new AnalisadorLexico();
	Janela janela = new Janela();
	
	public LPD() {
		lerLPDLinha();
		token = AnalisadorLexico.eliminarComentario(token);
		token = AnalisadorLexico.consumirEspaco(token);
		token = AnalisadorLexico.pegaToken(token);
		System.out.println(token);
	}
		

	public void lerLPDLinha() {
		
		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			String aux = "";
			while ((linha = reader.readLine()) != null) {
				aux = aux + linha ;
				janela.area.setText(aux);
				lerLPDCaracter(linha);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void lerLPDCaracter(String linha) {
		
		for(int i = 0; i < linha.length();i++) {
			token.add(linha.charAt(i));
		}
		
		
		

	}
	
	}


