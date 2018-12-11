import java.util.Stack;

public class GeradorCodigo {
	static Stack pilhaVar = new Stack();
	static Stack auxDalloc = new Stack();
	static boolean flgAux = false;
	static int auxParam3 = 0;
	static int auxParam4 = 0;
	static int auxRetornoFuncao = 0;
	static boolean auxRetornoFuncaoTipo = false;
	static String programaObjeto = "";
	
	public static void exibir_codigo_objeto(String param1, String param2,String param3,String param4){
			if(param2.equals("ALLOC")) {
				auxParam4 = auxParam4 + Integer.parseInt(param4) ;
				if(!flgAux) auxParam3 = Integer.parseInt(param3);
									
				flgAux = true;
			}else {
				
				if(flgAux) {
					programaObjeto = programaObjeto + "ALLOC" + " " + Integer.toString(auxParam3) + " "+ Integer.toString(auxParam4)+ "\n";
					auxDalloc.add(Integer.toString(auxParam3));
					auxDalloc.add(Integer.toString(auxParam4));
					auxParam3 = 0;
					auxParam4 = 0;
				}	
				if(!param1.equals(""))programaObjeto = programaObjeto + param1 + " " + param2 + " " + param3 + " " + param4 + "\n";
				else programaObjeto = programaObjeto + param2 + " " + param3 + " " + param4 + "\n";

				flgAux = false;
			}
			
	}
	
	public static int returnIndex(String lexema,int nivel) {
		int aux = pilhaVar.lastIndexOf(lexema);
		int count =0;
		
		for(int i = 0; i<AnalisadorSemantico.tabelaSimbolo.size();i++) {
		if(AnalisadorSemantico.tabelaSimbolo.get(i).getTipoLexema().equals("var")) {
		if(AnalisadorSemantico.tabelaSimbolo.get(i).getLexema().equals(lexema) && AnalisadorSemantico.tabelaSimbolo.get(i).nivel <= nivel){
		aux = count;
		}
		count ++;
		}

		}
		
		
		return aux;
	}
	
	public static String returnRotulo(String lexema) {
		for(int i = 0; i<AnalisadorSemantico.tabelaSimbolo.size();i++) {
			if(lexema.equals(AnalisadorSemantico.tabelaSimbolo.get(i).getLexema())) {
				return AnalisadorSemantico.tabelaSimbolo.get(i).getRotulo();
			}
		}
		
		
		return "-1";
		
	}
	
}
