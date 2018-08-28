import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class LPD {
	
	
	Stack token = new Stack();
	
	public LPD() {
	
		lerLPD();
		System.out.println(token);
		
		
	}
	

	public void lerLPD() {
		
		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] arrayValores = linha.split(" ");
				for (String s : arrayValores) {
					token.add(s);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	}


