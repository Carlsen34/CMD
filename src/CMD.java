import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
public class CMD {
	
	
	Stack palavraReservada = new Stack();
	
	
	public CMD() {
		inserirpalavrasReservadas();
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
	
	
	private void inserirpalavrasReservadas() {
		palavraReservada.add("E");
		palavraReservada.add("INÍCIO");
		palavraReservada.add("BOOLEANO");
		palavraReservada.add("DIV");
		palavraReservada.add("FACA");
		palavraReservada.add("SENAO");
		palavraReservada.add("FIM");
		palavraReservada.add("FALSO");
		palavraReservada.add("FUNCAO");
		palavraReservada.add("SE");
		palavraReservada.add("INTEGER");
		palavraReservada.add("NAO");
		palavraReservada.add("OU");
		palavraReservada.add("PROCEDIMENTO");
		palavraReservada.add("PROGRAMA");
		palavraReservada.add("LEIA");
		palavraReservada.add("ENTAO");
		palavraReservada.add("VERDADEIRO");
		palavraReservada.add("VAR");
		palavraReservada.add("ENQUANTO");
		palavraReservada.add("ESCREVA");

	}
	
}
