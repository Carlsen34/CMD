import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {
	static List<TabelaSimbolos> simbolos = new ArrayList<TabelaSimbolos>();

	public static void inserirTabela(String tokenLexema,String tipoLexema,String nivel,String rotulo) {
		TabelaSimbolos ts = new TabelaSimbolos();
		ts.setTokenLexema(tokenLexema);
		ts.setTipoLexema(tipoLexema);
		ts.setNivel(nivel);
		ts.setRotulo(rotulo);
		simbolos.add(ts);

	}
	
	public static boolean pesquisar_duplicvar_tabela(String tokenLexema,String tipoLexema,String nivel,String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
				return true;
			}
		}
		return false;
	}
	
	public static void printarTS() {
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).getTokenLexema());
			System.out.println(simbolos.get(i).getTipoLexema());
			System.out.println(simbolos.get(i).getNivel());
			System.out.println(simbolos.get(i).getRotulo());

		}
	}

}
