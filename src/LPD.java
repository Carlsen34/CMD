import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class LPD {
	
	Stack token = new Stack();
	Stack nLinhas = new Stack();
	//Janela janela = new Janela();
	
	public LPD() {
		lerLPDLinha();
		System.out.println("Codigo Fonte :" + token);
		token = AnalisadorLexico.eliminarComentario(token);
		System.out.println("Eliminar Comentario  :" + token);
		token = AnalisadorLexico.consumirEspaco(token);
		
		
//		token = AnalisadorLexico.pegaToken(token);
//		System.out.println(token);
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
				numerosLinhas ++;
				aux = aux + linha + '\n';
				//janela.area.setText(aux);


				lerLPDCaracter(linha,numerosLinhas);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void lerLPDCaracter(String linha,int numberLinhas) {

		if(linha.length() == 0) {
			token.add(' ');
		}
		for(int i = 0; i < linha.length();i++) {
			token.add(linha.charAt(i));
		}
	}
	}


