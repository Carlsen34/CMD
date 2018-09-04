import java.util.Stack;

public class AnalisadorLexico {
	static Stack token = new Stack();


	public static Stack eliminarComentario(Stack caracter) {
		Stack aux = new Stack();


		for(int i = 0; i<caracter.size();i++) {
			if(caracter.get(i).equals('{')) {
				do {
					i++;	
				}while(!caracter.get(i).equals('}'));
		}else {
			aux.add(caracter.get(i));

		}
		}

		
		return aux;

		
	}
	
	public static Stack consumirEspaco(Stack caracter) {
		Stack aux = new Stack();
		
		for(int i = 0;i<caracter.size();i++) {
			if(!caracter.get(i).equals(' ')) {
				aux.add(caracter.get(i));
			}
			
		}
		
		
		return aux;

	}
	
	public static Stack pegaToken(Stack caracter) {
		Simbulo simbulos = new Simbulo();
		for(int i = 0; i<caracter.size();i++) {
			String aux = caracter.get(i).toString();
			if(isDigit(aux) == false) {
				i = tratarCaracter(caracter,i);
			}else {
				i = tratarDigito(caracter,i);
			}
		}
		
		
		return token;
	}
	
	
	
	
	private static int tratarDigito(Stack caracter,int i) {
		String aux = "";
		do {
			aux = aux + caracter.get(i).toString();
			i++;

		}while(isDigit(caracter.get(i).toString())==true);
		
		token.add(aux);
		token.add(Simbulo.Snumero);
		return i-1;

	}
	
	
	private static int tratarCaracter(Stack caracter,int i) {
		String aux = "";
		do {
	
			aux = aux + caracter.get(i).toString();
			i++;

			if(i == caracter.size()) {
				System.out.println(aux);
				return i;
			}
			
		}while(isDigit(caracter.get(i).toString())==false);
		System.out.println(aux);

		return i-1;
	}
	
	

	private static boolean isDigit(String aux) {
		if(aux.equals("0") || aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4") || aux.equals("5") || aux.equals("6") ||
				aux.equals("7") || aux.equals("8") || aux.equals("9")) {
		return true;
		}
		return false;
	
}

	

	
	
}
