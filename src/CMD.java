import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class CMD {
	
	public CMD() {
		PalavraReservada palavraReservada  = new PalavraReservada();
		lerLPD();
	}
	public static void lerLPD() {
		
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
					System.out.println(s);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
