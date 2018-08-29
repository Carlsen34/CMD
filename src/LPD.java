import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class LPD {
	
	
	Stack token = new Stack();
	AnalisadorLexico lexico = new AnalisadorLexico();
	
	public LPD() {
		lerLPDLinha();
		eliminarComentario();
	}
		

	
	public void eliminarComentario() {
		Stack aux = new Stack();
		int abre = 0;
		int fecha = token.size();

		for(int i = 0; i<token.size();i++) {
			if(token.get(i).equals('{') && abre == 0) {
				abre = i;
			}
			if(token.get(i).equals('}') && fecha > abre ) {
				fecha = i;
			}
		}
		
		for(int i = 0; i<token.size();i++) {
			if(i < abre) {
				aux.add(token.get(i));
			}
			if(i > fecha) {
				aux.add(token.get(i));
			}
		}
	
		
		
		System.out.println(abre);
		System.out.println(fecha);
		System.out.println(aux);
		
	}
	
	
	

	public void lerLPDLinha() {
		
		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			while ((linha = reader.readLine()) != null) {
				
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


