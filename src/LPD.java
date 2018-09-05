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
		System.out.println("Codigo Fonte :" + token);
		token = AnalisadorLexico.eliminarComentario(token);
		System.out.println("Elininar Comentario  :" + token);
		token = AnalisadorLexico.consumirEspaco(token);
		System.out.println("Consumir Espaço :" + token);
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
				aux = aux + linha + '\n';
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


