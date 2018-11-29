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
					//System.out.println("" + " " + "ALLOC" + " " + Integer.toString(auxParam3) + " " + Integer.toString(auxParam4) );
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
		
		for(int i = 0; i<AnalisadorSemantico.simbolos.size();i++) {
		if(AnalisadorSemantico.simbolos.get(i).getTipoLexema().equals("var")) {
		if(AnalisadorSemantico.simbolos.get(i).getLexema().equals(lexema) && AnalisadorSemantico.simbolos.get(i).nivel <= nivel){
		aux = count;
		}
		count ++;
		}

		}
		
		
		return aux;
		//return pilhaVar.lastIndexOf(lexema);
	}
	
	public static String returnRotulo(String lexema) {
		for(int i = 0; i<AnalisadorSemantico.simbolos.size();i++) {
			if(lexema.equals(AnalisadorSemantico.simbolos.get(i).getLexema())) {
				return AnalisadorSemantico.simbolos.get(i).getRotulo();
			}
		}
		
		
		return "O";
		
	}
	
}
